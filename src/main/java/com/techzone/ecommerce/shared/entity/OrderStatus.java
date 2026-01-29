package com.techzone.ecommerce.shared.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    IN_PROCESS("IN_PROCESS", "En préparation", "amber"),
    IN_TRANSIT("IN_TRANSIT", "En cours de livraison", "blue"),
    DELIVERED("DELIVERED", "Livré", "emerald"),
    RETURN_ASK("RETURN_ASK", "Retour demandé", "purple"),
    RETURNED("RETURNED", "Retour reçu", "slate"),
    REFUNDED("REFUNDED", "Remboursé", "rose");
    private final String code;
    private final String label;
    private final String color;

    OrderStatus(String code, String label, String color) {
        this.code = code;
        this.label = label;
        this.color = color;
    }

}
