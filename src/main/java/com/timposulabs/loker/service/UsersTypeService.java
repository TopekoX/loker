package com.timposulabs.loker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timposulabs.loker.dto.UsersTypeDTO;
import com.timposulabs.loker.entity.UsersType;
import com.timposulabs.loker.repository.UsersTypeRepository;

@Service
public class UsersTypeService {

    private final UsersTypeRepository repository;

    public UsersTypeService(UsersTypeRepository repository) {
        this.repository = repository;
    }
    
    public List<UsersTypeDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    // Helper methods to convert between Entity and DTO
    private UsersTypeDTO toDTO(UsersType entity) {
        return new UsersTypeDTO(
            entity.getId(),
            entity.getTypeName(),
            entity.getUsers()
        );
    }

    private UsersType toEntity(UsersTypeDTO dto) {
        UsersType entity = new UsersType();
        entity.setId(dto.getId());
        entity.setTypeName(dto.getTypeName());
        entity.setUsers(dto.getUsers());
        return entity;
    }
}
