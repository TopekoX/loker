package com.timposulabs.loker.dto;

import java.util.Set;

import com.timposulabs.loker.entity.Skills;
import com.timposulabs.loker.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerProfileDTO {
    
    private Long id;
    
    @ToString.Exclude
    private Users user;

    private String firstName;

    private String lastName;

    private String city;

    private String state;

    private String country;

    private String workAuthorization;

    private String employeeType;

    private String resume;

    private String profilePictureUrl;    

    private Set<Skills> skills;
}
