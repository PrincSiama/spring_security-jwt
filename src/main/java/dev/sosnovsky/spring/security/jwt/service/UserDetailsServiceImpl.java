package dev.sosnovsky.spring.security.jwt.service;

import dev.sosnovsky.spring.security.jwt.exception.NotFoundException;
import dev.sosnovsky.spring.security.jwt.model.User;
import dev.sosnovsky.spring.security.jwt.repository.UserRepository;
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