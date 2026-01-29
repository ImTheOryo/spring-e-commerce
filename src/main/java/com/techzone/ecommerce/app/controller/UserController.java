package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final CategoryService categoryService;


    @GetMapping()
    public String profilView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        if (user == null) {
            return "error/404";
        }

        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("user", user);
        model.addAttribute("viewContent", "profil/orders :: order-list");
        return "profil/profil";
    }

    @GetMapping("/{id}")
    public String profilViewDetails(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        Order order = orderService.getOrder(id);

        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("user", user);
        model.addAttribute("order", new PartialOrderDTO(order));
        model.addAttribute("viewContent", "profil/orderDetails :: order-detail");
        return "profil/orderDetails";
    }

    @GetMapping("/edit")
    public String profilEdit(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("user", user);
        model.addAttribute("viewContent", "profil/edit :: profile-edit");
        return "profil/edit";
    }

    @PostMapping("update")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserDTO userDTO) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());

        if (!userService.updateUser(user)) {
            return "error/404";
        }

        return "redirect:/user";
    }
}
