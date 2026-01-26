package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAllAttributes(adminService.getDashboardInfos());
        return "admin/dashboard";
    }

    @GetMapping("/commands")
    public String getCommands(
            Model model,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false)OrderStatus status
            ) {
        model.addAttribute("activePage", "commands");
        model.addAllAttributes(adminService.getCommandsInfos(search, status, pageable));

        return "admin/commands";
    }
}
