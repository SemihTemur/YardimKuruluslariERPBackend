package com.semih.controller;

import com.semih.service.DonationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }
}
