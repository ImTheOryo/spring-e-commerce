package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.app.service.ProductService;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("products", productRepository.findAll());

        return "home/home";
    }
}
