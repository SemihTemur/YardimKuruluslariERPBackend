package com.semih.service;

import com.semih.dto.request.OtherExpenseRequest;
import com.semih.dto.response.OtherExpenseResponse;
import com.semih.model.OtherExpense;
import com.semih.repository.OtherExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OtherExpenseService {

    private final OtherExpenseRepository otherExpenseRepository;
    private final TreasuryService treasuryService;
    private final CurrencyRateService currencyRateService;

    public OtherExpenseService(OtherExpenseRepository otherExpenseRepository, TreasuryService treasuryService, CurrencyRateService currencyRateService) {
        this.otherExpenseRepository = otherExpenseRepository;
        this.treasuryService = treasuryService;
        this.currencyRateService = currencyRateService;
    }

    public OtherExpense mapDtoToEntity(OtherExpenseRequest otherExpenseRequest) {
        return new OtherExpense(
                otherExpenseRequest.getDescription(),
                otherExpenseRequest.getAmount(),
                otherExpenseRequest.getCurrency()
        );
    }

    public OtherExpenseResponse mapEntityToResponse(OtherExpense otherExpense) {
        return new OtherExpenseResponse(
                otherExpense.getId(),
                otherExpense.getCreatedDate(),
                otherExpense.getModifiedDate(),
                otherExpense.getDescription(),
                otherExpense.getAmount(),
                otherExpense.getCurrency()
        );
    }


    public void saveOtherExpense(OtherExpenseRequest otherExpenseRequest) {
        OtherExpense otherExpense = mapDtoToEntity(otherExpenseRequest);

        // gelen Parayi Try çevir, kasadaki para ile kontrol etmek için
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(otherExpense.getCurrency()), otherExpense.getAmount());

        // Kasadaki mevcut bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // eğer kasadaki para gider parasından büyükse kasadaki parayı azalt.
        if (cashBalance.compareTo(amountTry) >= 0) {
            BigDecimal updatedAmount = cashBalance.subtract(amountTry);
            treasuryService.updateTreasury(updatedAmount);

            otherExpenseRepository.save(otherExpense);
        } else {
            System.out.println("Kasada bu kadar para yoktur");
        }
    }

    public List<OtherExpenseResponse> getAllOtherExpenses() {
        return otherExpenseRepository.findAll()
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateOtherExpenseById(Long id, OtherExpenseRequest otherExpenseRequest) {
        OtherExpense existingOtherExpense = otherExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        // Var olan kayıttaki parayı try çeviriyorum.
        BigDecimal existingAmountTry = currencyRateService.convertToTry(String.valueOf(existingOtherExpense.getCurrency()), existingOtherExpense.getAmount());

        // gelen parayı try çevir
        BigDecimal receivedAmountTry = currencyRateService.convertToTry(String.valueOf(otherExpenseRequest.getCurrency()), otherExpenseRequest.getAmount());

        // kasadaki miktarı al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // farkı al
        BigDecimal difference = existingAmountTry.subtract(receivedAmountTry);

        // gelen değeri mevcut mıktardan azaltıcak isem
        if (existingAmountTry.compareTo(receivedAmountTry) >= 0 || cashBalance.compareTo(difference.abs()) >= 0) {
            // farkı mevcut bakiyenin üzerine ekle
            treasuryService.updateTreasury(cashBalance.add(difference));
        } else {
            throw new RuntimeException("Yetersiz bakiye.");
        }

        // güncellenen değeri veritabanına kaydediyorum.
        OtherExpense updatedOtherExpense = mapDtoToEntity(otherExpenseRequest);

        //bunlar map'lendiği için yeni değer oluşuyor.ben ise guncelleme yapıcagım ıcın id'yi setlıyorum.
        updatedOtherExpense.setId(id);
        updatedOtherExpense.setCreatedDate(existingOtherExpense.getCreatedDate());

        otherExpenseRepository.save(updatedOtherExpense);

    }

    public void deleteOtherExpenseById(Long id) {
        // silmeden önce para miktarını al
        OtherExpense existingOtherExpense = otherExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal existingAmountTry = currencyRateService.convertToTry(String.valueOf(existingOtherExpense.getCurrency()), existingOtherExpense.getAmount());

        // kasadaki para miktarini al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        treasuryService.updateTreasury(cashBalance.add(existingAmountTry));

        otherExpenseRepository.delete(existingOtherExpense);
    }

}
