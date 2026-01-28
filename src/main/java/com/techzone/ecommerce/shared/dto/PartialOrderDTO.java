package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderProduct;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PartialOrderDTO {
    private OrderStatus status;
    private double price;
    private long qty;
    private LocalDateTime createdAt;
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private List<PartialOrderProductDTO> orderProducts = new ArrayList<>();
    private String payment;

    public PartialOrderDTO(Order order) {
        this.status = order.getStatus();
        this.price = order.getTotalPrice();
        this.qty = order.getTotalQty();
        this.createdAt = order.getCreatedAt();
        this.id = order.getId();
        this.firstname = order.getFirstname();
        this.lastname = order.getLastname();
        this.address = order.getAddress();
        this.phone = order.getPhone();

        for (OrderProduct orderProduct : order.getOrderProductList()) {
            orderProducts.add(new PartialOrderProductDTO(orderProduct));
        }
    }

    public static List<PartialOrderDTO> orderToPartialOrders(List<Order> orders) {
        List<PartialOrderDTO> partialOrders = new ArrayList<>();
        for (Order order : orders) {
            partialOrders.add(new PartialOrderDTO(order));
        }
        return partialOrders;
    }
}
