package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.dto.OrderStatusCount;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.status as status, COUNT(o) as count FROM Order o GROUP BY o.status")
    List<OrderStatusCount> countOrdersByStatus();
}
