package com.semih.service;

import com.semih.repository.EventVolunteerRepository;
import org.springframework.stereotype.Service;

@Service
public class EventVolunteerService {

    private final EventVolunteerRepository eventVolunteerRepository;

    public EventVolunteerService(EventVolunteerRepository eventVolunteerRepository) {
        this.eventVolunteerRepository = eventVolunteerRepository;
    }
}
