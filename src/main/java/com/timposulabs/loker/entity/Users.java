package com.timposulabs.loker.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "email")
    private String email;
    
    @Column(nullable = false, name = "password")
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "registration_date")
    private Date registrationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private Date updateDate;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "usersTypeId", referencedColumnName = "id")
    private UsersType usersType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private JobSeekerProfile jobSeekerProfile;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private RecruiterProfile recruiterProfile;
}
