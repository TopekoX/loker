package com.timposulabs.loker.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.timposulabs.loker.dto.RecruiterProfileDTO;
import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.entity.Users;
import com.timposulabs.loker.service.RecruiterProfileService;
import com.timposulabs.loker.service.UsersService;
import com.timposulabs.loker.util.FileUploadUtil;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersService usersService;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UsersService usersService, RecruiterProfileService recruiterProfileService) {
        this.usersService = usersService;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String reqruiterProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            UsersDTO user =  usersService.findByEmail(username);
            Optional<RecruiterProfileDTO> recruiterProfile = recruiterProfileService.getRecruiterProfileById(user.getId());
            if (recruiterProfile.isPresent()) {
                // form is bound to attribute named "profile"
                model.addAttribute("profile", recruiterProfile.get());
                // keep old name in case other code relies on it (optional)
                model.addAttribute("recruiterProfile", recruiterProfile.get());
            } else {
                model.addAttribute("profile", new RecruiterProfileDTO());
                model.addAttribute("error", "Recruiter profile not found");
            }
        } else {
            // ensure profile attribute always exists to avoid Thymeleaf errors
            model.addAttribute("profile", new RecruiterProfileDTO());
        }
       
        return "recruiter-profile";
    }    

    @PostMapping("/addNew")
    public String createRecruiterProfile(RecruiterProfileDTO recruiterProfile, 
            @RequestParam("image") MultipartFile multipartFile, Model model) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            UsersDTO user =  usersService.findByEmail(username);
            // do not reuse user ID for the profile; the profile has its own generated id
            // only associate the user entity so the foreign key is populated
            recruiterProfile.setUser(toEntity(user));
        }
        model.addAttribute("profile", recruiterProfile);

        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePictureUrl(fileName);
        }
        RecruiterProfileDTO savedProfile = recruiterProfileService.saveRecruiterProfile(recruiterProfile);

        String uploadDir = "photos/recruiter/" + savedProfile.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload profile picture: " + e.getMessage());
            return "recruiter-profile";
        }
        return "redirect:/dashboard/";
    }

    // Helper method to converting
    private Users toEntity(UsersDTO dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUsersType(dto.getUsersType());
        return user;
    }
}
