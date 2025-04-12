package com.semih.controller;

import com.semih.dto.request.TreasuryRequest;
import com.semih.service.TreasuryService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/rest/api")
public class TreasuryController {

    private final TreasuryService treasuryService;

    public TreasuryController(TreasuryService treasuryService) {
        this.treasuryService = treasuryService;
    }

    @PostMapping(path = "/saveTreasury")
    public void saveTreasury(@RequestBody TreasuryRequest treasuryRequest) {
        treasuryService.saveTreasury(treasuryRequest);
    }

    @GetMapping(path = "/getTreasuryBalance")
    public BigDecimal getTreasuryBalance() {
        return treasuryService.getTreasuryBalance();
    }


}
