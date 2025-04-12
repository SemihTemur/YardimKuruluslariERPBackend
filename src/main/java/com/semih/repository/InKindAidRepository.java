package com.semih.repository;

import com.semih.model.InKindAid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InKindAidRepository extends JpaRepository<InKindAid, Long> {
}
