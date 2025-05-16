package com.semih.service;

import com.semih.dto.request.CashDonationRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.CashDonationResponse;
import com.semih.exception.InsufficientBalanceException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.CashDonation;
import com.semih.repository.CashDonationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashDonationService {

    private final CashDonationRepository cashDonationRepository;
    private final CurrencyRateService currencyRateService;
    private final DonorService donorService;
    private final TreasuryService treasuryService;

    public CashDonationService(CashDonationRepository cashDonationRepository, CurrencyRateService currencyRateService, DonorService donorService, TreasuryService treasuryService) {
        this.cashDonationRepository = cashDonationRepository;
        this.currencyRateService = currencyRateService;
        this.donorService = donorService;
        this.treasuryService = treasuryService;
    }

    private CashDonation mapToEntity(CashDonationRequest cashDonationRequest) {
        CashDonation cashDonation = new CashDonation();
        cashDonation.setDonor(donorService.getDonorByFirstNameAndLastName(cashDonationRequest.donorFirstName(), cashDonationRequest.donorLastName()));
        cashDonation.setAmount(cashDonationRequest.amount());
        cashDonation.setCurrency(cashDonationRequest.currency());
        return cashDonation;
    }

    private CashDonationResponse mapToResponse(CashDonation cashDonation) {
        return new CashDonationResponse(
                new BaseResponse(cashDonation.getId(),
                        cashDonation.getCreatedDate(),
                        cashDonation.getModifiedDate()),
                cashDonation.getDonor().getFirstName(),
                cashDonation.getDonor().getLastName(),
                cashDonation.getAmount(),
                cashDonation.getCurrency()
        );
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Nakdi Bağış")
    public CashDonationResponse saveCashDonation(CashDonationRequest cashDonationRequest) {
        CashDonation cashDonation = mapToEntity(cashDonationRequest);
        // Gelen para'yı try çevir.
        BigDecimal amountTry = currencyRateService
                .convertToTry(String.valueOf(cashDonation.getCurrency()), cashDonation.getAmount());

        // Kasadaki mevcut miktarı al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        // gelen miktarı üzerine ekle
        BigDecimal updatedBalance = cashBalance.add(amountTry);

        // Kasadaki parayı gğncelle
        treasuryService.updateTreasury(updatedBalance);

        CashDonation savedCashDonation = cashDonationRepository.save(cashDonation);
        return mapToResponse(savedCashDonation);
    }

    public List<CashDonationResponse> getCashDonationList() {
        return cashDonationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Nakdi Bağış")
    public CashDonationResponse updateCashDonationById(Long id, CashDonationRequest cashDonationRequest) {
        // var olan kaydı al
        CashDonation existingCashDonation = cashDonationRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Nakdi bağış bulunamadı!!!"));
        // try çevir.
        BigDecimal existingAmountTry = currencyRateService
                .convertToTry(String.valueOf(existingCashDonation.getCurrency()), existingCashDonation.getAmount());

        // gelen değeri try çevir
        BigDecimal amountTry = currencyRateService
                .convertToTry(String.valueOf(cashDonationRequest.currency()), cashDonationRequest.amount());

        // kasadaki bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        BigDecimal difference = amountTry.subtract(existingAmountTry);

        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal updatedBalance = cashBalance.add(difference);
            treasuryService.updateTreasury(updatedBalance);
        } else {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        CashDonation updatedCashDonation = mapToEntity(cashDonationRequest);
        updatedCashDonation.setId(id);
        updatedCashDonation.setCreatedDate(existingCashDonation.getCreatedDate());
        updatedCashDonation = cashDonationRepository.save(updatedCashDonation);
        return mapToResponse(updatedCashDonation);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Nakdi Bağış")
    public CashDonationResponse deleteCashDonationById(Long id) {
        // var olan kaydı al
        CashDonation deletedCashDonation = cashDonationRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Nakdi bağış bulunamadı!!!" + id));
        // parayı try çevir
        BigDecimal amountTry = currencyRateService
                .convertToTry(String.valueOf(deletedCashDonation.getCurrency()), deletedCashDonation.getAmount());

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.subtract(amountTry);

        if (updatedBalance.compareTo(BigDecimal.ZERO) >= 0) {
            treasuryService.updateTreasury(updatedBalance);

            cashDonationRepository.delete(deletedCashDonation);
        } else {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        return mapToResponse(deletedCashDonation);
    }

}
