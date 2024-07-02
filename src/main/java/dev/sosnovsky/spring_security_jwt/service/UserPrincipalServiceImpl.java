package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.exception.NotFoundException;
import dev.sosnovsky.spring_security_jwt.model.User;
import dev.sosnovsky.spring_security_jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserPrincipalServiceImpl implements UserPrincipalService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserFromPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new NotFoundException("Пользователь с email " + principal.getName() + " не найден"));
    }
}