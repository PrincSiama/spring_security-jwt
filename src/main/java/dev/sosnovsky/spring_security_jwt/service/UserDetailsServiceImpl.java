package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.exception.NotFoundException;
import dev.sosnovsky.spring_security_jwt.model.User;
import dev.sosnovsky.spring_security_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NotFoundException("Пользователь с email " + email + " не найден"));
    }
}