package com.timposulabs.loker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.timposulabs.loker.entity.JobPostActivity;

@Repository
public interface JobPostAcivityRepository extends JpaRepository<JobPostActivity, Long> {

}
