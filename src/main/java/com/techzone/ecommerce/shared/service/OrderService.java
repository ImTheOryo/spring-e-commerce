package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.OrderStatusCount;
import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.entity.*;
import com.techzone.ecommerce.shared.repository.OrderProductRepository;
import com.techzone.ecommerce.shared.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;

    public List<Integer> getYears(Long userId) {
        return orderRepository.findDistinctYearsByUserId(userId);
    }

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

    public Map<OrderStatus, Long> getOrdersStats() {
        List<OrderStatusCount> results = orderRepository.countOrdersByStatus();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> result.getStatus(), // On garde l'Enum comme clé
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

    public Page<Order> findAllByIsAvailableTrue(String search, OrderStatus status, Pageable page) {
        return orderRepository.findFilteredOrders(search, status, page);
    }

    public List<Order> findAllByIsAvailableTrue(String search, OrderStatus status) {
        return orderRepository.findFilteredOrders(search, status);
    }

    public List<Order> getUserOrderByYear(int year, User user) {
        try {
            LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
            start = start.minusSeconds(1);
            LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
            end = end.plusSeconds(1);
            return orderRepository.findByCreatedAtBetweenAndUserOrderByCreatedAtDesc(start, end, user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Order> getOrders () {
        return orderRepository.findAll();
    }

    public Order getOrder(long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            return order.orElse(null);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;

    }

    public void changeStatus(Long id, OrderStatus status) {
        if (orderRepository.findById(id).isPresent()) {
            Order order = orderRepository.findById(id).get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    @Transactional
    public Order createOrder(Cart cart, PartialOrderDTO partialOrderDTO, User user) {
        Order order = new Order();
        order.setFirstname(partialOrderDTO.getFirstname());
        order.setLastname(partialOrderDTO.getLastname());
        order.setPhone(partialOrderDTO.getPhone());
        order.setAddress(partialOrderDTO.getAddress());
        order.setStatus(OrderStatus.IN_PROCESS);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderProduct> orderProducts = new ArrayList<>();
        double orderTotal = 0;

        for (CartProduct cartProduct : cart.getCartProductList()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProduct.setOrder(order);

            double currentPrice = cartProduct.getProduct().getPrice();
            orderProduct.setPrice(currentPrice);
            orderProduct.setPromotion(cartProduct.getProduct().isPromotion());
            orderProduct.setPromotionPourcent(cartProduct.getProduct().getPromotionPourcent());

            productService.removeFromStock(cartProduct.getQuantity(), cartProduct.getProduct());

            orderProducts.add(orderProduct);

            orderTotal += (currentPrice * cartProduct.getQuantity());
        }

        order.setOrderProductList(orderProducts);
        order.setTotal(orderTotal);

        return orderRepository.save(order);
    }

    public List<Order> getAllByUser(User user) {
        try {
            return orderRepository.findByUserId(user.getId());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return null;
    }

    public boolean changeStatusApi(Long id, OrderStatus status) {
        try {
            if (orderRepository.findById(id).isPresent()) {
                Order order = orderRepository.findById(id).get();
                order.setStatus(status);
                orderRepository.save(order);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
