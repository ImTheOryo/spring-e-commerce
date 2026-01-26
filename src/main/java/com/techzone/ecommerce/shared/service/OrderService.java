package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.OrderStatusCount;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.repository.OrderProductRepository;
import com.techzone.ecommerce.shared.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    public int getOrdersCount() {
        return orderRepository.findAll().size();
    }

    public double getTotalRevenue() {
        return orderProductRepository.findAll().stream()
                .mapToDouble(product -> {
                    int quantity = product.getQuantity();
                    double pricePerUnit = product.getProduct().getPrice();
                    double promotionPercent = product.getPromotionPourcent() / 100.0;

                    if (promotionPercent > 0) {
                        return (pricePerUnit * quantity) * (1 - promotionPercent);
                    } else {
                        return (pricePerUnit * quantity);
                    }
                })
                .sum();
    }

    public Map<String, Long> getOrdersStats() {
        List<OrderStatusCount> results = orderRepository.countOrdersByStatus();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> result.getStatus().getLabel(),
                        OrderStatusCount::getCount
                ));
    }
}
