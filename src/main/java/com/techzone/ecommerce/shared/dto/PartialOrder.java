package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PartialOrder {
    private OrderStatus status;
    private double price;
    private long qty;
    private LocalDateTime createdAt;
    private long id;

    PartialOrder(Order order) {
        this.status = order.getStatus();
        this.price = order.getTotalPrice();
        this.qty = order.getTotalQty();
        this.createdAt = order.getCreatedAt();
        this.id = order.getId();
    }

    public static List<PartialOrder> orderToPartialOrders(List<Order> orders) {
        List<PartialOrder> partialOrders = new ArrayList<>();
        for (Order order : orders) {
            partialOrders.add(new PartialOrder(order));
        }
        return partialOrders;
    }
}
