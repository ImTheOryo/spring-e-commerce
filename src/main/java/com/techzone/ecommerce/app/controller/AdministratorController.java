package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.service.AdminService;
import com.techzone.ecommerce.shared.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/commands/{id}")
    public String getCommand(
            Model model,
            @PathVariable Long id
    ) {
        if (orderService.getOrder(id) == null){
            return "error/404";
        }

        model.addAllAttributes(adminService.getCommandInfos(id));
        return "admin/command";
    }

    @PostMapping("/commands/{id}/status")
    public String updateStatus(
            Model model,
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ){
        orderService.changeStatus(id, status);
        return getCommand(model, id);
    }
}
