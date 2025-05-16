package com.semih.service;

import com.semih.dto.request.OtherIncomeRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.OtherIncomeResponse;
import com.semih.exception.InsufficientBalanceException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.OtherIncome;
import com.semih.repository.OtherIncomeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OtherIncomeService {

    private final OtherIncomeRepository otherIncomeRepository;
    private final CurrencyRateService currencyRateService;
    private final TreasuryService treasuryService;

    public OtherIncomeService(OtherIncomeRepository otherIncomeRepository, CurrencyRateService currencyRateService, TreasuryService treasuryService) {
        this.otherIncomeRepository = otherIncomeRepository;
        this.currencyRateService = currencyRateService;
        this.treasuryService = treasuryService;
    }

    private OtherIncome mapToEntity(OtherIncomeRequest otherIncomeRequest) {
        return new OtherIncome(
                otherIncomeRequest.description(),
                otherIncomeRequest.amount(),
                otherIncomeRequest.currency()
        );
    }

    private OtherIncomeResponse mapToResponse(OtherIncome otherIncome) {
        return new OtherIncomeResponse(
                new BaseResponse(otherIncome.getId(),
                        otherIncome.getCreatedDate(),
                        otherIncome.getModifiedDate()),
                otherIncome.getDescription(),
                otherIncome.getAmount(),
                otherIncome.getCurrency()
        );
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Diğer Gelirler")
    public OtherIncomeResponse saveOtherIncome(OtherIncomeRequest otherIncomeRequest) {
        OtherIncome savedOtherIncome = mapToEntity(otherIncomeRequest);

        // Gelen değeri try çevir.
        BigDecimal amountTry = currencyRateService
                .convertToTry(String.valueOf(savedOtherIncome.getCurrency()), otherIncomeRequest.amount());

        // Mevcut kasadaki bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        BigDecimal updatedBalance = cashBalance.add(amountTry);
        treasuryService.updateTreasury(updatedBalance);

        savedOtherIncome = otherIncomeRepository.save(savedOtherIncome);
        return mapToResponse(savedOtherIncome);
    }

    public List<OtherIncomeResponse> getOtherIncomeList() {
        return otherIncomeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Diğer Gelirler")
    public OtherIncomeResponse updateOtherIncomeById(Long id, OtherIncomeRequest otherIncomeRequest) {
        OtherIncome otherIncome = otherIncomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diğer gelir bulunamadı!!!" + id));

        // mevcut kayıttaki bilgileri al
        BigDecimal amount = otherIncome.getAmount();
        String currencyType = String.valueOf(otherIncome.getCurrency());
        BigDecimal amountTry = currencyRateService.convertToTry(currencyType, amount);

        // gelen kayıttaki bilgileri al
        BigDecimal receivedAmount = otherIncomeRequest.amount();
        String receivedCurrencyType = String.valueOf(receivedAmount);
        BigDecimal receivedAmountTry = currencyRateService.convertToTry(receivedCurrencyType, receivedAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        BigDecimal difference = receivedAmountTry.subtract(amountTry);

        // ya gelen miktar o anki miktardan büyükse ya da fark kasadaki miktardan kucukse işlem yap
        if (receivedAmountTry.compareTo(amountTry) >= 0 || cashBalance.compareTo(difference.abs()) >= 0) {
            BigDecimal updatedBalance = cashBalance.add(difference);
            treasuryService.updateTreasury(updatedBalance);
        } else {
            // Hata fırlat
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        // gelen değeri guncellemek ıcın
        OtherIncome updatedOtherIncome = mapToEntity(otherIncomeRequest);
        updatedOtherIncome.setId(id);
        updatedOtherIncome.setCreatedDate(otherIncome.getCreatedDate());
        updatedOtherIncome = otherIncomeRepository.save(updatedOtherIncome);
        return mapToResponse(updatedOtherIncome);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Diğer Gelirler")
    public OtherIncomeResponse deleteOtherIncomeById(Long id) {
        // silmeden önce kasadan azaltma kontrolu
        OtherIncome deletedOtherIncome = otherIncomeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diğer gelir bulunamadı!!!" + id));
        BigDecimal amount = deletedOtherIncome.getAmount();
        String currencyType = String.valueOf(deletedOtherIncome.getCurrency());

        // silmeden önce parayı azaltmak için tl'ye çevir
        BigDecimal amountTry = currencyRateService.convertToTry(currencyType, amount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        if (cashBalance.compareTo(amountTry) < 0) {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");

        }
        BigDecimal updatedBalance = cashBalance.subtract(amountTry);
        treasuryService.updateTreasury(updatedBalance);
        otherIncomeRepository.delete(deletedOtherIncome);
        return mapToResponse(deletedOtherIncome);
    }

}
