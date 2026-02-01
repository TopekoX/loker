package com.timposulabs.loker.dto;

import com.timposulabs.loker.entity.UsersType;

public record UsersDTO(
    Long id, 
    String email, 
    String password,
    UsersType usersType) {

}
