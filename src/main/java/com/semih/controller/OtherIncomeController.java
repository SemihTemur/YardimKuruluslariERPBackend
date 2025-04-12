package com.semih.controller;

import com.semih.dto.request.OtherIncomeRequest;
import com.semih.dto.response.OtherIncomeResponse;
import com.semih.service.OtherIncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class OtherIncomeController {

    private OtherIncomeService otherIncomeService;

    public OtherIncomeController(OtherIncomeService otherIncomeService) {
        this.otherIncomeService = otherIncomeService;
    }

    @PostMapping(path="/saveOtherIncome")
    public void saveOtherIncome(@RequestBody OtherIncomeRequest otherIncomeRequest) {
        otherIncomeService.saveOtherIncome(otherIncomeRequest);
    }

    @GetMapping(path="/getOtherIncomeList")
    public List<OtherIncomeResponse> getOtherIncomeList() {
        return otherIncomeService.getAllOtherIncome();
    }

    @PutMapping(path="/updateOtherIncomeById/{id}")
    public void updateOtherIncomeById(@PathVariable Long id, @RequestBody OtherIncomeRequest otherIncomeRequest) {
        otherIncomeService.updateOtherIncomeById(id, otherIncomeRequest);
    }

    @DeleteMapping(path="/deleteOtherIncomeById/{id}")
    public void deleteOtherIncomeById(@PathVariable Long id) {
        otherIncomeService.deleteOtherIncomeById(id);
    }
}
