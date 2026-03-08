package com.timposulabs.loker.controller;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timposulabs.loker.dto.RecruiterProfileDTO;
import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.service.RecruiterProfileService;
import com.timposulabs.loker.service.UsersService;

@Controller
@RequestMapping("/reqruiter-profile")
public class ReqruiterProfileController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;

    public ReqruiterProfileController(UsersService usersService, RecruiterProfileService recruiterProfileService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
    }

    public String reqruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            UsersDTO user =  usersService.findByEmail(username);
            Optional<RecruiterProfileDTO> recruiterProfile = recruiterProfileService.getRecruiterProfileById(user.getId());
            if (recruiterProfile.isPresent()) {
                model.addAttribute("recruiterProfile", recruiterProfile.get());
            } else {
                model.addAttribute("error", "Recruiter profile not found");
            }
        }
       
        return "reqruiter-profile";
    }    
}
