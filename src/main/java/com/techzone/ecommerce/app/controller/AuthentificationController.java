package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthentificationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }

    @GetMapping("/login_sucess")
    public String loginSuccess() {
        return "security/login_succes";
    }

    @GetMapping("/errors")
    public String errorPage() {
        return "security/error";
    }
}
