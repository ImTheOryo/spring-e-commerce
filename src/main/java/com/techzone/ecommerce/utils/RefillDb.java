package com.techzone.ecommerce.utils;

import com.github.javafaker.Faker;
import com.techzone.ecommerce.shared.entity.*;
import com.techzone.ecommerce.shared.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
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
    private final Faker faker = new Faker();
    private final byte[] promotion = {5, 10, 15, 20, 30, 40, 50, 60, 70};
    private final OrderStatus[] statuses = {OrderStatus.DELIVERED, OrderStatus.IN_PROCESS, OrderStatus.DELIVERED, OrderStatus.REFUNDED, OrderStatus.RETURNED, OrderStatus.IN_TRANSIT};

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void init() {
        createUser(10);
        createCategoryAndProduct();
        createCarts();
        createOrders();
    }

    public void createCategoryAndProduct() {
        Category ordinateur = new Category();
        ordinateur.setName("ordinateur");
        ordinateur = categoryRepository.save(ordinateur);

        Category ordinateurPortable = new Category();
        ordinateurPortable.setName("ordinateur portable");
        ordinateurPortable.setParent(ordinateur);
        ordinateurPortable = categoryRepository.save(ordinateurPortable);
        createProduct(ordinateurPortable, 15);

        Category ordinateurFixe = new Category();
        ordinateurFixe.setName("ordinateur fixe");
        ordinateurFixe.setParent(ordinateur);
        ordinateurFixe = categoryRepository.save(ordinateurFixe);
        createProduct(ordinateurFixe, 30);

        Category telephone = new Category();
        telephone.setName("téléphone");
        telephone = categoryRepository.save(telephone);

        Category telephonePortable = new Category();
        telephonePortable.setName("téléphone portable");
        telephonePortable.setParent(telephone);
        telephonePortable = categoryRepository.save(telephonePortable);
        createProduct(telephonePortable, 20);

        Category telephoneFixe = new Category();
        telephoneFixe.setName("téléphone fixe");
        telephoneFixe.setParent(telephone);
        telephoneFixe = categoryRepository.save(telephoneFixe);
        createProduct(telephoneFixe, 2);

        Category accesoires = new Category();
        accesoires.setName("accessoires");
        accesoires = categoryRepository.save(accesoires);

        Category montreConnectee = new Category();
        montreConnectee.setName("montre connectée");
        montreConnectee.setParent(accesoires);
        montreConnectee = categoryRepository.save(montreConnectee);
        createProduct(montreConnectee, 13);

        Category peripheriques = new Category();
        peripheriques.setName("périphériques");
        peripheriques.setParent(accesoires);
        peripheriques = categoryRepository.save(peripheriques);
        createProduct(peripheriques, 40);
    }

    public void createUser(int quantity) {
            String password = bCryptPasswordEncoder.encode("password");
        for (int i = 0; i < quantity; i++) {
            User user = new User();
            user.setRole(RoleEnum.USER);
            user.setFirstname(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setEmail(faker.internet().safeEmailAddress());
            user.setPassword(password);
            userRepository.save(user);
        }
        User user = new User();
        user.setRole(RoleEnum.USER);
        user.setFirstname(faker.name().firstName());
        user.setLastname(faker.name().lastName());
        user.setEmail("test@test.com");
        user.setPassword(password);
        userRepository.save(user);
    }

    public void createProduct(Category category, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Product product = new Product();
            product.setCategory(category);
            product.setName(faker.commerce().productName());
            product.setPrice(Double.parseDouble(faker.commerce().price()));
            product.setStock(getRandom(0, 400));
            product.setDescription(faker.chuckNorris().fact());
            product.setBrand(faker.pokemon().name());
            product.setModel(faker.zelda().character());
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
                cartProduct.setQuantity(getRandom(1, cartProduct.getProduct().getStock()));
                cartProductRepository.save(cartProduct);
            }
        }
    }

    public void createOrders() {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User user : users) {
            for (int i = 0; i < getRandom(0, 20); i++) {
                Order order = new Order();
                order.setUser(user);
                order.setValidationDate((faker.date().past(100, TimeUnit.DAYS)).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                order.setAddress(faker.address().fullAddress());
                order.setPhone(faker.phoneNumber().phoneNumber());
                order.setStatus(statuses[getRandom(0, statuses.length - 1)]);
                order = orderRepository.save(order);

                for (int j = 0; j < getRandom(0, 20); j++) {
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
    }

    public static int getRandom(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }
}
