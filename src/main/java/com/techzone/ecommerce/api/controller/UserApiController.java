package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;
    private final OrderService orderService;
    private final CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("update")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserDTO userDTO) {
        User user = userService.getUser(userDetails.getUsername());
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());

        if (!userService.updateUser(user)) {
            return "error/404";
        }

        return "redirect:/user";
    }
}
