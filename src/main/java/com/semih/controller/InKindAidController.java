package com.semih.controller;

import com.semih.dto.request.InKindAidRequest;
import com.semih.dto.response.InKindAidResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.InKindAidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class InKindAidController {

    private final InKindAidService inKindAidService;

    public InKindAidController(InKindAidService inKindAidService) {
        this.inKindAidService = inKindAidService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDAID_SAVE')")
    @PostMapping(path = "/saveInKindAid")
    public ResponseEntity<RestResponse<InKindAidResponse>> saveInKindAid(@RequestBody InKindAidRequest inKindAidRequest) {
        InKindAidResponse savedInKindAidResponse = inKindAidService.saveInKindAid(inKindAidRequest);
        return new ResponseEntity<>(RestResponse.of(savedInKindAidResponse), HttpStatus.OK);
    }
    
    @GetMapping(path = "/getInKindAidList")
    public ResponseEntity<RestResponse<List<InKindAidResponse>>> getInKindAidList() {
        List<InKindAidResponse> inKindAidResponseList = inKindAidService.getInKindAidList();
        return new ResponseEntity<>(RestResponse.of(inKindAidResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDAID_UPDATE')")
    @PutMapping(path = "/updateInKindAidById/{id}")
    public ResponseEntity<RestResponse<InKindAidResponse>> updateInKindAidById(@PathVariable Long id, @RequestBody InKindAidRequest inKindAidRequest) {
        InKindAidResponse updatedInKindAid = inKindAidService.updateInKindAidById(id, inKindAidRequest);
        return new ResponseEntity<>(RestResponse.of(updatedInKindAid), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INKINDAID_DELETE')")
    @DeleteMapping(path = "/deleteInKindAidById/{id}")
    public ResponseEntity<RestResponse<InKindAidResponse>> deleteInKindAidById(@PathVariable Long id) {
        InKindAidResponse deletedInKindAidResponse = inKindAidService.deleteInKindAidById(id);
        return new ResponseEntity<>(RestResponse.of(deletedInKindAidResponse), HttpStatus.OK);
    }

}
