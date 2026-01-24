package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

// Récupérer la logique de Cécile pour enchaîner sur le panier

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartRepository cartRepository;

    @GetMapping("/cart")
    public String cartView(Model model, @PageableDefault(size = 12) Pageable pageable) {

        return "order/cart";
    }
}
