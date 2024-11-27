package com.semih.service;

import com.semih.dto.request.CharityOrganizationRequest;
import com.semih.dto.response.AddressResponse;
import com.semih.dto.response.CharityOrganizationResponse;
import com.semih.model.CharityOrganization;
import com.semih.repository.CharityOrganizationRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharityOrganizationService {

    private final CharityOrganizationRepository charityOrganizationRepository;
    private final ModelMapper modelMapper;

    public CharityOrganizationService(CharityOrganizationRepository charityOrganizationRepository, ModelMapper modelMapper) {
        this.charityOrganizationRepository = charityOrganizationRepository;
        this.modelMapper = modelMapper;
    }

    public CharityOrganizationResponse getCharityOrganizationFindByName(String name){
        CharityOrganization savedCharityOrgaization = charityOrganizationRepository.findCharityOrganizationByOrganizationName(name);
        CharityOrganizationResponse charityOrganizationResponse = modelMapper.map(savedCharityOrgaization, CharityOrganizationResponse.class);
        charityOrganizationResponse.setAddressResponse(modelMapper.map(savedCharityOrgaization.getAddress(), AddressResponse.class));
        return charityOrganizationResponse;
    }

    public List<CharityOrganizationResponse> findAllCharityOrganization(){
        List<CharityOrganization> charityOrganizations = charityOrganizationRepository.findAll();
        List<CharityOrganizationResponse> charityOrganizationResponses = new ArrayList<>();

        for(CharityOrganization charityOrganization : charityOrganizations){
            charityOrganizationResponses.add(modelMapper.map(charityOrganization, CharityOrganizationResponse.class));
        }
        return charityOrganizationResponses;
    }

    public void saveCharityOrganization(CharityOrganizationRequest charityOrganizationRequest) {
        CharityOrganization charityOrganization = modelMapper.map(charityOrganizationRequest, CharityOrganization.class);
        charityOrganizationRepository.save(charityOrganization);
    }

    public void updateCharityOrganization(CharityOrganizationRequest charityOrganizationRequest,String name) {
        CharityOrganization savedCharityOrgaization = charityOrganizationRepository.findCharityOrganizationByOrganizationName(name);
        modelMapper.map(charityOrganizationRequest, savedCharityOrgaization);
        charityOrganizationRepository.save(savedCharityOrgaization);
    }

    public void deleteCharityOrganizationById(Long id){
        charityOrganizationRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteCharityOrganizationByName(String charityOrganizationName){
       return charityOrganizationRepository.deleteCharityOrganizationByorganizationName(charityOrganizationName)>0;
    }

}
