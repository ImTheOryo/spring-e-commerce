package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAllAttributes(adminService.getDashboardInfos());
        return "admin/dashboard";
    }
}
