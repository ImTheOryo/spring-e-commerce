package com.techzone.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter

public class Role extends BaseEntity{
    private String name;
}
