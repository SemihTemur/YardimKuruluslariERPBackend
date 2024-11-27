package com.semih.controller;

import com.semih.dto.request.IncomeRequest;
import com.semih.model.Income;
import com.semih.service.IncomeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/api")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping(path="/saveIncome")
    public void saveIncome(@RequestBody IncomeRequest income){
        incomeService.saveIncome(income);
    }
}
