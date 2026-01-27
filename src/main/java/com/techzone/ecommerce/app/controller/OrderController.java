package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/{year}")
    public ResponseEntity<List<PartialOrderDTO>> getOrderByUserAndYear(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int year) {
        User user = userService.getUser(userDetails.getUsername());
        List<Order> orders = orderService.getUserOrderByYear(year, user);
        List<PartialOrderDTO> partialOrders = PartialOrderDTO.orderToPartialOrders(orders);
        return new ResponseEntity<>(partialOrders, HttpStatus.OK);
    }

    @GetMapping("/years")
    public ResponseEntity<List<Integer>> getYears(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.getUser(userDetails.getUsername());

        List<Integer> years = orderService.getYears(user.getId());
        return new ResponseEntity<>(years, HttpStatus.OK);
    }
}
