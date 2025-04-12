package com.semih.service;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.DonorNameResponse;
import com.semih.dto.response.DonorResponse;
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

    private Donor mapDtoToEntity(DonorRequest donorRequest) {
        return new Donor(
                donorRequest.getFirstName(),
                donorRequest.getLastName(),
                donorRequest.getPhoneNumber(),
                donorRequest.getEmail(),
                donorRequest.getGenderType(),
                new Address(
                        donorRequest.getAddress().getCity(),
                        donorRequest.getAddress().getDistrict(),
                        donorRequest.getAddress().getNeighborhood(),
                        donorRequest.getAddress().getStreet(),
                        donorRequest.getAddress().getUserType()
                )
        );
    }

    private DonorResponse mapEntityToResponse(Donor donor) {
        return new DonorResponse(
                donor.getId(),
                donor.getCreatedDate(),
                donor.getModifiedDate(),
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

    public void saveDonor(DonorRequest donorRequest) {
        donorRepository.save(mapDtoToEntity(donorRequest));
    }

    // Get
    public List<DonorResponse> getDonorList() {
        List<Donor> donorList = donorRepository.findAll();
        List<DonorResponse> donorResponseList = new ArrayList<>();
        for (Donor donor : donorList) {
            donorResponseList.add(mapEntityToResponse(donor));
        }
        return donorResponseList;
    }

    public DonorResponse getDonorById(Long id) {
        Donor donor = donorRepository.findById(id).orElseThrow(null);
        return mapEntityToResponse(donor);
    }

    public List<DonorNameResponse> getDonorNameAndSurnameList() {
        return donorRepository.getDonorNameAndSurnameList();
    }

    public Donor getDonorByFirstNameAndLastName(String firstName, String lastName) {
        return donorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("Kişi Bulunamadi"));
    }

    //update
    public void updateDonorById(Long id, DonorRequest donorRequest) {
        Donor existingDonor = donorRepository.findById(id).orElseThrow(() -> new RuntimeException("Bağışçı Bulunamadı"));
        Donor updatedDonor = mapDtoToEntity(donorRequest);
        updatedDonor.setId(existingDonor.getId());
        updatedDonor.setCreatedDate(existingDonor.getCreatedDate());
        donorRepository.save(updatedDonor);
    }

    // Delete
    @Transactional
    public void deleteDonorById(Long id) {
        donorRepository.deleteById(id);
    }

    public void deleteAllDonor() {
        donorRepository.deleteAll();
    }

}
