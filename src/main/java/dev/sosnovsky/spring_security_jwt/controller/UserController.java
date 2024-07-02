package dev.sosnovsky.spring_security_jwt.controller;

import dev.sosnovsky.spring_security_jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.UserDto;
import dev.sosnovsky.spring_security_jwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание пользователя. Доступно для всех пользователей")
    public UserDto create(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userService.create(registerUserRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по id. Доступно только аутентифицированным пользователям")
    public UserDto getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Получение списка всех пользователей. Доступно только пользователям с правами ADMIN")
    public List<UserDto> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @PostMapping("/{id}")
    @Operation(summary = "Назначение пользователю роли ADMIN. Доступно только пользователям с правами ADMIN")
    public UserDto setAdminRoleById(@PathVariable int id) {
        return userService.setAdminRoleById(id);
    }
}