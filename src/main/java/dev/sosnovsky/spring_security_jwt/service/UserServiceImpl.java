package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.exception.NotFoundException;
import dev.sosnovsky.spring_security_jwt.model.Role;
import dev.sosnovsky.spring_security_jwt.model.User;
import dev.sosnovsky.spring_security_jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.UserDto;
import dev.sosnovsky.spring_security_jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto create(RegisterUserRequest registerUserRequest) {
        User user = mapper.map(registerUserRequest, User.class);
        user.addRole(Role.USER);
        user.setRegistrationDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto getById(int id) {
        return mapper.map(findUserById(id), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto setAdminRoleById(int id) {
        User user = findUserById(id);
        user.addRole(Role.ADMIN);
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    private User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Невозможно получить пользователя. Пользователь с id " + id +
                        " не найден"));
    }
}