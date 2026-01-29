package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.ProductDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    public Map<String ,Object> getDashboardInfos () {
        Map<String , Object> dashboardInfos = new HashMap<>();
        dashboardInfos.put("totalCommandes", orderService.getOrdersCount());
        dashboardInfos.put("totalProduits", productService.getProductsCount());
        dashboardInfos.put("totalUtilisateurs", userService.getUsersCount());
        dashboardInfos.put("totalRevenus", orderService.getTotalRevenue());
        dashboardInfos.put("chartInfos", orderService.getOrdersStats());
        dashboardInfos.put("colors", getStatusColor());
        dashboardInfos.put("revenueGrowth", orderService.getRevenueGrowth());
        dashboardInfos.put("commandsGrowth", orderService.getOrdersGrowth());
        dashboardInfos.put("userGrowth", userService.getUserGrowth());
        return dashboardInfos;
    }

    public Map<String, Object> getCommandsInfos (String search, OrderStatus status ,Pageable page) {
        Map<String, Object> commandsInfos = new HashMap<>();
        Page<Order> allCommands = orderService.findAllByIsAvailableTrue(search, status, page);

        commandsInfos.put("allCommands", allCommands);
        commandsInfos.put("currentPage", allCommands.getNumber());
        commandsInfos.put("totalPages", allCommands.getTotalPages());
        commandsInfos.put("hasNext", allCommands.hasNext());
        commandsInfos.put("hasPrevious", allCommands.hasPrevious());
        commandsInfos.put("colors",  getStatusColor());
        commandsInfos.put("status", OrderStatus.values());
        return  commandsInfos;
    }

    public Map<String, Object> getCommandInfos (Long id) {
        Map<String, Object> commandInfos = new HashMap<>();

        commandInfos.put("commande",orderService.getOrder(id));
        commandInfos.put("colors",   getStatusColor());
        commandInfos.put("status", OrderStatus.values());
        return  commandInfos;
    }

    public Map<String, Object> getUsersInfos(
            String search,
            Pageable pageable
    ) {
        Map<String, Object> usersInfos = new HashMap<>();
        Page<User> allUsers = userService.findFilteredUsers(search, pageable);
        usersInfos.put("allUsers", allUsers);
        usersInfos.put("currentPage", allUsers.getNumber());
        usersInfos.put("totalPages", allUsers.getTotalPages());
        usersInfos.put("hasNext", allUsers.hasNext());
        usersInfos.put("hasPrevious", allUsers.hasPrevious());
        return  usersInfos;
    }

    public Map<String, Object> userInfos (
            Long id
    ) {
        Map<String, Object> userInfos = new HashMap<>();
        userInfos.put("user",userService.getUser(id));

        return  userInfos;
    }

    public boolean updateUser (
            long id,
            UserDTO userDTO
    ) {
        return userService.updateUser(id, userDTO);
    }

    public Map<String, Object> productsInfos (
            String search,
            Long category,
            Pageable pageable
    ) {
        Map<String, Object> productsInfos = new HashMap<>();
        Page<Product> allProducts = productService.findFilteredProduct(search, category, pageable);
        productsInfos.put("allProducts",allProducts);
        productsInfos.put("categories", categoryService.getAllCategory());
        productsInfos.put("currentPage", allProducts.getNumber());
        productsInfos.put("totalPages", allProducts.getTotalPages());
        productsInfos.put("hasNext", allProducts.hasNext());
        productsInfos.put("hasPrevious", allProducts.hasPrevious());
        return  productsInfos;
    }

    public Map<String, Object> productInfos (
            Long id
    ) {
        Map<String, Object> productInfos = new HashMap<>();
        productInfos.put("product", productService.get(id));
        productInfos.put("categories", categoryService.getAllCategory());
        return  productInfos;
    }

    public void updateProduct(
            Long id,
            ProductDTO productDTO
    ) {
       productService.updateProduct(productDTO, id);
    }

    public void createProduct(
            ProductDTO productDTO
    ) {
        productService.createProduct(productDTO);
    }

    public Map<String, Object> getCategoriesInfos() {
        Map<String, Object> categoriesInfos = new HashMap<>();
        categoriesInfos.put("categories", categoryService.getAllCategory());
        return  categoriesInfos;
    }

    public Map<OrderStatus, String> getStatusColor() {
        Map<OrderStatus, String> colors = new HashMap<>();
        colors.put(OrderStatus.IN_PROCESS, "fed7aa");
        colors.put(OrderStatus.IN_TRANSIT, "e9d5ff");
        colors.put(OrderStatus.DELIVERED, "fce7f3");
        colors.put(OrderStatus.RETURN_ASK, "bbf7d0");
        colors.put(OrderStatus.RETURNED, "fecaca");
        colors.put(OrderStatus.REFUNDED, "93c5fd");
        return colors;
    }
}
