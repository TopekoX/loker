package com.timposulabs.loker.dto;

import java.util.Set;

import com.timposulabs.loker.entity.Users;

public record UsersTypeDTO(Long id, String typeName, Set<Users> users) {

}
