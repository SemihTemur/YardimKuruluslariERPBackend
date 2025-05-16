package com.semih.controller;

import com.semih.dto.request.RoleRequest;
import com.semih.dto.response.RestResponse;
import com.semih.dto.response.RoleResponse;
import com.semih.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ROLE_SAVE')")
    @PostMapping(path = "/saveRole")
    public ResponseEntity<RestResponse<RoleResponse>> saveRole(@RequestBody RoleRequest roleRequest) {
        RoleResponse savedRoleResponse = roleService.saveRole(roleRequest);
        return new ResponseEntity<>(RestResponse.of(savedRoleResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('ROLE_SAVE')")
    @GetMapping(path = "/getRoleList")
    public ResponseEntity<RestResponse<List<RoleResponse>>> getRoleList() {
        List<RoleResponse> roleResponseList = roleService.getRoleList();
        return new ResponseEntity<>(RestResponse.of(roleResponseList), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ROLE_LIST')")
    @GetMapping(path = "/getRoles")
    public List<String> getRoles() {
        return roleService.getRoles();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('ROLE_LIST')")
    @GetMapping(path = "/getRoleById/{id}")
    public ResponseEntity<RestResponse<RoleResponse>> getRoleById(@PathVariable Long id) {
        RoleResponse roleResponse = roleService.getRoleById(id);
        return new ResponseEntity<>(RestResponse.of(roleResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') || hasRole('ROLE_UPDATE')")
    @PutMapping(path = "/updateRoleById/{id}")
    public ResponseEntity<RestResponse<RoleResponse>> updateRoleById(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        RoleResponse updatedRoleResponse = roleService.updateRoleById(id, roleRequest);
        return new ResponseEntity<>(RestResponse.of(updatedRoleResponse), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')|| hasRole('ROLE_DELETE')")
    @DeleteMapping(path = "/deleteRoleById/{id}")
    public ResponseEntity<RestResponse<RoleResponse>> deleteRoleById(@PathVariable Long id) {
        RoleResponse deletedRoleResponse = roleService.deleteRoleById(id);
        return new ResponseEntity<>(RestResponse.of(deletedRoleResponse), HttpStatus.OK);
    }


}
