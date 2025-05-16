package com.semih.controller;

import com.semih.dto.request.InKindDonationRequest;
import com.semih.dto.response.InKindDonationResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.InKindDonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class InKindDonationController {

    private final InKindDonationService inKindDonationService;

    public InKindDonationController(InKindDonationService inKindDonationService) {
        this.inKindDonationService = inKindDonationService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDDONATION_SAVE')")
    @PostMapping(path = "/saveInKindDonation")
    public ResponseEntity<RestResponse<InKindDonationResponse>> saveInKindDonation(@RequestBody InKindDonationRequest inKindDonationRequest) {
        InKindDonationResponse savedInKindDonationResponse = inKindDonationService.saveInKindDonation(inKindDonationRequest);
        return new ResponseEntity<>(RestResponse.of(savedInKindDonationResponse), HttpStatus.OK);
    }


    @GetMapping(path = "/getInKindDonationList")
    public ResponseEntity<RestResponse<List<InKindDonationResponse>>> getInKindDonationList() {
        List<InKindDonationResponse> indDonationResponseList = inKindDonationService.getInKindDonationList();
        return new ResponseEntity<>(RestResponse.of(indDonationResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDDONATION_UPDATE')")
    @PutMapping(path = "/updateInKindDonationById/{id}")
    private ResponseEntity<RestResponse<InKindDonationResponse>> updateInKindDonationById(@PathVariable Long id, @RequestBody InKindDonationRequest inKindDonationRequest) {
        InKindDonationResponse updatedInKindDonationResponse = inKindDonationService.updateInKindDonationById(id, inKindDonationRequest);
        return new ResponseEntity<>(RestResponse.of(updatedInKindDonationResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDDONATION_DELETE')")
    @DeleteMapping(path = "/deleteInKindDonationById/{id}")
    public ResponseEntity<RestResponse<InKindDonationResponse>> deleteInKindDonationById(@PathVariable Long id) {
        InKindDonationResponse deletedInKindDonationResponse = inKindDonationService.deleteInKindDonationById(id);
        return new ResponseEntity<>(RestResponse.of(deletedInKindDonationResponse), HttpStatus.OK);
    }
}
