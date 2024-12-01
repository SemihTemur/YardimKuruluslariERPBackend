package com.semih.controller;

import com.semih.dto.request.StudentRequest;
import com.semih.dto.request.VolunteerRequest;
import com.semih.dto.response.StudentResponse;
import com.semih.dto.response.VolunteerResponse;
import com.semih.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping(path="/saveVolunteer")
    public void saveVolunteer(@RequestBody List<VolunteerRequest> volunteerRequestList) {
        volunteerService.saveVolunteer(volunteerRequestList);
    }

    @GetMapping(path="/getVolunteerList")
    public List<VolunteerResponse> getVolunteerList() {
        return volunteerService.getVolunteerList();
    }

    @GetMapping(path="/getVolunteerById/{id}")
    public VolunteerResponse getVolunteerById(@PathVariable Long id) {
        return volunteerService.getVolunteerById(id);
    }

    @GetMapping(path="/getVolunteerByNameAndSurname")
    public VolunteerResponse getVolunteerByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return volunteerService.getVolunteerByNameAndSurname(name, surname);
    }

    @PutMapping(path="updateVolunteerById/{id}")
    public void updateVolunteerById(@PathVariable Long id, @RequestBody VolunteerRequest volunteerRequest) {
        volunteerService.updateVolunteerById(id, volunteerRequest);
    }

    @PutMapping(path="/updateVolunteerByNameAndSurname")
    public void updateVolunteerByNameAndSurname(@RequestParam String name, @RequestParam String surname,@RequestBody VolunteerRequest volunteerRequest ) {
        volunteerService.updateVolunteerByNameAndSurname(name, surname, volunteerRequest);
    }

    @DeleteMapping(path="/deleteVolunteerById/{id}")
    public String deleteVolunteerById(@PathVariable Long id) {
        if(volunteerService.deleteVolunteerById(id)) {
            return "Volunteer deleted successfully";
        }
        return "Volunteer deletion failed";
    }

    @DeleteMapping(path="/deleteVolunteerByNameAndSurname")
    public String deleteVolunteerByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        if(volunteerService.deleteVolunteerByNameAndSurname(name, surname)) {
            return "Volunteer deleted successfully";
        }
        return "Volunteer deletion failed";
    }

}
