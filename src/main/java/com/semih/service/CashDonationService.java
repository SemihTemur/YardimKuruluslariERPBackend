package com.semih.service;

import com.semih.dto.request.CashDonationRequest;
import com.semih.dto.response.CashDonationResponse;
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

    private CashDonation mapDtoToEntity(CashDonationRequest cashDonationRequest) {
        CashDonation cashDonation = new CashDonation();
        cashDonation.setDonor(donorService.getDonorByFirstNameAndLastName(cashDonationRequest.getDonorFirstName(), cashDonationRequest.getDonorLastName()));
        cashDonation.setAmount(cashDonationRequest.getAmount());
        cashDonation.setCurrency(cashDonationRequest.getCurrency());
        return cashDonation;
    }

    private CashDonationResponse mapEntityToResponse(CashDonation cashDonation) {
        return new CashDonationResponse(
                cashDonation.getId(),
                cashDonation.getCreatedDate(),
                cashDonation.getModifiedDate(),
                cashDonation.getDonor().getFirstName(),
                cashDonation.getDonor().getLastName(),
                cashDonation.getAmount(),
                cashDonation.getCurrency()
        );
    }


    public void saveCashDonation(CashDonationRequest cashDonationRequest) {
        CashDonation cashDonation = mapDtoToEntity(cashDonationRequest);
        // Gelen para'yı try çevir.
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(cashDonation.getCurrency()), cashDonation.getAmount());

        // Kasadaki mevcut miktarı al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        // gelen miktarı üzerine ekle
        BigDecimal updatedBalance = cashBalance.add(amountTry);

        // Kasadaki parayı gğncelle
        treasuryService.updateTreasury(updatedBalance);

        cashDonationRepository.save(cashDonation);
    }

    public List<CashDonationResponse> getAllCashDonations() {
        return cashDonationRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateCashDonationById(Long id, CashDonationRequest cashDonationRequest) {
        // var olan kaydı al
        CashDonation existingCashDonation = cashDonationRepository.findById(id).orElseThrow(() -> new RuntimeException("cashDonation not found"));
        // try çevir.
        BigDecimal existingAmountTry = currencyRateService.convertToTry(String.valueOf(existingCashDonation.getCurrency()), existingCashDonation.getAmount());

        // gelen değeri try çevir
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(cashDonationRequest.getCurrency()), cashDonationRequest.getAmount());

        // kasadaki bakiyeyi al
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        BigDecimal difference = amountTry.subtract(existingAmountTry);

        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal updatedBalance = cashBalance.add(difference);
            treasuryService.updateTreasury(updatedBalance);
        } else {
            throw new RuntimeException("Yetersiz Bakiye");
        }

        CashDonation updatedCashDonation = mapDtoToEntity(cashDonationRequest);
        updatedCashDonation.setId(id);
        updatedCashDonation.setCreatedDate(existingCashDonation.getCreatedDate());
        cashDonationRepository.save(updatedCashDonation);
    }

    public void deleteCashDonationById(Long id) {
        // var olan kaydı al
        CashDonation existingCashDonation = cashDonationRepository.findById(id).orElseThrow(() -> new RuntimeException("cashDonation not found"));
        // parayı try çevir
        BigDecimal amountTry = currencyRateService.convertToTry(String.valueOf(existingCashDonation.getCurrency()), existingCashDonation.getAmount());

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.subtract(amountTry);

        if (updatedBalance.compareTo(BigDecimal.ZERO) >= 0) {
            treasuryService.updateTreasury(updatedBalance);

            cashDonationRepository.delete(existingCashDonation);
        } else {
            throw new RuntimeException("Yetersiz Bakiye");
        }

    }

}
