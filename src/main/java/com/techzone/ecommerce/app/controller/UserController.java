package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String profilView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        List<Integer> years = orderService.getYears(user.getId());


        if (user == null) {
            return "error/404";
        }

        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("user", user);
        model.addAttribute("orderYears", years);

        return "profil/profil";
    }

    @GetMapping("/2")
    public String profilView2(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        user.getOrders().sort(Comparator.comparing(Order::getCreatedAt).reversed());
        model.addAttribute("user", user);
        model.addAttribute("viewContent", "profil/orders :: order-list");
        return "profil/profil2";
    }
}
