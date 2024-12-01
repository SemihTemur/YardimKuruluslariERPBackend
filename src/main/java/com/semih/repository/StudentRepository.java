package com.semih.repository;

import com.semih.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByNameAndSurname(String name, String surname);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    int deleteByIdAndReturn(@Param("id") Long id);

    int deleteByNameAndSurname(String name, String surname);
}
