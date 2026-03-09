package com.timposulabs.loker.dto;

import com.timposulabs.loker.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterProfileDTO {
    private Long id;

    @ToString.Exclude
    private Users user;
    
    private String firstName;
    
    private String lastName;
    
    private String city;
    
    private String state;
    
    private String country;
    
    private String companyName;
    
    private String profilePictureUrl;

    public String getPhotosImagePath() {
        if (profilePictureUrl == null || id == null) return null;
        return "/photos/recruiter/" + id + "/" + profilePictureUrl;
    }
}
