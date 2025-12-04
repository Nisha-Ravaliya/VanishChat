package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUsername(String username);
    Optional<Registration> findByEmail(String email);
    Optional<Registration> findByUsernameOrEmail(String username, String email);
    Optional<Registration> findByEmailOrUsername(String email, String username);
}
