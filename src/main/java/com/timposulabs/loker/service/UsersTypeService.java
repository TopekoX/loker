package com.timposulabs.loker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timposulabs.loker.entity.UsersType;
import com.timposulabs.loker.repository.UsersTypeRepository;

@Service
public class UsersTypeService {

    private final UsersTypeRepository repository;

    public UsersTypeService(UsersTypeRepository repository) {
        this.repository = repository;
    }
    
    public List<UsersType> getAll() {
        return repository.findAll();
    }
}
