package com.semih.controller;

import com.semih.service.ScholarshipService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }
}
