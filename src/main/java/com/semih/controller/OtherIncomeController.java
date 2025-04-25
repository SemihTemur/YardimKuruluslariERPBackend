package com.semih.controller;

import com.semih.dto.request.OtherIncomeRequest;
import com.semih.dto.response.OtherIncomeResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.OtherIncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class OtherIncomeController {

    private OtherIncomeService otherIncomeService;

    public OtherIncomeController(OtherIncomeService otherIncomeService) {
        this.otherIncomeService = otherIncomeService;
    }

    @PostMapping(path = "/saveOtherIncome")
    public ResponseEntity<RestResponse<OtherIncomeResponse>> saveOtherIncome(@RequestBody OtherIncomeRequest otherIncomeRequest) {
        OtherIncomeResponse savedOtherIncomeResponse = otherIncomeService.saveOtherIncome(otherIncomeRequest);
        return new ResponseEntity<>(RestResponse.of(savedOtherIncomeResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getOtherIncomeList")
    public ResponseEntity<RestResponse<List<OtherIncomeResponse>>> getOtherIncomeList() {
        List<OtherIncomeResponse> otherIncomeResponseList = otherIncomeService.getOtherIncomeList();
        return new ResponseEntity<>(RestResponse.of(otherIncomeResponseList), HttpStatus.OK);
    }

    @PutMapping(path = "/updateOtherIncomeById/{id}")
    public ResponseEntity<RestResponse<OtherIncomeResponse>> updateOtherIncomeById(@PathVariable Long id, @RequestBody OtherIncomeRequest otherIncomeRequest) {
        OtherIncomeResponse updatedOtherIncome = otherIncomeService.updateOtherIncomeById(id, otherIncomeRequest);
        return new ResponseEntity<>(RestResponse.of(updatedOtherIncome), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteOtherIncomeById/{id}")
    public ResponseEntity<RestResponse<OtherIncomeResponse>> deleteOtherIncomeById(@PathVariable Long id) {
        OtherIncomeResponse deletedOtherIncomeResponse = otherIncomeService.deleteOtherIncomeById(id);
        return new ResponseEntity<>(RestResponse.of(deletedOtherIncomeResponse), HttpStatus.OK);
    }
}
