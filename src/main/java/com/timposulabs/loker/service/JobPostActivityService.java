package com.timposulabs.loker.service;

import org.springframework.stereotype.Service;

import com.timposulabs.loker.entity.JobPostActivity;
import com.timposulabs.loker.repository.JobPostAcivityRepository;

@Service
public class JobPostActivityService {

    private final JobPostAcivityRepository jobPostAcivityRepository;
    
    public JobPostActivityService(JobPostAcivityRepository jobPostAcivityRepository) {
        this.jobPostAcivityRepository = jobPostAcivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostAcivityRepository.save(jobPostActivity);
    }
}
