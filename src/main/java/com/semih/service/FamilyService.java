package com.semih.service;

import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.FamilyNameResponse;
import com.semih.dto.response.FamilyResponse;
import com.semih.exception.ConflictException;
import com.semih.exception.NotFoundException;
import com.semih.model.Address;
import com.semih.model.Auditable;
import com.semih.model.Family;
import com.semih.repository.FamilyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyService {

    private final FamilyRepository familyRepository;

    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;

    }

    private Family mapToEntity(FamilyRequest familyRequest) {
        return new Family(
                familyRequest.familyName(),
                familyRequest.familyMemberCount(),
                familyRequest.phoneNumber(),
                familyRequest.email(),
                new Address(
                        familyRequest.address().city(),
                        familyRequest.address().district(),
                        familyRequest.address().neighborhood(),
                        familyRequest.address().street(),
                        familyRequest.address().userType()
                )
        );
    }

    private FamilyResponse mapToResponse(Family family) {
        return new FamilyResponse(
                new BaseResponse(family.getId(),
                        family.getCreatedDate(),
                        family.getModifiedDate()),
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

    private void validateUniqueness(FamilyRequest familyRequest) {
        if (familyRepository.existsByFamilyName(familyRequest.familyName())) {
            throw new ConflictException("Aile adı zaten mevcut!!!");
        }

        if (familyRepository.existsByPhoneNumber(familyRequest.phoneNumber())) {
            throw new ConflictException("Telefon numarası zaten mevcut!!!");
        }

        if (familyRepository.existsByEmail(familyRequest.email())) {
            throw new ConflictException("Email zaten mevcut!!!");
        }

    }


    @Auditable(actionType = "Ekledi", targetEntity = "Aile")
    public FamilyResponse saveFamily(FamilyRequest familyRequest) {
        validateUniqueness(familyRequest);
        Family savedFamily = familyRepository.save(mapToEntity(familyRequest));
        return mapToResponse(savedFamily);
    }

    // Get
    public List<FamilyResponse> getFamilyList() {
        List<Family> familyList = familyRepository.findAll();
        List<FamilyResponse> familyResponseList = new ArrayList<>();
        for (Family family : familyList) {
            familyResponseList.add(mapToResponse(family));
        }
        return familyResponseList;
    }

    public FamilyResponse getFamilyById(Long id) {
        Family family = familyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aile bulunamadı!!!" + id));
        return mapToResponse(family);
    }

    public Family getFamilyByName(String name) {
        return familyRepository.findByFamilyName(name)
                .orElseThrow(() -> new NotFoundException("Aile bulunamadı!!!" + name));
    }

    public List<FamilyNameResponse> getFamilyNames() {
        return familyRepository.getFamilyNames();
    }

    public Long getFamilyCount() {
        return familyRepository.count();
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Aile")
    public FamilyResponse updateFamilyById(Long id, FamilyRequest familyRequest) {
        Family existingFamily = familyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aile bulunamadı!!!" + id));

//        validateUniqueness(familyRequest);

        Family updatedFamily = mapToEntity(familyRequest);
        updatedFamily.setId(id);
        updatedFamily.setCreatedDate(existingFamily.getCreatedDate());
        updatedFamily = familyRepository.save(updatedFamily);
        return mapToResponse(updatedFamily);
    }


    @Auditable(actionType = "Sildi", targetEntity = "Aile")
    @Transactional
    public FamilyResponse deleteFamilyById(Long id) {
        Family deletedFamily = familyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aile bulunamadı!!!" + id));
        familyRepository.delete(deletedFamily);
        return mapToResponse(deletedFamily);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Aile")
    public List<FamilyResponse> deleteAllFamily() {
        List<FamilyResponse> familyResponseList = getFamilyList();
        familyRepository.deleteAll();
        return familyResponseList;
    }

}
