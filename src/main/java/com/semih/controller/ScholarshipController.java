package com.semih.controller;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.dto.response.ScholarshipExpenseResponse;
import com.semih.dto.response.ScholarshipResponse;
import com.semih.service.ScholarshipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path="/getScholarshipList")
    public List<ScholarshipResponse> getScholarshipList() {
        return scholarshipService.getAllScholarship();
    }

    @GetMapping(path="/getScholarshipExpenseList")
    public List<ScholarshipExpenseResponse> getScholarshipExpenseList() {
        return scholarshipService.getAllScholarshipExpense();
    }

    @PutMapping("/updateScholarshipById/{id}")
    public void updateScholarshipById(@PathVariable Long id, @RequestBody ScholarshipRequest scholarshipRequest) {
        scholarshipService.updateScholarshipById(id, scholarshipRequest);
    }

    @DeleteMapping(path="/deleteScholarshipById/{id}")
    public void deleteScholarshipById(@PathVariable Long id) {
        scholarshipService.deleteScholarshipById(id);
    }

}
