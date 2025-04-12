package com.semih.service;

import com.semih.dto.request.CashAidRequest;
import com.semih.dto.response.CashAidExpenseResponse;
import com.semih.dto.response.CashAidResponse;
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

    public CashAid mapDtoToEntity(CashAidRequest cashAidRequest) {
        CashAid cashAid = new CashAid();

        // gelen Aile'yi buluyorum isme göre.
        cashAid.setFamily(familyService.getFamilyByName(cashAidRequest.getFamilyName()));

        cashAid.setAidAmount(cashAidRequest.getAidAmount());
        cashAid.setCurrency(cashAidRequest.getCurrency());
        cashAid.setPeriod(cashAidRequest.getPeriod());
        cashAid.setDuration(cashAidRequest.getDuration());
        return cashAid;
    }

    public CashAidResponse mapEntityToResponse(CashAid cashAid) {
        return new CashAidResponse(
                cashAid.getId(),
                cashAid.getCreatedDate(),
                cashAid.getModifiedDate(),
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

    private CashAidExpenseResponse mapEntityToExpenseResponse(CashAid cashAid) {
        return new CashAidExpenseResponse(
                cashAid.getFamily().getFamilyName(),
                cashAid.getTotalDonatedAmount(),
                cashAid.getCurrency()
        );
    }

    public void saveCashAid(CashAidRequest cashAidRequest) {
        // toplam parayı almak için bazı bilgileri alıyorum
        BigDecimal aidAmount = cashAidRequest.getAidAmount();
        Integer duration = cashAidRequest.getDuration();

        // toplam parayı al
        BigDecimal totalAidAmount = helperUtils.calculateTotalDonatedAmount(aidAmount, duration);

        // toplam parayı try çevir
        BigDecimal totalAidAmountTry = currencyRateService.convertToTry(String.valueOf(cashAidRequest.getCurrency()), totalAidAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // eğer kasadaki para miktari daha büyük ise toplam bağışlancak mıktardan,
        // çevirme ve kaydetme işlemlerini yapıyorum.
        if (cashBalance.compareTo(totalAidAmountTry) >= 0) {
            CashAid cashAid = mapDtoToEntity(cashAidRequest);
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

            cashAidRepository.save(cashAid);
        } else {
            throw new RuntimeException("Yetersiz Bakiye");
        }
    }

    public List<CashAidResponse> getAllCashAid() {
        return cashAidRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<CashAidExpenseResponse> getAllCashAidExpense() {
        return cashAidRepository.findAll().stream()
                .map(this::mapEntityToExpenseResponse)
                .collect(Collectors.toList());
    }

    public void updateCashAidById(Long id, CashAidRequest cashAidRequest) {
        // Güncellenecek olan nesneyi ve toplam bağışlanan parayı alıyorum.
        CashAid existingCashAid = cashAidRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAidAmount = existingCashAid.getTotalDonatedAmount();

        //  şimdi toplam parayı Try'ye cevırmek kaldı.
        String currencyType = String.valueOf(existingCashAid.getCurrency());
        BigDecimal totalDonatedAidAmountTRY = currencyRateService.convertToTry(currencyType, totalDonatedAidAmount);

        BigDecimal receivedAmount = cashAidRequest.getAidAmount();
        Integer receivedDuration = cashAidRequest.getDuration();

        // gelen toplam parayı hesaplayalım.
        BigDecimal receivedTotalDonatedAidAmount = helperUtils.calculateTotalDonatedAmount(receivedAmount, receivedDuration);

        // gelen toplam parayı TRY çevirelim.
        BigDecimal receivedTotalDonatedAidAmountTRY = currencyRateService.convertToTry(String.valueOf(cashAidRequest.getCurrency()), receivedTotalDonatedAidAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // mevcut para'dan gelen para degerını cıkartıyorum.
        BigDecimal difference = totalDonatedAidAmountTRY.subtract(receivedTotalDonatedAidAmountTRY);

        BigDecimal updatedCashBalance = null;

        // mevcut bakiyeye fark eklenince 0dan büyük ya da eşit ise
        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) >= 0) {
            updatedCashBalance = cashBalance.add(difference);
        } else {
            // hata fırlatıcam burda
            throw new RuntimeException("Yetersiz Bakiye");
        }

        // gelen yeni nesneyı entity'e çeviriyorum
        CashAid uptadedCashAid = mapDtoToEntity(cashAidRequest);
        uptadedCashAid.setId(id);
        uptadedCashAid.setTotalDonatedAmount(receivedTotalDonatedAidAmount);
        uptadedCashAid.setCreatedDate(existingCashAid.getCreatedDate());

        // başlangıç değerine göre ve gelen değere göre son tarihi guncellıyorum
        LocalDate receivedDate = existingCashAid.getStartingDate();
        uptadedCashAid.setStartingDate(receivedDate);
        String receivedPeriodType = String.valueOf(uptadedCashAid.getPeriod());
        LocalDate endDate = helperUtils.determineEndingDate(receivedPeriodType, receivedDuration, receivedDate);
        uptadedCashAid.setEndingDate(endDate);

        // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum;
        treasuryService.updateTreasury(updatedCashBalance);

        cashAidRepository.save(uptadedCashAid);
    }

    public void deleteCashAidById(Long id) {
        CashAid cashAid = cashAidRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAmount = cashAid.getTotalDonatedAmount();

        // kayıtlı olan toplam miktari tl'ye çeviriyorum.
        BigDecimal totalDonatedAmountTry = currencyRateService.convertToTry(cashAid.getCurrency().toString(), totalDonatedAmount);

        // kasadaki parayı alıyorum.
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.add(totalDonatedAmountTry);

        treasuryService.updateTreasury(updatedBalance);

        cashAidRepository.delete(cashAid);
    }

}
