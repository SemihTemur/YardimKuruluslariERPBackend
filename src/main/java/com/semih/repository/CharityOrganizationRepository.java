package com.semih.repository;

import com.semih.model.CharityOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CharityOrganizationRepository extends JpaRepository<CharityOrganization, Long> {

   int deleteCharityOrganizationByCharityOrganizationName(String charityOrganizationName);

   CharityOrganization findCharityOrganizationByCharityOrganizationName(String charityOrganizationName);

}
