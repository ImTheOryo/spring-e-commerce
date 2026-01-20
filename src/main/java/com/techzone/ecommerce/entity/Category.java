package com.techzone.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    private String name;
}
