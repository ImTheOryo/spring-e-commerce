package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.UserDTO;
import com.techzone.ecommerce.shared.entity.User;
import com.techzone.ecommerce.shared.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String email){
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

    public boolean updateUser(User user){
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public User updateUserApi(User user){
        try {
            return userRepository.save(user);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public boolean updateUser(long id, UserDTO userDTO){
        try {
            User currentUserInfo = userRepository.getReferenceById(id);
            currentUserInfo.setFirstname(userDTO.getFirstname());
            currentUserInfo.setLastname(userDTO.getLastname());
            currentUserInfo.setAddress(userDTO.getAddress());
            currentUserInfo.setPhone(userDTO.getPhone());
            userRepository.save(currentUserInfo);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public Page<User> findFilteredUsers (
            String search,
            Pageable page
    ) {
        return userRepository.findFilteredUsers(search, page);
    }

    public List<User> findFilteredUsers (String search) {
        return userRepository.findFilteredUsers(search);
    }

    public User getUser (Long id) {
        try {
            Optional<User> order = userRepository.findById(id);
            return order.orElse(null);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
