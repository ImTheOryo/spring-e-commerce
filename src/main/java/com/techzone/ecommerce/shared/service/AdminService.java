package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.entity.User;
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
