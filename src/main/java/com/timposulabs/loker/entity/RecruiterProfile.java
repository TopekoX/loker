package com.timposulabs.loker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "recruiter_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    
    private String firstName;
    
    private String lastName;
    
    private String city;
    
    private String state;
    
    private String country;
    
    private String companyName;
    
    @Column(nullable = true)
    private String profilePictureUrl;

    public RecruiterProfile(Users user) {
        this.user = user;
    }
}
