package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.UserDTO;

import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUser(userDetails.getUsername());

        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserDTO userDTO) {
        User user = userService.getUser(userDetails.getUsername());
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());

        User res = userService.updateUserApi(user);
        if (res == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
