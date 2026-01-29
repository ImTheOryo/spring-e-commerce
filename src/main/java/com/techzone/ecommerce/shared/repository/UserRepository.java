package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt BETWEEN :start AND :end ")
    long countUsersBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
                SELECT u FROM User u WHERE
                (:search IS NULL OR 
                LOWER(u.firstname) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(u.lastname) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(u.phone) LIKE CONCAT('%', :search, '%') OR 
                LOWER(u.address) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<User> findFilteredUsers(@Param("search") String search, Pageable pageable);

    @Query("""
                SELECT u FROM User u WHERE
                (:search IS NULL OR 
                LOWER(u.firstname) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(u.lastname) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(u.phone) LIKE CONCAT('%', :search, '%') OR 
                LOWER(u.address) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    List<User> findFilteredUsers(@Param("search") String search);

    Optional<User> findById(long id);
}
