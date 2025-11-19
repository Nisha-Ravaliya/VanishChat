package com.example.VanishChat.Repository;

import com.example.VanishChat.Model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUsernameOrEmail(String username, String email);


}
