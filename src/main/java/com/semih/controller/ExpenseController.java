package com.semih.controller;

import com.semih.service.ExpenseService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {
    
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
}
