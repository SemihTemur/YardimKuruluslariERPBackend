package com.semih.controller;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.response.DonorNameResponse;
import com.semih.dto.response.DonorResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.DonorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping(path = "/saveDonor")
    public ResponseEntity<RestResponse<DonorResponse>> saveDonor(@RequestBody DonorRequest donorRequest) {
        DonorResponse savedDonorResponse = donorService.saveDonor(donorRequest);
        return new ResponseEntity<>(RestResponse.of(savedDonorResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getDonorList")
    public ResponseEntity<RestResponse<List<DonorResponse>>> getDonorList() {
        List<DonorResponse> donorResponseList = donorService.getDonorList();
        return new ResponseEntity<>(RestResponse.of(donorResponseList), HttpStatus.OK);
    }

    @GetMapping(path = "/getDonorById/{id}")
    public ResponseEntity<RestResponse<DonorResponse>> getDonorById(@PathVariable Long id) {
        DonorResponse donorResponse = donorService.getDonorById(id);
        return new ResponseEntity<>(RestResponse.of(donorResponse), HttpStatus.OK);
    }

    @GetMapping(path = "/getDonorNameAndSurnameList")
    public List<DonorNameResponse> getDonorNameAndSurnameList() {
        return donorService.getDonorNameAndSurnameList();
    }

    @PutMapping(path = "/updateDonorById/{id}")
    public ResponseEntity<RestResponse<DonorResponse>> updateDonorById(@PathVariable Long id, @RequestBody DonorRequest donorRequest) {
        DonorResponse updatedDonorResponse = donorService.updateDonorById(id, donorRequest);
        return new ResponseEntity<>(RestResponse.of(updatedDonorResponse), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteDonorById/{id}")
    public ResponseEntity<RestResponse<DonorResponse>> deleteDonorById(@PathVariable Long id) {
        DonorResponse deletedDonorResponse = donorService.deleteDonorById(id);
        return new ResponseEntity<>(RestResponse.of(deletedDonorResponse), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllDonor")
    public ResponseEntity<RestResponse<List<DonorResponse>>> deleteAllFamily() {
        List<DonorResponse> deletedDonorResponseList = donorService.deleteAllDonor();
        return new ResponseEntity<>(RestResponse.of(deletedDonorResponseList), HttpStatus.OK);
    }

}
