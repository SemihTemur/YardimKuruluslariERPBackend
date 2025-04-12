package com.semih.controller;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.response.DonorNameResponse;
import com.semih.dto.response.DonorResponse;
import com.semih.service.DonorService;
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
    public void saveDonor(@RequestBody DonorRequest donorRequest) {
        donorService.saveDonor(donorRequest);
    }

    @GetMapping(path = "/getDonorList")
    public List<DonorResponse> getDonorList() {
        return donorService.getDonorList();
    }

    @GetMapping(path = "/getDonorById/{id}")
    public DonorResponse getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @GetMapping(path = "/getDonorNameAndSurnameList")
    public List<DonorNameResponse> getDonorNameAndSurnameList() {
        return donorService.getDonorNameAndSurnameList();
    }

    @PutMapping(path = "/updateDonorById/{id}")
    public void updateDonorById(@PathVariable Long id, @RequestBody DonorRequest donorRequest) {
        donorService.updateDonorById(id, donorRequest);
    }

    @DeleteMapping(path = "/deleteDonorById/{id}")
    public void deleteDonorById(@PathVariable Long id) {
        donorService.deleteDonorById(id);
    }

    @DeleteMapping(path = "deleteAllDonor")
    public void deleteAllFamily() {
        donorService.deleteAllDonor();
    }

}
