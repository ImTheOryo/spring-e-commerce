package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private OrderStatus status;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;

    public double getTotalPrice() {
        if (orderProductList == null) return 0;
        return orderProductList.stream()
                .mapToDouble(op -> (op.getPrice() * op.getQuantity()))
                .sum();
    }

    public int getTotalQty() {
        if (orderProductList == null) return 0;
        return orderProductList.stream()
                .mapToInt(OrderProduct::getQuantity)
                .sum();
    }
    @Transient
    private double total;
}
