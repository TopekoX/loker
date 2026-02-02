package com.timposulabs.loker.dto;

import java.util.Set;

import com.timposulabs.loker.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersTypeDTO {

    private Long id;

    private String typeName;
    
    private Set<Users> users;
}