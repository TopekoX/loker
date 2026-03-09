package com.timposulabs.loker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.timposulabs.loker.entity.RecruiterProfile;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

    // lookup by the foreign key instead of profile primary key
    Optional<RecruiterProfile> findByUserId(Long userId);
} 
