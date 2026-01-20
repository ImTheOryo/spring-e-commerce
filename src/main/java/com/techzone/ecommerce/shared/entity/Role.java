package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class Role extends BaseEntity{
    private String name;
}
