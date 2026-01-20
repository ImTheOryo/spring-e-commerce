package com.techzone.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {
    private String firstname;
    private String lastname;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String email;
    private String password;
}
