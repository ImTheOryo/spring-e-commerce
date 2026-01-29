package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.CartDTO;
import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CartService;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CartDTO> cart(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.getUser(Long.parseLong(jwt.getId()));

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        CartDTO cartDTO = new CartDTO(user.getCart());

        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody CartProduct cartProduct, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }

        Cart cart = cartService.addCartProduct(cartProduct, user.getCart());

        if (cart == null) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Produit ajouter avec succés", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody CartProduct cartProduct, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }

        CartProduct res = cartService.updateCartProduct(cartProduct, user.getCart());
        if (res == null) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Produit ajouter avec succés", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }

        if (!cartService.removeCartProduct(id, user.getCart())) {
            return new ResponseEntity<>("Il y a eu un problème lors de l'ajout", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Produit ajouter avec succés", HttpStatus.OK);
    }

}
