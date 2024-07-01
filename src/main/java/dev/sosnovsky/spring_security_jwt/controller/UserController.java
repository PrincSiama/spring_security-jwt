package dev.sosnovsky.spring_security_jwt.controller;

import dev.sosnovsky.spring_security_jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.UserDto;
import dev.sosnovsky.spring_security_jwt.service.UserService;
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

    // Доступно всем
    @PostMapping
    public UserDto create(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return userService.create(registerUserRequest);
    }

    // Доступно только аутентифицированным пользователям
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable int id) {
        return userService.getById(id);
    }

    // Доступно только пользователям с правами Admin
    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    // Доступно только пользователям с правами Admin
    @PostMapping("/{id}")
    public void setAdminRoleById(@PathVariable int id) {
        userService.setAdminRoleById(id);
    }
}