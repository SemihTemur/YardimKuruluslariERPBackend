package com.semih.controller;

import com.semih.dto.request.CashAidRequest;
import com.semih.dto.response.CashAidExpenseResponse;
import com.semih.dto.response.CashAidResponse;
import com.semih.service.CashAidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class CashAidController {

    private final CashAidService cashAidService;

    public CashAidController(CashAidService cashAidService) {
        this.cashAidService = cashAidService;
    }

    @PostMapping(path="/saveCashAid")
    public void saveCashAid(@RequestBody CashAidRequest cashAidRequest) {
        cashAidService.saveCashAid(cashAidRequest);
    }

    @GetMapping(path="/getCashAidList")
    public List<CashAidResponse> getCashAidList() {
        return cashAidService.getAllCashAid();
    }

    @GetMapping(path="/getCashAidExpenseList")
    public List<CashAidExpenseResponse> getCashAidExpenseList() {
        return cashAidService.getAllCashAidExpense();
    }

    @PutMapping(path="/updateCashAidById/{id}")
    public void updateCashAidById(@PathVariable Long id, @RequestBody CashAidRequest cashAidRequest) {
        cashAidService.updateCashAidById(id, cashAidRequest);
    }

    @DeleteMapping(path="/deleteCashAidById/{id}")
    public void deleteCashAidById(@PathVariable Long id) {
        cashAidService.deleteCashAidById(id);
    }

}
