package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.api.service.AdministratorService;
import com.techzone.ecommerce.shared.dto.ProductDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.AdminService;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministratorApiController {

    @Autowired
    private AdministratorService adminServiceAPI;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/commands")
    public ResponseEntity<List<Order>> getCommands(
    ) {
        List<Order> map = adminServiceAPI.getCommands();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/commands/{id}")
    public ResponseEntity<?> getCommand(
            @PathVariable Long id
    ) {
        if (orderService.getOrder(id) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @PutMapping("/commands/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        if (orderService.changeStatusApi(id, status)) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
    ) {
        List<User> map = adminServiceAPI.getUsers();

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<UserDTO> users = new ArrayList<>();
        for (User user : map) {
            users.add(adminServiceAPI.getUserDTO(user));
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User map = adminServiceAPI.getUserById(id);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        UserDTO userDTO = adminServiceAPI.getUserDTO(map);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        List<Product> map = adminServiceAPI.getProducts();

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<ProductDTO> products = new ArrayList<>();
        for (Product product : map) {
            products.add(adminServiceAPI.getproduct(product));
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(
            @PathVariable Long id
    ) {
        Product map = adminServiceAPI.getProductById(id);

        if (map == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        ProductDTO productDTO = adminServiceAPI.getproduct(map);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
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
