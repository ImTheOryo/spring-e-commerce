package com.techzone.ecommerce.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.techzone.ecommerce.app.handler.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final String jwtKey = "3cfa76ef14937c1c0ea519f8fc572f9af3083839d332997b63f5999083311650";
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,"RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return  new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/register", "/static/**", "/images/**", "/h2-console/**", "/error", "/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/admin/**", "/admin", "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/", "/product/**", "/api/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // --- ICI : ON AJOUTE LE SUPPORT DU BASIC AUTH POUR POSTMAN ---
                .httpBasic(Customizer.withDefaults())

                // 2. PARTIE WEB : Formulaire Classique
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(new CustomSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // 3. PARTIE API : Resource Server (JWT)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                            } else {
                                response.sendRedirect("/login");
                            }
                        })
                )
        // ... reste du code (logout, sessionManagement)

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .sessionManagement(session -> session
                        // Crucial: "IF_REQUIRED" permet au Web de créer une session,
                        // mais l'API avec JWT restera techniquement sans état par requête.
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        return http.build();
    }

}