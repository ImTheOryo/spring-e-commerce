package com.techzone.ecommerce.config;


import com.techzone.ecommerce.utils.RefillDb;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbLoader {

    @Bean
    CommandLineRunner initDatabase(RefillDb refillDb) {
        return args -> {
            System.out.println("--- Début de la génération des données ---");
            refillDb.init();
            System.out.println("--- Données générées avec succès ! ---");
        };
    }
}