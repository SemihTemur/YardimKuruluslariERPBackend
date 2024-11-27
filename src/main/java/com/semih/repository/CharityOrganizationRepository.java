package com.semih.repository;

import com.semih.model.CharityOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharityOrganizationRepository extends JpaRepository<CharityOrganization, Long> {

   int deleteCharityOrganizationByorganizationName(String organizationName);

   CharityOrganization findCharityOrganizationByOrganizationName(String organizationName);

}
