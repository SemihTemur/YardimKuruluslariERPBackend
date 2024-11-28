package com.semih.service;

import com.semih.model.CharityOrganization;
import com.semih.model.Income;
import com.semih.repository.CharityOrganizationRepository;
import com.semih.repository.IncomeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CharityOrganizationRepository charityOrganizationRepository;

    public IncomeService(IncomeRepository incomeRepository, CharityOrganizationRepository charityOrganizationRepository) {
        this.incomeRepository = incomeRepository;
        this.charityOrganizationRepository = charityOrganizationRepository;
    }

    public void saveIncome(IncomeRequest income) {
       Optional<CharityOrganization>  charityOrganization = charityOrganizationRepository.findById(income.getId());
        Income incomes = new Income();
       if (charityOrganization.isPresent()) {
           BeanUtils.copyProperties(income, incomes);
           incomes.setCharityOrganization(charityOrganization.get());
           incomeRepository.save(incomes);
       }
    }
}
