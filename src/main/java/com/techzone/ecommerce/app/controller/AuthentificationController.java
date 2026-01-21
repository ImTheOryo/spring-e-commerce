package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthentificationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error
    ) {
        return "security/login";
    }

    @GetMapping("/login_sucess")
    public String loginSuccess() {
        return "security/login_succes";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "security/error";
    }
}
