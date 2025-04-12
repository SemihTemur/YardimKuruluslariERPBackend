package com.semih.service;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.FamilyNameResponse;
import com.semih.dto.response.FamilyResponse;
import com.semih.model.Address;
import com.semih.model.Family;
import com.semih.repository.FamilyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyService(FamilyRepository familyRepository, ModelMapper modelMapper) {
        this.familyRepository = familyRepository;

    }

    private Family toEntity(FamilyRequest familyRequest) {
        return new Family(
                familyRequest.familyName(),
                familyRequest.familyMemberCount(),
                familyRequest.phoneNumber(),
                familyRequest.email(),
                new Address(
                        familyRequest.address().getCity(),
                        familyRequest.address().getDistrict(),
                        familyRequest.address().getNeighborhood(),
                        familyRequest.address().getStreet(),
                        familyRequest.address().getUserType()
                )
        );
    }

    private FamilyResponse toResponse(Family family) {
        return new FamilyResponse(
                family.getId(),
                family.getCreatedDate(),
                family.getModifiedDate(),
                family.getFamilyName(),
                family.getFamilyMemberCount(),
                family.getPhoneNumber(),
                family.getEmail(),
                new AddressResponse(
                        family.getAddress().getCity(),
                        family.getAddress().getDistrict(),
                        family.getAddress().getNeighborhood(),
                        family.getAddress().getStreet()
                )
        );
    }


    // Post
    public void saveFamily(FamilyRequest familyRequest) {
        Family family = toEntity(familyRequest);
        familyRepository.save(family);
    }

    // Get
    public List<FamilyResponse> getFamilyList() {
        List<Family> familyList = familyRepository.findAll();
        List<FamilyResponse> familyResponseList = new ArrayList<>();
        for (Family family : familyList) {
            familyResponseList.add(toResponse(family));
        }
        return familyResponseList;
    }

    public FamilyResponse getFamilyById(Long id) {
        Family family = familyRepository.findById(id).orElseThrow(null);
        return toResponse(family);
    }

    public Family getFamilyByName(String name) {
        return familyRepository.findByFamilyName(name).orElseThrow(() -> new RuntimeException("Bulunamadi"));
    }

    public List<FamilyNameResponse> getFamilyNames() {
        return familyRepository.getFamilyNames();
    }

    //update
    public void updateFamilyById(Long id, FamilyRequest familyRequest) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("aile bulunamadi"));
        Family updatedFamily = toEntity(familyRequest);
        updatedFamily.setId(id);
        updatedFamily.setCreatedDate(family.getCreatedDate());
        familyRepository.save(updatedFamily);

    }

    // Delete
    @Transactional
    public void deleteFamilyById(Long id) {
        familyRepository.deleteById(id);
    }

    public void deleteAllFamily() {
        familyRepository.deleteAll();
    }

}
