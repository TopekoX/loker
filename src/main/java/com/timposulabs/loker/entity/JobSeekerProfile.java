package com.timposulabs.loker.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_seeker_profile")
@Data
@NoArgsConstructor
public class JobSeekerProfile {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    private String firstName;

    private String lastName;

    private String city;

    private String state;

    private String country;

    private String workAuthorization;

    private String employeeType;

    private String resume;

    @Column(nullable = true)
    private String profilePictureUrl;    

    @OneToMany(mappedBy = "jobSeekerProfile", 
        cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Skills> skills;

    public JobSeekerProfile(Long id, Users user, String firstName, String lastName, String city, String state,
            String country, String workAuthorization, String employeeType, String resume, String profilePictureUrl) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.workAuthorization = workAuthorization;
        this.employeeType = employeeType;
        this.resume = resume;
        this.profilePictureUrl = profilePictureUrl;
    }   
}
