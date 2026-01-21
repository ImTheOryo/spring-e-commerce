package com.techzone.ecommerce.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthentificationController {

    @GetMapping("/login")
    public String loginPage() {
        return "security/login";
    }
}
