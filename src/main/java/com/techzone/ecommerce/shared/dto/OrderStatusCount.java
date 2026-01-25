package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.OrderStatus;

public interface OrderStatusCount {
    OrderStatus getStatus();
    Long getCount();
}
