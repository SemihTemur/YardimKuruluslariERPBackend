package com.semih.controller;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.DonorResponse;
import com.semih.dto.response.FamilyResponse;
import com.semih.model.Donor;
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

    @PostMapping(path="/saveDonor")
    public String saveDonor(@RequestBody DonorRequest donorRequest) {
        if(donorService.saveDonor(donorRequest)){
            return "success";
        }
        return "fail";
    }

    @GetMapping(path = "/getDonorList")
    public List<DonorResponse> getDonorList() {
        return donorService.getDonorList();
    }

    @GetMapping(path = "/getDonorById/{id}")
    public DonorResponse getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @PutMapping(path = "updateDonorById/{id}")
    public String updateDonorById(@PathVariable Long id, @RequestBody DonorRequest donorRequest) {
        if (donorService.updateDonorById(id, donorRequest)) {
            return "success";
        }
        return "fail";
    }

    @DeleteMapping(path = "/deleteDonorById/{id}")
    public String deleteDonorById(@PathVariable Long id) {
        if (donorService.deleteDonorById(id)) {
            return "Delete deleted successfully";
        }
        return "Delete deletion failed";
    }

    @DeleteMapping(path = "deleteAllDonor")
    public String deleteAllFamily() {
        donorService.deleteAllDonor();
        return "All donor deleted successfully";
    }

}
