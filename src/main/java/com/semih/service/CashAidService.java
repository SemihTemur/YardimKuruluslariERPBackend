package com.semih.service;

import com.semih.dto.request.CashAidRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.CashAidExpenseResponse;
import com.semih.dto.response.CashAidResponse;
import com.semih.exception.InsufficientBalanceException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.CashAid;
import com.semih.repository.CashAidRepository;
import com.semih.utils.HelperUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashAidService {

    private final CashAidRepository cashAidRepository;
    private final FamilyService familyService;
    private final CurrencyRateService currencyRateService;
    private final HelperUtils helperUtils;
    private final TreasuryService treasuryService;

    public CashAidService(CashAidRepository cashAidRepository, FamilyService familyService, CurrencyRateService currencyRateService, HelperUtils helperUtils, TreasuryService treasuryService) {
        this.cashAidRepository = cashAidRepository;
        this.familyService = familyService;
        this.currencyRateService = currencyRateService;
        this.helperUtils = helperUtils;
        this.treasuryService = treasuryService;
    }

    //  Mappers
    public CashAid mapToEntity(CashAidRequest cashAidRequest) {
        CashAid cashAid = new CashAid();

        // gelen Aile'yi buluyorum isme göre.
        cashAid.setFamily(familyService.getFamilyByName(cashAidRequest.familyName()));

        cashAid.setAidAmount(cashAidRequest.aidAmount());
        cashAid.setCurrency(cashAidRequest.currency());
        cashAid.setPeriod(cashAidRequest.period());
        cashAid.setDuration(cashAidRequest.duration());
        return cashAid;
    }

    public CashAidResponse mapToResponse(CashAid cashAid) {
        return new CashAidResponse(
                new BaseResponse(cashAid.getId(),
                        cashAid.getCreatedDate(),
                        cashAid.getModifiedDate()),
                cashAid.getFamily().getFamilyName(),
                cashAid.getAidAmount(),
                cashAid.getCurrency(),
                cashAid.getPeriod(),
                cashAid.getDuration(),
                cashAid.getTotalDonatedAmount(),
                cashAid.getStartingDate(),
                cashAid.getEndingDate()
        );
    }

    private CashAidExpenseResponse mapToExpenseResponse(CashAid cashAid) {
        return new CashAidExpenseResponse(
                new BaseResponse(cashAid.getId(),
                        cashAid.getCreatedDate(),
                        cashAid.getModifiedDate()),
                cashAid.getFamily().getFamilyName(),
                cashAid.getTotalDonatedAmount(),
                cashAid.getCurrency()
        );
    }

    // Save
    @Auditable(actionType = "Ekledi", targetEntity = "Ayni Bağış")
    public CashAidResponse saveCashAid(CashAidRequest cashAidRequest) {
        // toplam parayı almak için bazı bilgileri alıyorum
        BigDecimal aidAmount = cashAidRequest.aidAmount();
        Integer duration = cashAidRequest.duration();

        // toplam parayı al
        BigDecimal totalAidAmount = helperUtils.calculateTotalDonatedAmount(aidAmount, duration);

        // toplam parayı try çevir
        BigDecimal totalAidAmountTry = currencyRateService
                .convertToTry(String.valueOf(cashAidRequest.currency()), totalAidAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // eğer kasadaki para miktari daha büyük ise toplam bağışlancak mıktardan,
        // çevirme ve kaydetme işlemlerini yapıyorum.
        if (cashBalance.compareTo(totalAidAmountTry) >= 0) {
            CashAid cashAid = mapToEntity(cashAidRequest);
            cashAid.setTotalDonatedAmount(totalAidAmount);

            LocalDate today = LocalDate.now();
            cashAid.setStartingDate(today);

            // bitiş tarihini alma
            String periodType = String.valueOf(cashAid.getPeriod());
            LocalDate endDate = helperUtils.determineEndingDate(periodType, duration, today);
            cashAid.setEndingDate(endDate);

            // kasadaki para miktarını güncelleme
            BigDecimal updatedCashBalance = cashBalance.subtract(totalAidAmountTry);

            treasuryService.updateTreasury(updatedCashBalance);

            CashAid savedCashAid = cashAidRepository.save(cashAid);
            return mapToResponse(savedCashAid);
        } else {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }
    }

    // List
    public List<CashAidResponse> getCashAidList() {
        return cashAidRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CashAidExpenseResponse> getAllCashAidExpense() {
        return cashAidRepository.findAll().stream()
                .map(this::mapToExpenseResponse)
                .collect(Collectors.toList());
    }

    // update
    @Auditable(actionType = "Güncelledi", targetEntity = "Ayni Bağış")
    public CashAidResponse updateCashAidById(Long id, CashAidRequest cashAidRequest) {
        // Güncellenecek olan nesneyi ve toplam bağışlanan parayı alıyorum.
        CashAid existingCashAid = cashAidRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nakdi yardım bulunamadı!!!" + id));
        BigDecimal totalDonatedAidAmount = existingCashAid.getTotalDonatedAmount();

        //  şimdi toplam parayı Try'ye cevırmek kaldı.
        String currencyType = String.valueOf(existingCashAid.getCurrency());
        BigDecimal totalDonatedAidAmountTRY = currencyRateService.convertToTry(currencyType, totalDonatedAidAmount);

        BigDecimal receivedAmount = cashAidRequest.aidAmount();
        Integer receivedDuration = cashAidRequest.duration();

        // gelen toplam parayı hesaplayalım.
        BigDecimal receivedTotalDonatedAidAmount = helperUtils
                .calculateTotalDonatedAmount(receivedAmount, receivedDuration);

        // gelen toplam parayı TRY çevirelim.
        BigDecimal receivedTotalDonatedAidAmountTRY = currencyRateService
                .convertToTry(String.valueOf(cashAidRequest.currency()), receivedTotalDonatedAidAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // mevcut para'dan gelen para degerını cıkartıyorum.
        BigDecimal difference = totalDonatedAidAmountTRY.subtract(receivedTotalDonatedAidAmountTRY);

        BigDecimal updatedCashBalance = null;

        // mevcut bakiyeye fark eklenince 0dan büyük ya da eşit ise
        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) >= 0) {
            updatedCashBalance = cashBalance.add(difference);
        } else {
            // hata fırlatıcam burda
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        // gelen yeni nesneyı entity'e çeviriyorum
        CashAid updatedCashAid = mapToEntity(cashAidRequest);
        updatedCashAid.setId(id);
        updatedCashAid.setTotalDonatedAmount(receivedTotalDonatedAidAmount);
        updatedCashAid.setCreatedDate(existingCashAid.getCreatedDate());

        // başlangıç değerine göre ve gelen değere göre son tarihi guncellıyorum
        LocalDate receivedDate = existingCashAid.getStartingDate();
        updatedCashAid.setStartingDate(receivedDate);
        String receivedPeriodType = String.valueOf(updatedCashAid.getPeriod());
        LocalDate endDate = helperUtils.determineEndingDate(receivedPeriodType, receivedDuration, receivedDate);
        updatedCashAid.setEndingDate(endDate);

        // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum;
        treasuryService.updateTreasury(updatedCashBalance);

        updatedCashAid = cashAidRepository.save(updatedCashAid);
        return mapToResponse(updatedCashAid);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Ayni Bağış")
    public CashAidResponse deleteCashAidById(Long id) {
        CashAid deletedCashAid = cashAidRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nakdi yardım bulunamadı!!!" + id));
        BigDecimal totalDonatedAmount = deletedCashAid.getTotalDonatedAmount();

        // kayıtlı olan toplam miktari tl'ye çeviriyorum.
        BigDecimal totalDonatedAmountTry = currencyRateService
                .convertToTry(deletedCashAid.getCurrency().toString(), totalDonatedAmount);

        // kasadaki parayı alıyorum.
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.add(totalDonatedAmountTry);

        treasuryService.updateTreasury(updatedBalance);

        cashAidRepository.delete(deletedCashAid);
        return mapToResponse(deletedCashAid);
    }

}
