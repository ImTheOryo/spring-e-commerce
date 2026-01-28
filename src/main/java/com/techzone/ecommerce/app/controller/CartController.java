package com.techzone.ecommerce.app.controller;

import org.springframework.ui.Model;
import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.entity.*;
import com.techzone.ecommerce.shared.service.CartService;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public String cartView(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }

        List<CartProduct> cartProducts = user.getCart().getCartProductList();


        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);

        model.addAttribute("products", cartProducts);
        model.addAttribute("total", user.getCart().getTotal());
        model.addAttribute("qty", user.getCart().getQty());

        return "cart/cart";
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
