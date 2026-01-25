package com.techzone.ecommerce.shared.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return dashboardInfos;
    }

    public List<String> getStatusColor() {
        return List.of("#fed7aa", "#e9d5ff", "#fce7f3", "#bbf7d0", "#fecaca", "#93c5fd");
    }
}
