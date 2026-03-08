package com.timposulabs.loker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.timposulabs.loker.dto.RecruiterProfileDTO;
import com.timposulabs.loker.repository.RecruiterProfileRepository;

@Service
public class RecruiterProfileService {
    
    private final RecruiterProfileRepository recruiterProfileRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfileDTO> getRecruiterProfileById(Long id) {
        return recruiterProfileRepository.findById(id).map(this::convertToDTO);
    }

    private RecruiterProfileDTO convertToDTO(com.timposulabs.loker.entity.RecruiterProfile recruiterProfile) {
        RecruiterProfileDTO dto = new RecruiterProfileDTO();
        dto.setId(recruiterProfile.getId());
        dto.setUser(recruiterProfile.getUser());
        dto.setFirstName(recruiterProfile.getFirstName());
        dto.setLastName(recruiterProfile.getLastName());
        dto.setCity(recruiterProfile.getCity());
        dto.setState(recruiterProfile.getState());
        dto.setCountry(recruiterProfile.getCountry());
        dto.setCompanyName(recruiterProfile.getCompanyName());
        dto.setProfilePictureUrl(recruiterProfile.getProfilePictureUrl());
        return dto;
    }
}
