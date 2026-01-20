package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    private String firstname;
    private String lastname;

    private RoleEnum role;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
