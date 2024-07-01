package dev.sosnovsky.spring_security_jwt.repository;

import dev.sosnovsky.spring_security_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);
}
