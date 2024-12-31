package com.semih.controller;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.service.ScholarshipService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @PostMapping(path="/saveScholarship")
    public void saveScholarship(@RequestBody ScholarshipRequest scholarshipRequest) {
        scholarshipService.saveScholarship(scholarshipRequest);
    }

    @PutMapping("/updateScholarship/{id}")
    public void updateScholarshipById(@PathVariable Long id, @RequestBody ScholarshipRequest scholarshipRequest) {
        scholarshipService.updateScholarshipById(id, scholarshipRequest);
    }

}
