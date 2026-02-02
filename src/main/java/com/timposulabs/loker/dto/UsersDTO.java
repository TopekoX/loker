package com.timposulabs.loker.dto;

import com.timposulabs.loker.entity.UsersType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    
    private Long id;
    
    private String email;
    
    private String password;
    
    private UsersType usersType;
}
