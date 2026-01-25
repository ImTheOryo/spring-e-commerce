package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    public double getUserGrowth() {
        LocalDateTime now = LocalDateTime.now();

        long current = userRepository.countUsersBetweenDates(now.minusDays(7), now);
        long previous = userRepository.countUsersBetweenDates(now.minusDays(14), now.minusDays(7));

        return calculatePercentageGrowth((double) current, (double) previous);
    }

    private double calculatePercentageGrowth(double current, double previous) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        return ((current - previous) / previous) * 100;
    }
}
