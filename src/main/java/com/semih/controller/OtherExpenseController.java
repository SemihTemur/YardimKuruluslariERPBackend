package com.semih.controller;

import com.semih.dto.request.OtherExpenseRequest;
import com.semih.dto.response.OtherExpenseResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.OtherExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api")
public class OtherExpenseController {

    private final OtherExpenseService otherExpenseService;

    public OtherExpenseController(OtherExpenseService otherExpenseService) {
        this.otherExpenseService = otherExpenseService;
    }

    @PostMapping(path = "/saveOtherExpense")
    public ResponseEntity<RestResponse<OtherExpenseResponse>> saveOtherExpense(@RequestBody OtherExpenseRequest otherExpenseRequest) {
        OtherExpenseResponse savedOtherExpenseResponse = otherExpenseService.saveOtherExpense(otherExpenseRequest);
        return new ResponseEntity<>(RestResponse.of(savedOtherExpenseResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getOtherExpenseList")
    public ResponseEntity<RestResponse<List<OtherExpenseResponse>>> getOtherExpenseList() {
        List<OtherExpenseResponse> otherExpenseResponseList = otherExpenseService.getOtherExpenseList();
        return new ResponseEntity<>(RestResponse.of(otherExpenseResponseList), HttpStatus.OK);
    }

    @PutMapping(path = "/updateOtherExpenseById/{id}")
    public ResponseEntity<RestResponse<OtherExpenseResponse>> updateOtherExpenseById(@PathVariable Long id, @RequestBody OtherExpenseRequest otherExpenseRequest) {
        OtherExpenseResponse updatedOtherExpense = otherExpenseService.updateOtherExpenseById(id, otherExpenseRequest);
        return new ResponseEntity<>(RestResponse.of(updatedOtherExpense), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteOtherExpenseById/{id}")
    public ResponseEntity<RestResponse<OtherExpenseResponse>> deleteOtherExpenseById(@PathVariable Long id) {
        OtherExpenseResponse deletedOtherExpense = otherExpenseService.deleteOtherExpenseById(id);
        return new ResponseEntity<>(RestResponse.of(deletedOtherExpense), HttpStatus.OK);
    }


}
