package com.semih.repository;

import com.semih.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family,Long> {

    Optional<Family> findByFamilyName(String familyName);

    @Modifying
    @Query("Delete from Family f where f.id=:familyId")
    int deleteByFamilyIdAndReturn(@Param("familyId") Long familyId);

    int  deleteByFamilyName(String familyName);

}
