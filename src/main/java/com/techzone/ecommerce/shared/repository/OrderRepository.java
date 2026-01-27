package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.dto.OrderStatusCount;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.OrderStatus;
import com.techzone.ecommerce.shared.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.techzone.ecommerce.shared.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.status as status, COUNT(o) as count FROM Order o GROUP BY o.status")
    List<OrderStatusCount> countOrdersByStatus();
    List<Order> findByCreatedAtBetweenAndUserOrderByCreatedAt(LocalDateTime start, LocalDateTime end, User user);
    @Query("SELECT DISTINCT YEAR(o.createdAt) FROM Order o WHERE o.user.id = :userId ORDER BY YEAR(o.createdAt) DESC")
    List<Integer> findDistinctYearsByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(op.quantity * p.price * (1 - op.promotionPourcent / 100.0)) " +
            "FROM OrderProduct op JOIN op.product p JOIN op.order o " +
            "WHERE o.createdAt BETWEEN :start AND :end " +
            "AND o.status NOT IN (com.techzone.ecommerce.shared.entity.OrderStatus.RETURNED, " +
            "com.techzone.ecommerce.shared.entity.OrderStatus.REFUNDED)")
    Double sumRevenueBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE o.createdAt BETWEEN :start AND :end " +
            "AND o.status NOT IN (com.techzone.ecommerce.shared.entity.OrderStatus.RETURNED, " +
            "com.techzone.ecommerce.shared.entity.OrderStatus.REFUNDED)")
    long countOrdersBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query("SELECT o FROM Order o WHERE " +
            "(:status IS NULL OR o.status = :status) AND " +
            "(:search IS NULL OR LOWER(o.user.firstname) LIKE LOWER(CONCAT('%', :search, '%')) OR CAST(o.id AS string) LIKE CONCAT('%', :search, '%'))")
    Page<Order> findFilteredOrders(@Param("search") String search, @Param("status") OrderStatus status, Pageable pageable);

}