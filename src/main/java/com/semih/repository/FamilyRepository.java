package com.semih.repository;

import com.semih.dto.response.FamilyNameResponse;
import com.semih.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByFamilyName(String familyName);

    @Query("Select new com.semih.dto.response.FamilyNameResponse(f.familyName) from Family f")
    List<FamilyNameResponse> getFamilyNames();
}
