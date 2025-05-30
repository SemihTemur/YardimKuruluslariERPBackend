package com.semih.controller;

import com.semih.dto.request.CashDonationRequest;
import com.semih.dto.response.CashDonationResponse;
import com.semih.dto.response.MonthlyDonationStatsResponse;
import com.semih.dto.response.RestResponse;
import com.semih.dto.response.TopDonorResponse;
import com.semih.service.CashDonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class CashDonationController {

    private final CashDonationService cashDonationService;

    public CashDonationController(CashDonationService cashDonationService) {
        this.cashDonationService = cashDonationService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('CASHDONATION_SAVE')")
    @PostMapping("/saveCashDonation")
    public ResponseEntity<RestResponse<CashDonationResponse>> saveCashDonation(@RequestBody CashDonationRequest cashDonationRequest) {
        CashDonationResponse savedCashDonationResponse = cashDonationService.saveCashDonation(cashDonationRequest);
        return new ResponseEntity<>(RestResponse.of(savedCashDonationResponse), HttpStatus.OK);
    }

    @GetMapping("/getCashDonationList")
    public ResponseEntity<RestResponse<List<CashDonationResponse>>> getCashDonationList() {
        List<CashDonationResponse> cashDonationResponseList = cashDonationService.getCashDonationList();
        return new ResponseEntity<>(RestResponse.of(cashDonationResponseList), HttpStatus.OK);
    }

    @GetMapping("/getCashDonationAmounts")
    public ResponseEntity<RestResponse<BigDecimal>> getCashDonationAmounts() {
        BigDecimal cashDonationAmounts = cashDonationService.getCashDonationAmounts();
        return new ResponseEntity<>(RestResponse.of(cashDonationAmounts), HttpStatus.OK);
    }

    @GetMapping("/getTopDonors")
    public ResponseEntity<RestResponse<List<TopDonorResponse>>> getTopDonors() {
        List<TopDonorResponse> topDonorResponseList = cashDonationService.getTopDonorResponse();
        return new ResponseEntity<>(RestResponse.of(topDonorResponseList), HttpStatus.OK);
    }

    @GetMapping("/getMonthlyDonationStats")
    public ResponseEntity<RestResponse<List<MonthlyDonationStatsResponse>>> getMonthlyDonationStatsResponse() {
        List<MonthlyDonationStatsResponse> monthlyDonationStatsResponseList = cashDonationService.getMonthlyDonationStatsResponse();
        return new ResponseEntity<>(RestResponse.of(monthlyDonationStatsResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('CASHDONATION_UPDATE')")
    @PutMapping("/updateCashDonationById/{id}")
    public ResponseEntity<RestResponse<CashDonationResponse>> updateCashDonationById(@PathVariable Long id, @RequestBody CashDonationRequest cashDonationRequest) {
        CashDonationResponse updatedCashDonationResponse = cashDonationService.updateCashDonationById(id, cashDonationRequest);
        return new ResponseEntity<>(RestResponse.of(updatedCashDonationResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('CASHDONATION_DELETE')")
    @DeleteMapping("/deleteCashDonationById/{id}")
    public ResponseEntity<RestResponse<CashDonationResponse>> deleteCashDonationById(@PathVariable Long id) {
        CashDonationResponse deletedCashDonationResponse = cashDonationService.deleteCashDonationById(id);
        return new ResponseEntity<>(RestResponse.of(deletedCashDonationResponse), HttpStatus.OK);
    }

}
