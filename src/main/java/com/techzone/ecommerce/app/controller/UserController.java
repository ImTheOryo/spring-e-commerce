package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String profilView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        model.addAttribute("user", user);

        return "profil/profil";
    }
}
