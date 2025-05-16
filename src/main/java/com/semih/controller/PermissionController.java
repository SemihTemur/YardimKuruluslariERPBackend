package com.semih.controller;

import com.semih.dto.request.PermissionRequest;
import com.semih.dto.response.PermissionResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('AUTHORIZATION_SAVE')")
    @PostMapping("/savePermission")
    public ResponseEntity<RestResponse<PermissionResponse>> savePermission(@RequestBody PermissionRequest permissionRequest) {
        PermissionResponse permissionResponse = permissionService.savePermission(permissionRequest);
        return new ResponseEntity<>(RestResponse.of(permissionResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('AUTHORIZATION_LIST')")
    @GetMapping("/getPermissionList")
    public ResponseEntity<RestResponse<List<PermissionResponse>>> getPermissionList() {
        List<PermissionResponse> permissionList = permissionService.getPermissionList();
        return new ResponseEntity<>(RestResponse.of(permissionList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('AUTHORIZATION_LIST')")
    @GetMapping("/getPermissionById/{id}")
    public ResponseEntity<RestResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        PermissionResponse permissionResponse = permissionService.getPermissionById(id);
        return new ResponseEntity<>(RestResponse.of(permissionResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('AUTHORIZATION_UPDATE')")
    @PutMapping("/updatePermissionById/{id}")
    public ResponseEntity<RestResponse<PermissionResponse>> updatePermissionById(@PathVariable Long id, @RequestBody PermissionRequest permission) {
        PermissionResponse permissionResponse = permissionService.updatePermissionById(id, permission);
        return new ResponseEntity<>(RestResponse.of(permissionResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('AUTHORIZATION_DELETE')")
    @DeleteMapping("/deletePermissionById/{id}")
    public ResponseEntity<RestResponse<PermissionResponse>> deletePermissionById(@PathVariable Long id) {
        PermissionResponse permissionResponse = permissionService.deletePermissionById(id);
        return new ResponseEntity<>(RestResponse.of(permissionResponse), HttpStatus.OK);
    }

//    @DeleteMapping("/deletePermission/{id}")
//    public void deletePermission(@PathVariable Long id) {
//        permissionService.deletePermission(id);
//    }
//
//    @GetMapping("/getPermissionById/{id}")
//    public ResponseEntity<RestResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
//        PermissionResponse permissionResponse = permissionService.getPermissionById(id);
//        return new ResponseEntity<>(RestResponse.of(permissionResponse), HttpStatus.OK);
//    }

}
