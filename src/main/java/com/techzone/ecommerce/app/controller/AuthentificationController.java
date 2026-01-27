package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.RoleEnum;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.entityForm.UserForm;
import com.techzone.ecommerce.shared.repository.UserRepository;
import com.techzone.ecommerce.shared.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class AuthentificationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/404")
    public String errorPage() {
        return "security/404";
    }

    @GetMapping("/register")
    public String registerPage(
            Model model
    ) {
        model.addAttribute("userForm", new UserForm());
        return "register/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid UserForm userForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            String firstError = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            model.addAttribute("error", firstError);
            return "register/register";
        }

        if (!Objects.equals(userForm.getPassword(), userForm.getConfirmPassword())) {
            model.addAttribute("error", "Les mots de passes ne correspondent pas");
            return "register/register";
        }

        if (userDetailsServiceImpl.existsByEmail(userForm.getEmail())) {
            model.addAttribute("error", "Email invalide");
            return "register/register";
        }

        try {
            String encodedPassword = bCryptPasswordEncoder.encode(userForm.getPassword().trim());
            User user = new User();
            user.setFirstname(userForm.getFirstName());
            user.setLastname(userForm.getLastName());
            user.setEmail(userForm.getEmail().toLowerCase().trim());
            user.setPassword(encodedPassword);
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(RoleEnum.USER);
            Cart cart = new Cart();
            user.setCart(cart);

            userRepository.save(user);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur technique est survenue.");
            return "register/register";
        }
    }
}
