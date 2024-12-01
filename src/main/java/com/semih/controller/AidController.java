package com.semih.controller;

import com.semih.service.AidService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AidController {

    private final AidService aidService;

    public AidController(AidService aidService) {
        this.aidService = aidService;
    }
}
