package com.techzone.ecommerce.app.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        System.out.println(roles.contains("ROLE_ADMIN"));

        if (roles.contains("ROLE_ADMIN")) {
            System.out.println("Je suis admin");
            response.sendRedirect("/admin");
        } else {
            System.out.println("Je suis utilisateur");
            response.sendRedirect("/");
        }
    }
}