package com.timposulabs.loker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.timposulabs.loker.entity.Users;
import com.timposulabs.loker.service.UsersTypeService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UsersController {

    private final UsersTypeService service;

    public UsersController(UsersTypeService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("getAllTypes", service.getAll());
        model.addAttribute("users", new Users());
        return "register";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("users", new Users());
        return "login";
    }    
}
