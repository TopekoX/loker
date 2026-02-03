package com.timposulabs.loker.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.timposulabs.loker.dto.UsersDTO;
import com.timposulabs.loker.exception.EmailAlreadyExistsException;
import com.timposulabs.loker.service.UsersService;
import com.timposulabs.loker.service.UsersTypeService;

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
        // model.addAttribute("getAllTypes", usersTypeservice.getAll(pageable));
        model.addAttribute("user", new UsersDTO());
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UsersDTO());
        return "login";
    }

    @PostMapping("/register/new")
    public String newUsers(@Valid @ModelAttribute("user") UsersDTO usersDTO, BindingResult result,Model model) {
        
        if (result.hasErrors()) {
            return "register";
        } else {
            try {
                usersService.save(usersDTO);
                return "redirect:/dashboard";
            } catch (EmailAlreadyExistsException e) {
                // Daftarkan error langsung ke field 'email' agar terbaca oleh th:errors
                result.rejectValue("email", "error.user", e.getMessage());
                // Tambahkan pesan kustom error ke model untuk ditampilkan di view
                model.addAttribute("customError", e.getMessage() + ", Silakan gunakan email lain atau <a href=\"login.html\" class=\"underline font-bold\">Masuk</a>.");
                return "register";
            }
        }
    }
}
