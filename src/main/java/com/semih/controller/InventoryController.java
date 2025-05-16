package com.semih.controller;

import com.semih.dto.response.InventoryResponse;
import com.semih.dto.response.RestResponse;
import com.semih.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')  or hasAuthority('INVENTORY_LIST')")
    @GetMapping(path = "/getInventoryList")
    public ResponseEntity<RestResponse<List<InventoryResponse>>> getInventoryList() {
        List<InventoryResponse> inventoryResponseList = inventoryService.getInventoryList();
        return new ResponseEntity<>(RestResponse.of(inventoryResponseList), HttpStatus.OK);
    }
}
