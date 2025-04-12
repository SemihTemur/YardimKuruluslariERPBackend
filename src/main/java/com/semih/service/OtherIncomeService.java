package com.semih.service;

import com.semih.dto.request.OtherIncomeRequest;
import com.semih.dto.response.OtherIncomeResponse;
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

    private OtherIncome mapDtoToEntity(OtherIncomeRequest otherIncomeRequest) {
        return new OtherIncome(
                otherIncomeRequest.getDescription(),
                otherIncomeRequest.getAmount(),
                otherIncomeRequest.getCurrency()
        );
    }

    private OtherIncomeResponse mapEntityToResponse(OtherIncome otherIncome) {
        return new OtherIncomeResponse(
                otherIncome.getId(),
                otherIncome.getCreatedDate(),
                otherIncome.getModifiedDate(),
                otherIncome.getDescription(),
                otherIncome.getAmount(),
                otherIncome.getCurrency()
        );
    }


    public void saveOtherIncome(OtherIncomeRequest otherIncomeRequest) {
        OtherIncome otherIncome = mapDtoToEntity(otherIncomeRequest);
        ;

        // Gelen değeri try çevir.
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(otherIncome.getCurrency()), otherIncomeRequest.getAmount());

        // Mevcut kasadaki bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        BigDecimal updatedBalance = cashBalance.add(amountTry);
        treasuryService.updateTreasury(updatedBalance);

        otherIncomeRepository.save(otherIncome);
    }

    public List<OtherIncomeResponse> getAllOtherIncome() {
        return otherIncomeRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateOtherIncomeById(Long id, OtherIncomeRequest otherIncomeRequest) {
        OtherIncome otherIncome = otherIncomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no other income with this id"));

        // mevcut kayıttaki bilgileri al
        BigDecimal amount = otherIncome.getAmount();
        String currencyType = String.valueOf(otherIncome.getCurrency());
        BigDecimal amountTry = currencyRateService.convertToTry(currencyType, amount);

        // gelen kayıttaki bilgileri al
        BigDecimal receivedAmount = otherIncomeRequest.getAmount();
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
            throw new RuntimeException("Yetersiz bakiye.");
        }

        // gelen değeri guncellemek ıcın
        OtherIncome updatedOtherIncome = mapDtoToEntity(otherIncomeRequest);
        updatedOtherIncome.setId(id);
        updatedOtherIncome.setCreatedDate(otherIncome.getCreatedDate());
        otherIncomeRepository.save(updatedOtherIncome);
    }

    public void deleteOtherIncomeById(Long id) {
        // silmeden önce kasadan azaltma kontrolu
        OtherIncome savedOtherIncome = otherIncomeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no other income with this id"));
        BigDecimal amount = savedOtherIncome.getAmount();
        String currencyType = String.valueOf(savedOtherIncome.getCurrency());

        // silmeden önce parayı azaltmak için tl'ye çevir
        BigDecimal amountTry = currencyRateService.convertToTry(currencyType, amount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        if (cashBalance.compareTo(amountTry) >= 0) {
            BigDecimal updatedBalance = cashBalance.subtract(amountTry);
            treasuryService.updateTreasury(updatedBalance);
            otherIncomeRepository.delete(savedOtherIncome);
        } else {
            throw new RuntimeException("Yetersiz bakiye.");
        }
    }

}
