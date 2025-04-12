package com.semih.service;

import com.semih.dto.request.TreasuryRequest;
import com.semih.model.Treasury;
import com.semih.repository.TreasuryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TreasuryService {

    private final TreasuryRepository treasuryRepository;

    private Treasury mapDtoToEntity(TreasuryRequest treasuryRequest) {
        return new Treasury(
                treasuryRequest.getBalance()
        );
    }

//    private TreasuryResponse mapEntityToResponse(Treasury treasury) {
//        return new TreasuryResponse(
//                treasury.getBalance()
//        );
//    }

    public TreasuryService(TreasuryRepository treasuryRepository) {
        this.treasuryRepository = treasuryRepository;
    }

    public void saveTreasury(TreasuryRequest treasuryRequest) {
        treasuryRepository.save(mapDtoToEntity(treasuryRequest));
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
