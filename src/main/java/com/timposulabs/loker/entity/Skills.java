package com.timposulabs.loker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skills {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String experienceLevel;

    private String yearOfExperience;

    @ManyToOne(cascade = {jakarta.persistence.CascadeType.DETACH, 
        jakarta.persistence.CascadeType.MERGE, 
        jakarta.persistence.CascadeType.PERSIST, 
        jakarta.persistence.CascadeType.REFRESH})
    @JoinColumn(name = "job_seeker_profile_id", referencedColumnName = "id")
    private JobSeekerProfile jobSeekerProfile;
}
