package com.semih.controller;

import com.semih.dto.request.InKindDonationRequest;
import com.semih.dto.response.InKindDonationResponse;
import com.semih.service.InKindDonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class InKindDonationController {

    private final InKindDonationService inKindDonationService;

    public InKindDonationController(InKindDonationService inKindDonationService) {
        this.inKindDonationService = inKindDonationService;
    }

    @PostMapping(path="/saveInKindDonation")
    public void saveInKindDonation(@RequestBody InKindDonationRequest inKindDonationRequest) {
        inKindDonationService.saveInKindDonation(inKindDonationRequest);
    }

    @GetMapping(path="/getInKindDonationList")
    public List<InKindDonationResponse> getInKindDonationList() {
        return inKindDonationService.getInKindDonations();
    }

    @PutMapping(path="/updateInKindDonationById/{id}")
    private void updateInKindDonationById(@PathVariable Long id, @RequestBody InKindDonationRequest inKindDonationRequest) {
        inKindDonationService.updateInKindDonationById(id, inKindDonationRequest);
    }

    @DeleteMapping(path="/deleteInKindDonationById/{id}")
    public void deleteInKindDonationById(@PathVariable Long id) {
        inKindDonationService.deleteInKindDonationById(id);
    }
}
