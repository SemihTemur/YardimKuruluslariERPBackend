package com.semih.controller;

import com.semih.dto.request.OtherExpenseRequest;
import com.semih.dto.response.OtherExpenseResponse;
import com.semih.service.OtherExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/rest/api")
public class OtherExpenseController {

    private final OtherExpenseService otherExpenseService;

    public OtherExpenseController(OtherExpenseService otherExpenseService) {
        this.otherExpenseService = otherExpenseService;
    }

    @PostMapping(path="/saveOtherExpense")
    public void saveOtherExpense(@RequestBody OtherExpenseRequest otherExpenseRequest) {
        otherExpenseService.saveOtherExpense(otherExpenseRequest);
    }

    @GetMapping(path="/getOtherExpenseList")
    public List<OtherExpenseResponse> getOtherExpenseList() {
        return otherExpenseService.getAllOtherExpenses();
    }

    @PutMapping(path="/updateOtherExpenseById/{id}")
    public void updateOtherExpenseById(@PathVariable Long id, @RequestBody OtherExpenseRequest otherExpenseRequest) {
        otherExpenseService.updateOtherExpenseById(id, otherExpenseRequest);
    }

    @DeleteMapping(path="deleteOtherExpenseById/{id}")
    public void deleteOtherExpenseById(@PathVariable Long id) {
        otherExpenseService.deleteOtherExpenseById(id);
    }


}
