package com.semih.controller;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.FamilyNameResponse;
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

    @PostMapping(path = "/saveFamily")
    public void saveFamily(@RequestBody FamilyRequest familyRequest) {
        familyService.saveFamily(familyRequest);
    }

    @GetMapping(path = "/getFamilyList")
    public List<FamilyResponse> getFamilyList() {
        return familyService.getFamilyList();
    }

    @GetMapping(path = "/getFamilyById/{id}")
    public FamilyResponse getFamilyById(@PathVariable Long id) {
        return familyService.getFamilyById(id);
    }

    @GetMapping(path = "/getFamilyNamesList")
    public List<FamilyNameResponse> getFamilyNames() {
        return familyService.getFamilyNames();
    }

    @PutMapping(path = "updateFamilyById/{id}")
    public void updateFamilyById(@PathVariable Long id, @RequestBody FamilyRequest FamilyRequest) {
        familyService.updateFamilyById(id, FamilyRequest);
    }

    @DeleteMapping(path = "/deleteFamilyById/{id}")
    public void deleteFamilyById(@PathVariable Long id) {
        familyService.deleteFamilyById(id);
    }

    @DeleteMapping(path = "deleteAllFamily")
    public void deleteAllFamily() {
        familyService.deleteAllFamily();
    }

}
