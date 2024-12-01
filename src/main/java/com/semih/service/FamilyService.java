package com.semih.service;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.FamilyResponse;
import com.semih.model.Family;
import com.semih.repository.FamilyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final ModelMapper modelMapper;

    public FamilyService(FamilyRepository familyRepository, ModelMapper modelMapper) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
    }

    // Post
    public void saveFamily(List<FamilyRequest> familyRequestList){
        for(FamilyRequest familyRequest:familyRequestList){
            familyRepository.save(modelMapper.map(familyRequest, Family.class));
        }
    }

    // Get
    public List<FamilyResponse> getFamilyList(){
        List<Family> familyList = familyRepository.findAll();
        List<FamilyResponse> familyResponseList = new ArrayList<>();
        for(Family family:familyList){
            familyResponseList.add(modelMapper.map(family, FamilyResponse.class));
        }
        return familyResponseList;
    }

    public FamilyResponse getFamilyById(Long id){
        Optional<Family> family  = familyRepository.findById(id);
        if(family.isPresent()){
            return modelMapper.map(family.get(), FamilyResponse.class);
        }
        return null;
    }

    public FamilyResponse getFamilyByFamilyName(String familyName){
        Optional<Family> family  = familyRepository.findByFamilyName(familyName);
        if(family.isPresent()){
            return modelMapper.map(family.get(), FamilyResponse.class);
        }
        return null;
    }

    //update
    public void updateFamilyById(Long id, FamilyRequest familyRequest){
        Optional<Family> family  = familyRepository.findById(id);
        if(family.isPresent()){
            modelMapper.map(familyRequest, family.get());
            familyRepository.save(family.get());
        }
        System.out.println("Bulunamadi");
    }

    public void updateFamilyByFamilyName(String familyName, FamilyRequest familyRequest){
        Optional<Family> family  = familyRepository.findByFamilyName(familyName);
        if(family.isPresent()){
            modelMapper.map(familyRequest, family.get());
            familyRepository.save(family.get());
        }
        System.out.println("Bulunamadi");
    }


    // Delete
    @Transactional
    public boolean deleteFamilyById(Long id){
        return familyRepository.deleteByFamilyIdAndReturn(id)>0;
    }

    @Transactional
    public boolean deleteFamilyByFamilyName(String familyName){
        return familyRepository.deleteByFamilyName(familyName)>0;
    }

}
