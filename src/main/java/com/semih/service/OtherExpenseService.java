package com.semih.service;

import com.semih.dto.request.OtherExpenseRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.OtherExpenseResponse;
import com.semih.exception.InsufficientBalanceException;
import com.semih.exception.NotFoundException;
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

    public OtherExpense mapToEntity(OtherExpenseRequest otherExpenseRequest) {
        return new OtherExpense(
                otherExpenseRequest.description(),
                otherExpenseRequest.amount(),
                otherExpenseRequest.currency()
        );
    }

    public OtherExpenseResponse mapToResponse(OtherExpense otherExpense) {
        return new OtherExpenseResponse(
                new BaseResponse(otherExpense.getId(),
                        otherExpense.getCreatedDate(),
                        otherExpense.getModifiedDate()),
                otherExpense.getDescription(),
                otherExpense.getAmount(),
                otherExpense.getCurrency()
        );
    }

    public OtherExpenseResponse saveOtherExpense(OtherExpenseRequest otherExpenseRequest) {
        OtherExpense savedOtherExpense = mapToEntity(otherExpenseRequest);

        // gelen Parayi Try çevir, kasadaki para ile kontrol etmek için
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(savedOtherExpense.getCurrency()),
                savedOtherExpense.getAmount());

        // Kasadaki mevcut bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // eğer kasadaki para gider parasından büyükse kasadaki parayı azalt.
        if (cashBalance.compareTo(amountTry) < 0) {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }
        BigDecimal updatedAmount = cashBalance.subtract(amountTry);
        treasuryService.updateTreasury(updatedAmount);

        savedOtherExpense = otherExpenseRepository.save(savedOtherExpense);
        return mapToResponse(savedOtherExpense);
    }

    public List<OtherExpenseResponse> getOtherExpenseList() {
        return otherExpenseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OtherExpenseResponse updateOtherExpenseById(Long id, OtherExpenseRequest otherExpenseRequest) {
        OtherExpense existingOtherExpense = otherExpenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diğer gider bulunamadı!!!"));
        // Var olan kayıttaki parayı try çeviriyorum.
        BigDecimal existingAmountTry = currencyRateService
                .convertToTry(String.valueOf(existingOtherExpense.getCurrency()), existingOtherExpense.getAmount());

        // gelen parayı try çevir
        BigDecimal receivedAmountTry = currencyRateService
                .convertToTry(String.valueOf(otherExpenseRequest.currency()), otherExpenseRequest.amount());

        // kasadaki miktarı al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // farkı al
        BigDecimal difference = existingAmountTry.subtract(receivedAmountTry);

        // gelen değeri mevcut mıktardan azaltıcak isem
        if (existingAmountTry.compareTo(receivedAmountTry) >= 0 || cashBalance.compareTo(difference.abs()) >= 0) {
            // farkı mevcut bakiyenin üzerine ekle
            treasuryService.updateTreasury(cashBalance.add(difference));
        } else {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        // güncellenen değeri veritabanına kaydediyorum.
        OtherExpense updatedOtherExpense = mapToEntity(otherExpenseRequest);

        //bunlar map'lendiği için yeni değer oluşuyor.ben ise guncelleme yapıcagım ıcın id'yi setlıyorum.
        updatedOtherExpense.setId(id);
        updatedOtherExpense.setCreatedDate(existingOtherExpense.getCreatedDate());

        updatedOtherExpense = otherExpenseRepository.save(updatedOtherExpense);
        return mapToResponse(updatedOtherExpense);
    }

    public OtherExpenseResponse deleteOtherExpenseById(Long id) {
        // silmeden önce para miktarını al
        OtherExpense deletedOtherExpense = otherExpenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diğer gider bulunamadı!!!"));
        BigDecimal existingAmountTry = currencyRateService
                .convertToTry(String.valueOf(deletedOtherExpense.getCurrency()), deletedOtherExpense.getAmount());

        // kasadaki para miktarini al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        treasuryService.updateTreasury(cashBalance.add(existingAmountTry));

        otherExpenseRepository.delete(deletedOtherExpense);
        return mapToResponse(deletedOtherExpense);
    }

}
