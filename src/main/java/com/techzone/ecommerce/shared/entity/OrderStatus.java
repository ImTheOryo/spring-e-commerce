package com.techzone.ecommerce.shared.entity;

public enum OrderStatus {
    IN_PROCESS("IN_PROCESS", "En préparation"),
    IN_TRANSIT("IN_TRANSIT", "En cours de livraison"),
    DELIVERED("DELIVERED", "Livré"),
    RETURN_ASK("RETURN_ASK", "Retour demandé"),
    RETURNED("RETURNED", "Retour reçu"),
    REFUNDED("REFUNDED", "Remboursé");
    private final String code;
    private final String label;

    OrderStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
