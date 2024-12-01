package com.semih.repository;

import com.semih.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

   Optional<Volunteer> findByNameAndSurname(String name, String surname);

    @Modifying
   @Query("Delete from Volunteer v where v.id=:id")
   int deleteByIdAndReturn(Long id);

    int deleteByNameAndSurname(String name, String surname);
}
