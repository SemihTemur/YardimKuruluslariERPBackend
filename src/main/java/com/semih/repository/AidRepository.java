package com.semih.repository;

import com.semih.model.Aid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AidRepository extends JpaRepository<Aid, Long> {
}
