package com.semih.service;

import com.semih.dto.request.DonorRequest;
import com.semih.dto.request.FamilyRequest;
import com.semih.dto.response.DonorResponse;
import com.semih.dto.response.FamilyResponse;
import com.semih.model.Donor;
import com.semih.model.Family;
import com.semih.repository.DonorRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final ModelMapper modelMapper;

    public DonorService(DonorRepository donorRepository, ModelMapper modelMapper) {
        this.donorRepository = donorRepository;
        this.modelMapper = modelMapper;
    }

    public boolean saveDonor(DonorRequest donorRequest) {
      Donor savedDonor = donorRepository.save(modelMapper.map(donorRequest, Donor.class));
      return savedDonor!=null;
    }

    // Get
    public List<DonorResponse> getDonorList(){
        List<Donor> donorList = donorRepository.findAll();
        List<DonorResponse> donorResponseList = new ArrayList<>();
        for(Donor donor:donorList){
            donorResponseList.add(modelMapper.map(donor, DonorResponse.class));
        }
        return donorResponseList;
    }

    public DonorResponse getDonorById(Long id){
        Donor donor  = donorRepository.findById(id).orElseThrow(null);
        return modelMapper.map(donor, DonorResponse.class);
    }

    //update
    public boolean updateDonorById(Long id, DonorRequest donorRequest){
        Optional<Donor> donor  = donorRepository.findById(id);
        if(donor.isPresent()){
            modelMapper.map(donorRequest, donor.get());
            donorRepository.save(donor.get());
            return true;
        }
        return false;
    }

    // Delete
    @Transactional
    public boolean deleteDonorById(Long id){
        donorRepository.deleteById(id);

        Optional<Donor> donor  = donorRepository.findById(id);

        return donor.isPresent();
    }

    public void deleteAllDonor(){
        donorRepository.deleteAll();
    }

}
