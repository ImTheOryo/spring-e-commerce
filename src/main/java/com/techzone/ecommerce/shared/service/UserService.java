package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public com.techzone.ecommerce.shared.entity.User getUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public int getUsersCount() {
        return userRepository.findAll().size();
    }
}
