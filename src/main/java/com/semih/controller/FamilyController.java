package com.semih.controller;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.FamilyNameResponse;
import com.semih.dto.response.FamilyResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_SAVE')")
    @PostMapping(path = "/saveFamily")
    public ResponseEntity<RestResponse<FamilyResponse>> saveFamily(@RequestBody FamilyRequest familyRequest) {
        FamilyResponse savedFamilyResponse = familyService.saveFamily(familyRequest);
        return new ResponseEntity<>(RestResponse.of(savedFamilyResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_LIST')")
    @GetMapping(path = "/getFamilyList")
    public ResponseEntity<RestResponse<List<FamilyResponse>>> getFamilyList() {
        List<FamilyResponse> familyResponseList = familyService.getFamilyList();
        return new ResponseEntity<>(RestResponse.of(familyResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_LIST')")
    @GetMapping(path = "/getFamilyById/{id}")
    public ResponseEntity<RestResponse<FamilyResponse>> getFamilyById(@PathVariable Long id) {
        FamilyResponse familyResponse = familyService.getFamilyById(id);
        return new ResponseEntity<>(RestResponse.of(familyResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_LIST') or hasAuthority('CASHAID_LIST') or hasAuthority('INKINDAID_LIST')")
    @GetMapping(path = "/getFamilyNamesList")
    public List<FamilyNameResponse> getFamilyNames() {
        return familyService.getFamilyNames();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_UPDATE')")
    @PutMapping(path = "updateFamilyById/{id}")
    public ResponseEntity<RestResponse<FamilyResponse>> updateFamilyById(@PathVariable Long id, @RequestBody FamilyRequest FamilyRequest) {
        FamilyResponse updatedFamilyResponse = familyService.updateFamilyById(id, FamilyRequest);
        return new ResponseEntity<>(RestResponse.of(updatedFamilyResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_DELETE')")
    @DeleteMapping(path = "/deleteFamilyById/{id}")
    public ResponseEntity<RestResponse<FamilyResponse>> deleteFamilyById(@PathVariable Long id) {
        FamilyResponse deletedFamilyResponse = familyService.deleteFamilyById(id);
        return new ResponseEntity<>(RestResponse.of(deletedFamilyResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasAuthority('FAMILY_DELETE')")
    @DeleteMapping(path = "deleteAllFamily")
    public ResponseEntity<RestResponse<List<FamilyResponse>>> deleteAllFamily() {
        List<FamilyResponse> deletedFamilyResponseList = familyService.deleteAllFamily();
        return new ResponseEntity<>(RestResponse.of(deletedFamilyResponseList), HttpStatus.OK);
    }

}
