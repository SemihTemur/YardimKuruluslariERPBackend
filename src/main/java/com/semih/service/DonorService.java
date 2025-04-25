package com.semih.service;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.DonorNameResponse;
import com.semih.dto.response.DonorResponse;
import com.semih.exception.NotFoundException;
import com.semih.model.Address;
import com.semih.model.Donor;
import com.semih.repository.DonorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    private Donor mapToEntity(DonorRequest donorRequest) {
        return new Donor(
                donorRequest.firstName(),
                donorRequest.lastName(),
                donorRequest.phoneNumber(),
                donorRequest.email(),
                donorRequest.genderType(),
                new Address(
                        donorRequest.address().city(),
                        donorRequest.address().district(),
                        donorRequest.address().neighborhood(),
                        donorRequest.address().street(),
                        donorRequest.address().userType()
                )
        );
    }

    private DonorResponse mapToResponse(Donor donor) {
        return new DonorResponse(
                new BaseResponse(donor.getId(),
                        donor.getCreatedDate(),
                        donor.getModifiedDate()),
                donor.getFirstName(),
                donor.getLastName(),
                donor.getPhoneNumber(),
                donor.getEmail(),
                donor.getGenderType(),
                new AddressResponse(
                        donor.getAddress().getCity(),
                        donor.getAddress().getDistrict(),
                        donor.getAddress().getNeighborhood(),
                        donor.getAddress().getStreet()
                )
        );
    }

    public DonorResponse saveDonor(DonorRequest donorRequest) {
        Donor savedDonor = donorRepository.save(mapToEntity(donorRequest));
        return mapToResponse(savedDonor);
    }

    // Get
    public List<DonorResponse> getDonorList() {
        List<Donor> donorList = donorRepository.findAll();
        List<DonorResponse> donorResponseList = new ArrayList<>();
        for (Donor donor : donorList) {
            donorResponseList.add(mapToResponse(donor));
        }
        return donorResponseList;
    }

    public DonorResponse getDonorById(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bağışçı bulunamadı!!!" + id));
        return mapToResponse(donor);
    }

    public List<DonorNameResponse> getDonorNameAndSurnameList() {
        return donorRepository.getDonorNameAndSurnameList();
    }

    public Donor getDonorByFirstNameAndLastName(String firstName, String lastName) {
        return donorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NotFoundException("Bağışçı bulunamadı!!!" + firstName + lastName));
    }

    //update
    public DonorResponse updateDonorById(Long id, DonorRequest donorRequest) {
        Donor existingDonor = donorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bağışçı bulunamadı!!!" + id));
        Donor updatedDonor = mapToEntity(donorRequest);
        updatedDonor.setId(existingDonor.getId());
        updatedDonor.setCreatedDate(existingDonor.getCreatedDate());
        updatedDonor = donorRepository.save(updatedDonor);
        return mapToResponse(updatedDonor);
    }

    // Delete
    @Transactional
    public DonorResponse deleteDonorById(Long id) {
        Donor deletedDonor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bağışçı bulunamadı!!!" + id));
        donorRepository.delete(deletedDonor);
        return mapToResponse(deletedDonor);
    }

    public List<DonorResponse> deleteAllDonor() {
        List<DonorResponse> donorResponseList = getDonorList();
        donorRepository.deleteAll();
        return donorResponseList;
    }

}
