package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.dto.CartDTO;
import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CartService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping("/{year}")
    public ResponseEntity<List<PartialOrderDTO>> getOrderByUserAndYear(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int year) {
        User user = userService.getUser(userDetails.getUsername());
        List<Order> orders = orderService.getUserOrderByYear(year, user);
        List<PartialOrderDTO> partialOrders = PartialOrderDTO.orderToPartialOrders(orders);
        return new ResponseEntity<>(partialOrders, HttpStatus.OK);
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYears(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        List<Integer> years = orderService.getYears(user.getId());
        return new ResponseEntity<>(years, HttpStatus.OK);
    }

    @GetMapping("/payment")
    public String getPaymentView(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return "error/404";
        }
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("user", new UserDTO(user));
        model.addAttribute("products", new CartDTO(user.getCart()));


        return "payment/payment";
    }

    @PostMapping("/payed")
    public String createOrder(Model model, @AuthenticationPrincipal UserDetails userDetails, @ModelAttribute PartialOrderDTO partialOrderDTO) {
        User user = userService.getUser(userDetails.getUsername());
        if (user == null) {
            return "error/404";
        }

        Order order = orderService.createOrder(user.getCart(), partialOrderDTO, user);

        if (order == null) {
            return "error/404";
        }
        cartService.cleanCart(user.getCart());

        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        return "payment/payed";
    }
}
