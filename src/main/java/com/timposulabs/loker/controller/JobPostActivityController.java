package com.timposulabs.loker.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.timposulabs.loker.service.UsersService;
import org.springframework.ui.Model;

@Controller
public class JobPostActivityController {
    
    private final UsersService usersService;

    public JobPostActivityController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model) {
        Object user = usersService.getCurrentUser();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            System.out.println("username: " + username);
        } 
        model.addAttribute("user", user);
        return "dashboard";
    }
}
