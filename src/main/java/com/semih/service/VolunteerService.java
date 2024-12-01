package com.semih.service;

import com.semih.dto.request.VolunteerRequest;
import com.semih.dto.response.VolunteerResponse;
import com.semih.model.Volunteer;
import com.semih.repository.VolunteerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final ModelMapper modelMapper;

    public VolunteerService(VolunteerRepository volunteerRepository, ModelMapper modelMapper) {
        this.volunteerRepository = volunteerRepository;
        this.modelMapper = modelMapper;
    }

    // Post
    public void saveVolunteer(List<VolunteerRequest> volunteerRequestList){
        for(VolunteerRequest volunteerRequest:volunteerRequestList){
            volunteerRepository.save(modelMapper.map(volunteerRequest, Volunteer.class));
        }
    }

    // Get
    public List<VolunteerResponse> getVolunteerList(){
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        List<VolunteerResponse> volunteerResponseList = new ArrayList<>();
        for(Volunteer volunteer:volunteerList){
            volunteerResponseList.add(modelMapper.map(volunteer, VolunteerResponse.class));
        }
        return volunteerResponseList;
    }

    public VolunteerResponse getVolunteerById(Long id){
        Optional<Volunteer> volunteer  = volunteerRepository.findById(id);
        if(volunteer.isPresent()){
            return modelMapper.map(volunteer.get(), VolunteerResponse.class);
        }
        return null;
    }

    public VolunteerResponse getVolunteerByNameAndSurname(String name, String surname){
        Optional<Volunteer> volunteer  = volunteerRepository.findByNameAndSurname(name, surname);
        if(volunteer.isPresent()){
            return modelMapper.map(volunteer.get(), VolunteerResponse.class);
        }
        return null;
    }

    //update
    public void updateVolunteerById(Long id, VolunteerRequest volunteerRequest){
        Optional<Volunteer> volunteer  = volunteerRepository.findById(id);
        if(volunteer.isPresent()){
            modelMapper.map(volunteerRequest, volunteer.get());
            volunteerRepository.save(volunteer.get());
        }
        System.out.println("Bulunamadi");
    }

    public void updateVolunteerByNameAndSurname(String name, String surname, VolunteerRequest volunteerRequest){
        Optional<Volunteer> volunteer  = volunteerRepository.findByNameAndSurname(name, surname);
        if(volunteer.isPresent()){
            modelMapper.map(volunteerRequest, volunteer.get());
            volunteerRepository.save(volunteer.get());
        }
        System.out.println("Bulunamadi");
    }


    // Delete
    @Transactional
    public boolean deleteVolunteerById(Long id){
        return volunteerRepository.deleteByIdAndReturn(id)>0;
    }

    @Transactional
    public boolean deleteVolunteerByNameAndSurname(String name, String surname){
        return volunteerRepository.deleteByNameAndSurname(name,surname)>0;
    }

}
