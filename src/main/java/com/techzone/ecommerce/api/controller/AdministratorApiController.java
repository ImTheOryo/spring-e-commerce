package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.dto.ProductDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.service.AdminService;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdministratorApiController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/infos")
    public ResponseEntity<Map<String, Object>> getInfos() {
        Map<String, Object> map = adminService.getDashboardInfos();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/commands")
    public ResponseEntity<Map<String, Object>> getCommands(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) OrderStatus status
    ) {
        Map<String, Object> map = adminService.getCommandsInfos(search, status);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/commands/{id}")
    public ResponseEntity<Map<String, Object>> getCommand(
            @PathVariable Long id
    ) {
        if (orderService.getOrder(id) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Map<String, Object> map = adminService.getCommandInfos(id);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/commands/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        if (orderService.changeStatusApi(id, status)) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return getCommand(id);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam(required = false) String search) {
        Map<String, Object> map = adminService.getUsersInfos(search);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        Map<String, Object> map = adminService.userInfos(id);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, UserDTO user) {
        if (adminService.updateUser(id, user)) {
            return getUser(id);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(required = false) Long category, @RequestParam(required = false) String search) {
        Map<String, Object> map = adminService.productsInfos(search, category);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(
            @PathVariable Long id
    ) {
        Map<String, Object> map = adminService.productInfos(id);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/products/save")
    public ResponseEntity<String> createProduct(@Valid ProductDTO productDTO) {
        adminService.createProduct(productDTO);
        return new ResponseEntity<>("Produit créé", HttpStatus.OK);
    }

    @PostMapping("/products/save/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") ProductDTO productDTO
    ) {
        adminService.updateProduct(id, productDTO);
        return new ResponseEntity<>("Produit créé", HttpStatus.NOT_FOUND);
    }
}
