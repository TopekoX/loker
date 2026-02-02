package com.timposulabs.loker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    public Page<UsersTypeDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
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
