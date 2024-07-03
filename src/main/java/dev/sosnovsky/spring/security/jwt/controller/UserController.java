package dev.sosnovsky.spring.security.jwt.controller;

import dev.sosnovsky.spring.security.jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.UserDto;
import dev.sosnovsky.spring.security.jwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Контроллер для пользователей", description = "Позволяет создавать нового пользователя," +
        " получать пользователя по id или список пользователей, добавлять роль ADMIN")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание пользователя. Доступно для всех пользователей")
    public UserDto create(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userService.create(registerUserRequest);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение пользователя по id. Доступно только аутентифицированным пользователям")
    public UserDto getById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение списка всех пользователей. Доступно только пользователям с правами ADMIN")
    public List<UserDto> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @PostMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Назначение пользователю роли ADMIN. Доступно только пользователям с правами ADMIN")
    public UserDto setAdminRoleById(@PathVariable int id) {
        return userService.setAdminRoleById(id);
    }
}