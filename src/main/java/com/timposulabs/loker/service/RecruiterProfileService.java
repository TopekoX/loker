package com.timposulabs.loker.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.timposulabs.loker.dto.RecruiterProfileDTO;
import com.timposulabs.loker.entity.RecruiterProfile;
import com.timposulabs.loker.repository.RecruiterProfileRepository;
import com.timposulabs.loker.repository.UsersRepository;

@Service
public class RecruiterProfileService {
    
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository,
                                   UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfileDTO> getRecruiterProfileById(Long id) {
        return recruiterProfileRepository.findById(id).map(this::convertToDTO);
    }

    public RecruiterProfileDTO saveRecruiterProfile(RecruiterProfileDTO recruiterProfile) {
        RecruiterProfile entity = null;

        if (recruiterProfile.getUser() != null && recruiterProfile.getUser().getId() != null) {
            Optional<RecruiterProfile> byUser = recruiterProfileRepository
                    .findByUserId(recruiterProfile.getUser().getId());
            if (byUser.isPresent()) {
                entity = byUser.get();
            }
        }

        if (entity == null) {
            if (recruiterProfile.getId() != null && recruiterProfileRepository.existsById(recruiterProfile.getId())) {
                entity = recruiterProfileRepository.findById(recruiterProfile.getId()).get();
            } else {
                entity = new RecruiterProfile();
            }
        }

        entity.setFirstName(recruiterProfile.getFirstName());
        entity.setLastName(recruiterProfile.getLastName());
        entity.setCity(recruiterProfile.getCity());
        entity.setState(recruiterProfile.getState());
        entity.setCountry(recruiterProfile.getCountry());
        entity.setCompanyName(recruiterProfile.getCompanyName());
        entity.setProfilePictureUrl(recruiterProfile.getProfilePictureUrl());

        if (recruiterProfile.getUser() != null && recruiterProfile.getUser().getId() != null) {
            usersRepository.findById(recruiterProfile.getUser().getId())
                           .ifPresent(entity::setUser);
        }

        return convertToDTO(recruiterProfileRepository.save(entity));
    }

    // Helper method to converting
    private RecruiterProfileDTO convertToDTO(RecruiterProfile recruiterProfile) {
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
