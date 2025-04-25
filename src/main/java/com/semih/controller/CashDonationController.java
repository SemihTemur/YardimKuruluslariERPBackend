package com.semih.controller;

import com.semih.dto.request.CashDonationRequest;
import com.semih.dto.response.CashDonationResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.CashDonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RestResponse<CashDonationResponse>> saveCashDonation(@RequestBody CashDonationRequest cashDonationRequest) {
        CashDonationResponse savedCashDonationResponse = cashDonationService.saveCashDonation(cashDonationRequest);
        return new ResponseEntity<>(RestResponse.of(savedCashDonationResponse), HttpStatus.OK);
    }

    @GetMapping("/getCashDonationList")
    public ResponseEntity<RestResponse<List<CashDonationResponse>>> getCashDonationList() {
        List<CashDonationResponse> cashDonationResponseList = cashDonationService.getCashDonationList();
        return new ResponseEntity<>(RestResponse.of(cashDonationResponseList), HttpStatus.OK);
    }

    @PutMapping("/updateCashDonationById/{id}")
    public ResponseEntity<RestResponse<CashDonationResponse>> updateCashDonationById(@PathVariable Long id, @RequestBody CashDonationRequest cashDonationRequest) {
        CashDonationResponse updatedCashDonationResponse = cashDonationService.updateCashDonationById(id, cashDonationRequest);
        return new ResponseEntity<>(RestResponse.of(updatedCashDonationResponse), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCashDonationById/{id}")
    public ResponseEntity<RestResponse<CashDonationResponse>> deleteCashDonationById(@PathVariable Long id) {
        CashDonationResponse deletedCashDonationResponse = cashDonationService.deleteCashDonationById(id);
        return new ResponseEntity<>(RestResponse.of(deletedCashDonationResponse), HttpStatus.OK);
    }

}
