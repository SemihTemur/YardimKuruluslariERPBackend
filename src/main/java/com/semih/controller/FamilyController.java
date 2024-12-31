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

    @PostMapping(path = "/saveFamily")
    public String saveFamily(@RequestBody FamilyRequest familyRequest) {
        if (familyService.saveFamily(familyRequest)) {
            return "success";
        }
        return "fail";
    }

    @GetMapping(path = "/getFamilyList")
    public List<FamilyResponse> getFamilyList() {
        return familyService.getFamilyList();
    }

    @GetMapping(path = "/getFamilyById/{id}")
    public FamilyResponse getFamilyById(@PathVariable Long id) {
        return familyService.getFamilyById(id);
    }

    @PutMapping(path = "updateFamilyById/{id}")
    public String updateFamilyById(@PathVariable Long id, @RequestBody FamilyRequest FamilyRequest) {
        if (familyService.updateFamilyById(id, FamilyRequest)) {
            return "success";
        }
        return "fail";
    }

    @DeleteMapping(path = "/deleteFamilyById/{id}")
    public String deleteFamilyById(@PathVariable Long id) {
        if (familyService.deleteFamilyById(id)) {
            return "Family deleted successfully";
        }
        return "Family deletion failed";
    }

    @DeleteMapping(path = "deleteAllFamily")
    public String deleteAllFamily() {
        familyService.deleteAllFamily();
        return "All family deleted successfully";
    }

}
