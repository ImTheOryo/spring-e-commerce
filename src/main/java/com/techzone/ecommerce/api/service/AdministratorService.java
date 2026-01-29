package com.techzone.ecommerce.api.service;

import com.techzone.ecommerce.shared.dto.PartialOrderDTO;
import com.techzone.ecommerce.shared.dto.ProductDTO;
import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.Order;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.OrderService;
import com.techzone.ecommerce.shared.service.ProductService;
import com.techzone.ecommerce.shared.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorService {
    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhone(user.getPhone());
        return userDTO;
    }
    
    public ProductDTO getproduct(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setInStock(product.isInStock());
        productDTO.setAvailable(product.isAvailable());
        productDTO.setStock(product.getStock());
        productDTO.setPrice(product.getPrice());
        productDTO.setPromotion(product.isPromotion());
        productDTO.setBrand(product.getBrand());
        productDTO.setModel(product.getModel());
        productDTO.setUrlPhoto(product.getUrlPhoto());
        productDTO.setPromotionPourcent(product.getPromotionPourcent());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory().getId());
        return productDTO;
    }
    
    public List<Order> getCommands(){
        return orderService.getOrders();
    }

    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    public User getUserById(Long id){
        try {
            return userService.getUser(id);
        } catch (Exception e){
            return null;
        }
    }

    public List<Product> getProducts(){
        return productService.getAll();
    }

    public Product getProductById(Long id){
        try {
            return productService.get(id);
        }  catch (Exception e){
            return null;
        }
    }
}
