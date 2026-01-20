package com.techzone.ecommerce.shared.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
