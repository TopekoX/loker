package com.timposulabs.loker.service;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.entity.Users;
import com.timposulabs.loker.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository repository;
    
    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public Page<UsersDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public UsersDTO save(UsersDTO dto) {
        Users user = toEntity(dto);
        return toDTO(repository.save(user));
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
