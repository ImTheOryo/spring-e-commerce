package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.OrderStatusCount;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderProduct;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.repository.OrderProductRepository;
import com.techzone.ecommerce.shared.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public long getOrdersCount() {
        return orderRepository.count();
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

    public double getRevenueGrowth() {
        LocalDateTime now = LocalDateTime.now();

        double current = getRevenueBetween(now.minusDays(7), now);
        double previous = getRevenueBetween(now.minusDays(14), now.minusDays(7));

        return calculatePercentageGrowth(current, previous);
    }

    public double getOrdersGrowth() {
        LocalDateTime now = LocalDateTime.now();

        long current = orderRepository.countOrdersBetweenDates(now.minusDays(7), now);
        long previous = orderRepository.countOrdersBetweenDates(now.minusDays(14), now.minusDays(7));

        return calculatePercentageGrowth((double) current, (double) previous);
    }

    private double calculatePercentageGrowth(double current, double previous) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((current - previous) / previous) * 100;
    }

    private double getRevenueBetween(LocalDateTime start, LocalDateTime end) {
        Double revenue = orderRepository.sumRevenueBetweenDates(start, end);
        return (revenue != null) ? revenue : 0.0;
    }

}
