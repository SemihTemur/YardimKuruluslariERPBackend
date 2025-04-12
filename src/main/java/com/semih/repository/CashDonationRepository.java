package com.semih.repository;

import com.semih.model.CashDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashDonationRepository extends JpaRepository<CashDonation, Long> {

}
