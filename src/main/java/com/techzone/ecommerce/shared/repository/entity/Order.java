package com.techzone.ecommerce.shared.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime validation_date;
    private boolean isPayed;
    private boolean isDelivered;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;
}
