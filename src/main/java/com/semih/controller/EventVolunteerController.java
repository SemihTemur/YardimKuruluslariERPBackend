package com.semih.controller;

import com.semih.service.EventVolunteerService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventVolunteerController {

    private final EventVolunteerService eventVolunteerService;

    public EventVolunteerController(EventVolunteerService eventVolunteerService) {
        this.eventVolunteerService = eventVolunteerService;
    }
}
