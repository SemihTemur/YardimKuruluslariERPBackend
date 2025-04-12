package com.semih.controller;

import com.semih.dto.request.CashDonationRequest;
import com.semih.dto.response.CashDonationResponse;
import com.semih.service.CashDonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class CashDonationController {

    private final CashDonationService cashDonationService;

    public CashDonationController(CashDonationService cashDonationService) {
        this.cashDonationService = cashDonationService;
    }

    @PostMapping("/saveCashDonation")
    public void saveCashDonation(@RequestBody CashDonationRequest cashDonationRequest) {
        cashDonationService.saveCashDonation(cashDonationRequest);
    }

    @GetMapping("/getCashDonationList")
    public List<CashDonationResponse> getCashDonationList() {
        return cashDonationService.getAllCashDonations();
    }

    @PutMapping("/updateCashDonationById/{id}")
    public void updateCashDonationById(@PathVariable Long id, @RequestBody CashDonationRequest cashDonationRequest) {
        cashDonationService.updateCashDonationById(id,cashDonationRequest);
    }

    @DeleteMapping("/deleteCashDonationById/{id}")
    public void deleteCashDonationById(@PathVariable Long id) {
        cashDonationService.deleteCashDonationById(id);
    }

}
