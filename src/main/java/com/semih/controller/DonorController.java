package com.semih.controller;

import com.semih.service.DonorService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }
}
