package com.techzone.ecommerce.shared.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

}

