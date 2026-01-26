package com.techzone.ecommerce.utils;

import com.github.javafaker.Faker;
import com.techzone.ecommerce.shared.entity.*;
import com.techzone.ecommerce.shared.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefillDb {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final Faker faker = new Faker(new Locale("fr"));
    private final byte[] promotion = {5, 10, 15, 20, 30, 40, 50, 60, 70};
    private final OrderStatus[] statuses = {OrderStatus.DELIVERED, OrderStatus.IN_PROCESS, OrderStatus.DELIVERED, OrderStatus.REFUNDED, OrderStatus.RETURNED, OrderStatus.IN_TRANSIT};
    private String[] brands;
    private final int qtyBrands = 10;
    private final String[] emails = {"@gmail.com","@live.fr", "@live.com","@test.com","@test.fr"};

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void init() {

        brands = new String[qtyBrands];

        for (int i = 0; i < qtyBrands; i++) {
            brands[i] = faker.pokemon().name();
        }
        createCategoryAndProduct();

        User user = createUser(100);
        createCarts();
        createOrders(user);
    }

    public void createCategoryAndProduct() {
        Category ordinateur = new Category();
        ordinateur.setName("ordinateur");
        ordinateur = categoryRepository.save(ordinateur);
        createProduct(ordinateur, 20);

        Category telephone = new Category();
        telephone.setName("téléphone");
        telephone = categoryRepository.save(telephone);
        createProduct(telephone, 10);

        Category accesoires = new Category();
        accesoires.setName("accessoires");
        accesoires = categoryRepository.save(accesoires);
        createProduct(accesoires, 76);
    }

    public User createUser(int quantity) {
        String password = bCryptPasswordEncoder.encode("password");
        for (int i = 0; i < quantity; i++) {
            User user = new User();
            user.setRole(RoleEnum.USER);
            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setEmail(user.getFirstname() + user.getLastname() + emails[getRandom(0, emails.length - 1)]);
            user.setPassword(password);
            userRepository.save(user);
        }
        User user = new User();
        user.setRole(RoleEnum.ADMIN);
        user.setFirstname(faker.name().firstName());
        user.setLastname(faker.name().lastName());
        user.setEmail("admin@test.com");
        user.setPassword(password);
        userRepository.save(user);

        User user2 = new User();
        user2.setRole(RoleEnum.USER);
        user2.setFirstname(faker.name().firstName());
        user2.setLastname(faker.name().lastName());
        user2.setEmail("user@test.com");
        user2.setPassword(password);
        userRepository.save(user2);

        return user2;
    }

    public void createProduct(Category category, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Product product = new Product();
            product.setCategory(category);
            product.setName(faker.commerce().productName());
            product.setPrice(Double.parseDouble(faker.commerce().price()));
            product.setStock(getRandom(0, 400));
            product.setDescription(faker.lorem().paragraph(getRandom(2, 6)));
            product.setBrand(brands[getRandom(0, qtyBrands - 1)]);
            product.setModel(faker.company().name());
            product.setAvailable(faker.bool().bool());
            product.setInStock(product.getStock() > 0);
            product.setUrlPhoto("https://picsum.photos/seed/" + getRandom(1, 1000) + "/400/500");
            product.setPromotion(faker.bool().bool());
            product.setPromotionPourcent(product.isPromotion() ? promotion[getRandom(0, promotion.length - 1)] : 0);
            productRepository.save(product);
        }
    }

    public void createCarts() {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User user : users) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
            for (int i = 0; i < getRandom(0, 20); i++) {
                CartProduct cartProduct = new CartProduct();
                cartProduct.setCart(cart);
                cartProduct.setProduct(products.get(getRandom(0, products.size() - 1)));
                cartProduct.setQuantity(Math.min(getRandom(1, 10), cartProduct.getProduct().getStock()));
                cartProductRepository.save(cartProduct);
            }
        }
    }

    public void createOrders(User user2) {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User user : users) {
            for (int i = 0; i < getRandom(0, 20); i++) {
                Order order = new Order();
                order.setUser(user);
                order.setCreatedAt((faker.date().past(100, TimeUnit.DAYS)).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                order.setAddress(faker.address().fullAddress());
                order.setPhone(faker.phoneNumber().phoneNumber());
                order.setStatus(statuses[getRandom(0, statuses.length - 1)]);
                order = orderRepository.save(order);

                for (int j = 0; j < getRandom(1, 20); j++) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(products.get(getRandom(0, products.size() - 1)));
                    orderProduct.setQuantity(getRandom(1, orderProduct.getProduct().getStock()));
                    orderProduct.setPromotion(faker.bool().bool());
                    orderProduct.setPromotionPourcent(orderProduct.isPromotion() ? promotion[getRandom(0, promotion.length - 1)] : 0);
                    orderProductRepository.save(orderProduct);
                }
            }

        }


        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            Date date = faker.date().past(120, TimeUnit.DAYS);
            LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            order.setCreatedAt(dateTime);
            orderRepository.save(order);
        }

        for (int i = 1; i <= 2; i++) {
            Order order = new Order();
            order.setUser(user2);
            order.setCreatedAt(LocalDateTime.now().minusYears(i));
            order.setAddress(faker.address().fullAddress());
            order.setPhone(faker.phoneNumber().phoneNumber());
            order.setStatus(statuses[getRandom(0, statuses.length - 1)]);
            order = orderRepository.save(order);

            for (int j = 0; j < getRandom(1, 20); j++) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(products.get(getRandom(0, products.size() - 1)));
                orderProduct.setQuantity(getRandom(1, orderProduct.getProduct().getStock()));
                orderProduct.setPromotion(faker.bool().bool());
                orderProduct.setPromotionPourcent(orderProduct.isPromotion() ? promotion[getRandom(0, promotion.length - 1)] : 0);
                orderProductRepository.save(orderProduct);
            }
        }
    }

    public static int getRandom(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }
}
