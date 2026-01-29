package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.RoleEnum;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.entityForm.UserForm;
import com.techzone.ecommerce.shared.repository.UserRepository;
import com.techzone.ecommerce.shared.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthentificationApiController {
    private final JwtEncoder encoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String registerPage(
            Model model
    ) {
        model.addAttribute("userForm", new UserForm());
        return "register/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid UserForm userForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            String firstError = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            model.addAttribute("error", firstError);
            return "register/register";
        }

        if (!Objects.equals(userForm.getPassword(), userForm.getConfirmPassword())) {
            model.addAttribute("error", "Les mots de passes ne correspondent pas");
            return "register/register";
        }

        if (userDetailsServiceImpl.existsByEmail(userForm.getEmail())) {
            model.addAttribute("error", "Email invalide");
            return "register/register";
        }

        try {
            String encodedPassword = bCryptPasswordEncoder.encode(userForm.getPassword().trim());
            User user = new User();
            user.setFirstname(userForm.getFirstName());
            user.setLastname(userForm.getLastName());
            user.setEmail(userForm.getEmail().toLowerCase().trim());
            user.setPassword(encodedPassword);
            user.setCreatedAt(LocalDateTime.now());
            user.setRole(RoleEnum.USER);
            Cart cart = new Cart();
            user.setCart(cart);

            userRepository.save(user);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur technique est survenue.");
            return "register/register";
        }
    }

    @PostMapping("/auth/login")
    public String token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replace("ROLE_", "")) // "ROLE_ADMIN" -> "ADMIN"
                .collect(Collectors.joining(" "));

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
