package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.exception.NotFoundException;
import dev.sosnovsky.spring_security_jwt.model.User;
import dev.sosnovsky.spring_security_jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserPrincipalServiceImpl implements UserPrincipalService {
    private final UserRepository userRepository;

    @Override
    public User getUserFromPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new NotFoundException("Пользователь с email " + principal.getName() + " не найден"));
    }
}
