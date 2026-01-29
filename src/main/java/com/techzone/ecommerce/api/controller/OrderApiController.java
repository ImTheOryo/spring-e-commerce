package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CartService;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderApiController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping("/{orderId}")
    public ResponseEntity<PartialOrderDTO> getOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long orderId){
        User user = userService.getUser(userDetails.getUsername());
        if(user == null) return new ResponseEntity<>(null, HttpStatus.OK);


        Order order = orderService.getOrder(orderId);
        if(order == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        if(!Objects.equals(order.getUser().getId(), user.getId())) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(new PartialOrderDTO(order), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PartialOrderDTO>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());
        List<Order> orders = orderService.getAllByUser(user);
        List<PartialOrderDTO> partialOrders = PartialOrderDTO.orderToPartialOrders(orders);
        return new ResponseEntity<>(partialOrders, HttpStatus.OK);
    }

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

    @PostMapping("/")
    public ResponseEntity<Boolean> createOrder(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute PartialOrderDTO partialOrderDTO) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
        }

        Order order = orderService.createOrder(user.getCart(), partialOrderDTO, user);

        if (order == null) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        cartService.cleanCart(user.getCart());

        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}
