package com.timposulabs.loker.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.entity.JobPostActivity;
import com.timposulabs.loker.service.JobPostActivityService;
import com.timposulabs.loker.service.UsersService;
import org.springframework.ui.Model;

@Controller
public class JobPostActivityController {
    
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {
        Object user = usersService.getCurrentUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            System.out.println("Authenticated user: " + username);
            model.addAttribute("username", username);
        } 
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model) {
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUser());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNew(JobPostActivity jobPostActivity, Model model) {
        UsersDTO user = usersService.getCurrentUserDTO();
        if (user != null) {
            jobPostActivity.setPostedById(usersService.toEntity(user)); 
        } else {
            model.addAttribute("error", "User not authenticated");
            return "add-jobs";
        }
        jobPostActivity.setPostedDate(new java.util.Date());
        model.addAttribute("jobPostActivity", jobPostActivity);
        JobPostActivity savedActivity = jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }
}