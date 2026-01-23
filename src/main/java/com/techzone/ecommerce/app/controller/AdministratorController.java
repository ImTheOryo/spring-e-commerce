package com.techzone.ecommerce.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministratorController {

    @GetMapping("/admin")
    public String administrator(){
        return "admin/admin";
    }
}
