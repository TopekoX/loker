package com.timposulabs.loker.dto;

import com.timposulabs.loker.entity.UsersType;

import jakarta.validation.constraints.NotNull;
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
    
    @NotNull(message = "Pilih salah satu tipe pendaftaran")
    private UsersType usersType;
}
