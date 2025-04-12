package com.semih.controller;

import com.semih.dto.response.InventoryResponse;
import com.semih.service.InventoryService;
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

    @GetMapping(path="/getInventoryList")
    public List<InventoryResponse> getInventoryList() {
       return inventoryService.getAllInventory();
    }
}
