package com.semih.repository;

import com.semih.model.EventVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventVolunteerRepository extends JpaRepository<EventVolunteer, Long> {
}
