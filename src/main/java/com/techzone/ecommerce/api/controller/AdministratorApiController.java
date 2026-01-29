package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.ProductDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.service.AdminService;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdministratorApiController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

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

        model.addAttribute("activePage", "commands");
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

    @GetMapping("/users")
    public String getUsers(
            Model model,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String search
    ) {
        model.addAttribute("activePage", "users");
        model.addAllAttributes(adminService.getUsersInfos(search, pageable));

        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String getUser(
            Model model,
            @PathVariable Long id
    ) {
        model.addAttribute("activePage", "users");
        model.addAllAttributes(adminService.userInfos(id));

        return "admin/user";
    }

    @PostMapping("/users/{id}")
    public String updateUser(
            Model model,
            @PathVariable Long id,
            UserDTO user
    ) {
        if (adminService.updateUser(id, user)){
            return getUser(model, id);
        }
        return "error/404";

    }

    @GetMapping("/products")
    public String getProducts(
            Model model,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String search
    ) {
        model.addAttribute("activePage", "products");
        model.addAllAttributes(adminService.productsInfos(search, category, pageable));
        return "admin/products";
    }

    @GetMapping("/products/{id}")
    public String getProduct(
            Model model,
            @PathVariable Long id
    ) {
        model.addAttribute("activePage", "products");
        model.addAllAttributes(adminService.productInfos(id));
        return "admin/product";
    }

    @GetMapping("/product")
    public String newProduct(
            Model model
    ) {
        model.addAttribute("activePage", "products");
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("product", new ProductDTO());
        return "admin/product";
    }

    @PostMapping("/products/save")
    public String createProduct(
            Model model,
            @Valid ProductDTO productDTO
    ) {
        adminService.createProduct(productDTO);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/save/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") ProductDTO productDTO
    ) {
        adminService.updateProduct(id, productDTO);
        return "redirect:/admin/products";
    }
}
