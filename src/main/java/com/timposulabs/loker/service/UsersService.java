package com.timposulabs.loker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.timposulabs.loker.dto.JobSeekerProfileDTO;
import com.timposulabs.loker.dto.RecruiterProfileDTO;
import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.entity.JobSeekerProfile;
import com.timposulabs.loker.entity.RecruiterProfile;
import com.timposulabs.loker.entity.Users;
import com.timposulabs.loker.exception.EmailAlreadyExistsException;
import com.timposulabs.loker.repository.JobSeekerProfileRepository;
import com.timposulabs.loker.repository.RecruiterProfileRepository;
import com.timposulabs.loker.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UsersService(UsersRepository repository, 
                            JobSeekerProfileRepository jobSeekerProfileRepository,
                                RecruiterProfileRepository recruiterProfileRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UsersDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDTO);
    }

    public UsersDTO save(UsersDTO dto) {
        Users user = toEntity(dto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email sudah terdaftar: " + user.getEmail());
        }        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users userSaved = userRepository.save(user);

        if (user.getUsersType().getId() == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(userSaved));    
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(userSaved));
        }
        return toDTO(userSaved);
    }

    public UsersDTO findByEmail(String email) {
        Users user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found with email: " + email)
        );
        return toDTO(user);
    }   

    public Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username)
            );

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Pelamar"))) {
                JobSeekerProfileDTO jobSeekerProfile = 
                        toJobSeekerProfileDTO(jobSeekerProfileRepository.findById(user.getId()).orElse(new JobSeekerProfile()));
                return jobSeekerProfile;
            } else {
                // previously we looked up by profile PK – incorrect if the generated
                // profile id differs from the user id.  Use the foreign key column.
                RecruiterProfileDTO recruiterProfile = 
                        toRecruiterProfileDTO(recruiterProfileRepository
                                .findByUserId(user.getId())
                                .orElse(new RecruiterProfile()));
                return recruiterProfile;
            }
        }
        return null;
    }

    public UsersDTO getCurrentUserDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + username)
            );
            return toDTO(user);
        }
        return null;
    }

    // Helper methods to convert between Entity and DTO
    private UsersDTO toDTO(Users entity) {
        return new UsersDTO(
            entity.getId(),
            entity.getPassword(),
            entity.getEmail(),
            entity.getUsersType()
        );
    }

    public Users toEntity(UsersDTO dto) {
        Users entity = new Users();
        entity.setId(dto.getId());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setUsersType(dto.getUsersType());
        return entity;
    }

    private RecruiterProfileDTO toRecruiterProfileDTO(RecruiterProfile entity) {
        RecruiterProfileDTO dto = new RecruiterProfileDTO();
        dto.setId(entity.getId());
        dto.setUser(entity.getUser());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setCompanyName(entity.getCompanyName());
        dto.setProfilePictureUrl(entity.getProfilePictureUrl());
        return dto;
    }

    private JobSeekerProfileDTO toJobSeekerProfileDTO(JobSeekerProfile entity) {
        JobSeekerProfileDTO dto = new JobSeekerProfileDTO();
        dto.setId(entity.getId());
        dto.setUser(entity.getUser());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setProfilePictureUrl(entity.getProfilePictureUrl());
        return dto;
    }
}
