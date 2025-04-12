package com.semih.repository;

import com.semih.dto.response.DonorNameResponse;
import com.semih.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {


    Optional<Donor> findByFirstNameAndLastName(String donorName, String lastName);

    @Query("SELECT new com.semih.dto.response.DonorNameResponse(d.firstName, d.lastName) FROM Donor d")
    List<DonorNameResponse> getDonorNameAndSurnameList();


}
