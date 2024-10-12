// src/main/java/com/example/demo/repository/UserRepository.java
package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
}
