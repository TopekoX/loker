package com.timposulabs.loker.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.entity.UsersType;
import com.timposulabs.loker.exception.EmailAlreadyExistsException;
import com.timposulabs.loker.service.UsersService;
import com.timposulabs.loker.service.UsersTypeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeservice;
    private final UsersService usersService;

    public UsersController(UsersTypeService usersTypeservice, UsersService usersService) {
        this.usersTypeservice = usersTypeservice;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model, @PageableDefault(size = 10) Pageable pageable) {
        model.addAttribute("usersTypes", usersTypeservice.getAll());
        
        UsersDTO dto = new UsersDTO();
        dto.setUsersType(new UsersType());

        model.addAttribute("user", dto);
        return "register";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UsersDTO());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }   
    
    @PostMapping("/register/new")
    public String newUsers(@Valid @ModelAttribute("user") UsersDTO usersDTO, BindingResult result,Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("usersTypes", usersTypeservice.getAll());
            result.rejectValue("usersType.id", "error.user", "Silakan pilih tipe pengguna");
            return "register";
        } else {
            try {
                usersService.save(usersDTO);
                return "redirect:/dashboard";
            } catch (EmailAlreadyExistsException e) {
                model.addAttribute("usersTypes", usersTypeservice.getAll());
                // Daftarkan error langsung ke field 'email' agar terbaca oleh th:errors
                result.rejectValue("email", "error.user", e.getMessage());
                // Tambahkan pesan kustom error ke model untuk ditampilkan di view
                model.addAttribute("customError", e.getMessage() + ", Silakan gunakan email lain atau <a href=\"/login\" class=\"underline font-bold\">Masuk</a>.");
                return "register";
            }
        }
    }
}
