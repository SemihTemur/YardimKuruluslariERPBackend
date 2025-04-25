package com.semih.controller;

import com.semih.dto.request.CashAidRequest;
import com.semih.dto.response.CashAidExpenseResponse;
import com.semih.dto.response.CashAidResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.CashAidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class CashAidController {

    private final CashAidService cashAidService;

    public CashAidController(CashAidService cashAidService) {
        this.cashAidService = cashAidService;
    }

    @PostMapping(path = "/saveCashAid")
    public ResponseEntity<RestResponse<CashAidResponse>> saveCashAid(@RequestBody CashAidRequest cashAidRequest) {
        CashAidResponse savedCashAidResponse = cashAidService.saveCashAid(cashAidRequest);
        return new ResponseEntity<>(RestResponse.of(savedCashAidResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getCashAidList")
    public ResponseEntity<RestResponse<List<CashAidResponse>>> getCashAidList() {
        List<CashAidResponse> cashAidResponseList = cashAidService.getCashAidList();
        return new ResponseEntity<>(RestResponse.of(cashAidResponseList), HttpStatus.OK);
    }

    @GetMapping(path = "/getCashAidExpenseList")
    public ResponseEntity<RestResponse<List<CashAidExpenseResponse>>> getCashAidExpenseList() {
        List<CashAidExpenseResponse> cashAidExpenseResponseList = cashAidService.getAllCashAidExpense();
        return new ResponseEntity<>(RestResponse.of(cashAidExpenseResponseList), HttpStatus.OK);
    }

    @PutMapping(path = "/updateCashAidById/{id}")
    public ResponseEntity<RestResponse<CashAidResponse>> updateCashAidById(@PathVariable Long id, @RequestBody CashAidRequest cashAidRequest) {
        CashAidResponse updatedCashAidResponse = cashAidService.updateCashAidById(id, cashAidRequest);
        return new ResponseEntity<>(RestResponse.of(updatedCashAidResponse), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteCashAidById/{id}")
    public ResponseEntity<RestResponse<CashAidResponse>> deleteCashAidById(@PathVariable Long id) {
        CashAidResponse deletedCashAidResponse = cashAidService.deleteCashAidById(id);
        return new ResponseEntity<>(RestResponse.of(deletedCashAidResponse), HttpStatus.OK);
    }

}
