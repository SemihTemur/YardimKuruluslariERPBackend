package com.semih.controller;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.dto.response.RestResponse;
import com.semih.dto.response.ScholarshipExpenseResponse;
import com.semih.dto.response.ScholarshipResponse;
import com.semih.service.ScholarshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('SCHOLARSHIP_SAVE')")
    @PostMapping(path = "/saveScholarship")
    public ResponseEntity<RestResponse<ScholarshipResponse>> saveScholarship(@RequestBody ScholarshipRequest scholarshipRequest) {
        ScholarshipResponse savedScholarshipResponse = scholarshipService.saveScholarship(scholarshipRequest);
        return new ResponseEntity<>(RestResponse.of(savedScholarshipResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('SCHOLARSHIP_LIST')")
    @GetMapping(path = "/getScholarshipList")
    public ResponseEntity<RestResponse<List<ScholarshipResponse>>> getScholarshipList() {
        List<ScholarshipResponse> scholarshipResponseList = scholarshipService.getScholarshipList();
        return new ResponseEntity<>(RestResponse.of(scholarshipResponseList), HttpStatus.OK);
    }
    
    @GetMapping(path = "/getScholarshipExpenseList")
    public ResponseEntity<RestResponse<List<ScholarshipExpenseResponse>>> getScholarshipExpenseList() {
        List<ScholarshipExpenseResponse> scholarshipExpenseResponseList = scholarshipService.getScholarshipExpenseList();
        return new ResponseEntity<>(RestResponse.of(scholarshipExpenseResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('SCHOLARSHIP_UPDATE')")
    @PutMapping("/updateScholarshipById/{id}")
    public ResponseEntity<RestResponse<ScholarshipResponse>> updateScholarshipById(@PathVariable Long id, @RequestBody ScholarshipRequest scholarshipRequest) {
        ScholarshipResponse updatedScholarshipResponse = scholarshipService.updateScholarshipById(id, scholarshipRequest);
        return new ResponseEntity<>(RestResponse.of(updatedScholarshipResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('SCHOLARSHIP_DELETE')")
    @DeleteMapping(path = "/deleteScholarshipById/{id}")
    public ResponseEntity<RestResponse<ScholarshipResponse>> deleteScholarshipById(@PathVariable Long id) {
        ScholarshipResponse deletedScholarshipResponse = scholarshipService.deleteScholarshipById(id);
        return new ResponseEntity<>(RestResponse.of(deletedScholarshipResponse), HttpStatus.OK);
    }

}
