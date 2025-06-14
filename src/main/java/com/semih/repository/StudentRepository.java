package com.semih.repository;

import com.semih.dto.response.StudentNameResponse;
import com.semih.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByNameAndSurname(String name, String surname);

    Boolean existsByNameAndSurname(String name, String surname);

    Boolean existsByTckn(String tckn);

    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    @Query("Select new com.semih.dto.response.StudentNameResponse(s.name,s.surname) from Student s")
    List<StudentNameResponse> getStudentNameAndSurnameList();

    @Query("Select count(s) from Student s")
    Long getStudentCount();

}
