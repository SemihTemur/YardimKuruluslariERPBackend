package com.semih.controller;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.FamilyResponse;
import com.semih.service.FamilyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping(path="/saveFamily")
    public void saveFamily(@RequestBody List<FamilyRequest> familyRequestList) {
        familyService.saveFamily(familyRequestList);
    }

    @GetMapping(path="/getFamilyList")
    public List<FamilyResponse> getFamilyList() {
        return familyService.getFamilyList();
    }

    @GetMapping(path="/getFamilyById/{id}")
    public FamilyResponse getFamilyById(@PathVariable Long id) {
        return familyService.getFamilyById(id);
    }

    @GetMapping(path="/getFamilyByFamilyName")
    public FamilyResponse getFamilyByFamilyName(@RequestParam String familyName) {
        return familyService.getFamilyByFamilyName(familyName);
    }

    @PutMapping(path="updateFamilyById/{id}")
    public void updateFamilyById(@PathVariable Long id, @RequestBody FamilyRequest FamilyRequest) {
        familyService.updateFamilyById(id, FamilyRequest);
    }

    @PutMapping(path="/updateFamilyByFamilyName")
    public void updateFamilyByFamilyName(@RequestParam String  familyName, @RequestBody FamilyRequest familyRequest ) {
        familyService.updateFamilyByFamilyName(familyName, familyRequest);
    }

    @DeleteMapping(path="/deleteFamilyById/{id}")
    public String deleteFamilyById(@PathVariable Long id) {
        if(familyService.deleteFamilyById(id)) {
            return "Family deleted successfully";
        }
        return "Family deletion failed";
    }

    @DeleteMapping(path="/deleteFamilyByFamilyName")
    public String deleteFamilyByFamilyName(@RequestParam String familyName) {
        if(familyService.deleteFamilyByFamilyName(familyName)) {
            return "Family deleted successfully";
        }
        return "Family deletion failed";
    }

}
