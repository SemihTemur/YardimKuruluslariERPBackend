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
    public boolean saveFamily(FamilyRequest familyRequest){
       Family savedFamily = familyRepository.save(modelMapper.map(familyRequest, Family.class));
       return savedFamily != null;
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
        Family family  = familyRepository.findById(id).orElseThrow(null);
        return modelMapper.map(family, FamilyResponse.class);
    }

    //update
    public boolean updateFamilyById(Long id, FamilyRequest familyRequest){
        Family family  = familyRepository.findById(id).orElseThrow(()->new RuntimeException("aile bulunamadi"));
        modelMapper.map(familyRequest, family);
        familyRepository.save(family);
        return true;

    }

    // Delete
    @Transactional
    public boolean deleteFamilyById(Long id){
        familyRepository.deleteById(id);

        Optional<Family> family  = familyRepository.findById(id);

        return family.isPresent();
    }

    public void deleteAllFamily(){
        familyRepository.deleteAll();
    }

}
