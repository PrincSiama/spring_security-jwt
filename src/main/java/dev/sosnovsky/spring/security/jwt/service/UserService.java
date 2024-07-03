package dev.sosnovsky.spring.security.jwt.service;

import dev.sosnovsky.spring.security.jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto create(RegisterUserRequest registerUserRequest);

    UserDto getById(int id);

    List<UserDto> getAllUsers(Pageable pageable);

    UserDto setAdminRoleById(int id);
}