package com.timposulabs.loker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    
    public UsersService(UsersRepository repository, 
                            JobSeekerProfileRepository jobSeekerProfileRepository,
                                RecruiterProfileRepository recruiterProfileRepository) {
        this.userRepository = repository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Page<UsersDTO> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDTO);
    }

    public UsersDTO save(UsersDTO dto) {
        Users user = toEntity(dto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email sudah terdaftar: " + user.getEmail());
        }        
        Users userSaved = userRepository.save(user);

        if (user.getUsersType().getId() == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(userSaved));    
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(userSaved));
        }
        return toDTO(userSaved);
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

    private Users toEntity(UsersDTO dto) {
        Users entity = new Users();
        entity.setId(dto.getId());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setUsersType(dto.getUsersType());
        return entity;
    }
}
