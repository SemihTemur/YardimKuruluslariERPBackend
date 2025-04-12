package com.semih.repository;

import com.semih.model.InKindDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InKindDonationRepository extends JpaRepository<InKindDonation, Long> {
}
