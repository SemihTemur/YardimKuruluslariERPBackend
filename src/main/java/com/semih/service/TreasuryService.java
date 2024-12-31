package com.semih.service;

import com.semih.dto.request.TreasuryRequest;
import com.semih.model.Treasury;
import com.semih.repository.TreasuryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TreasuryService {

    private final TreasuryRepository treasuryRepository;
    private final ModelMapper modelMapper;

    public TreasuryService(TreasuryRepository treasuryRepository,ModelMapper modelMapper) {
        this.treasuryRepository = treasuryRepository;
        this.modelMapper = modelMapper;
    }

    public void saveTreasury(TreasuryRequest treasuryRequest) {
        treasuryRepository.save(modelMapper.map(treasuryRequest, Treasury.class));
    }

    public void updateTreasury(BigDecimal updatedCashBalance) {
        Treasury treasury = new Treasury();
        treasury.setBalance(updatedCashBalance);
        treasuryRepository.save(treasury);
    }

    public BigDecimal getTreasuryBalance() {
        return treasuryRepository.findById(1l).map(Treasury::getBalance).orElse(BigDecimal.ZERO);
    }

}
