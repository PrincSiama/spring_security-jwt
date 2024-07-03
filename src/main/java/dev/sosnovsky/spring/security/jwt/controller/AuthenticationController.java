package dev.sosnovsky.spring.security.jwt.controller;

import dev.sosnovsky.spring.security.jwt.model.dto.AccessTokenRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.LoginRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.TokensResponse;
import dev.sosnovsky.spring.security.jwt.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Контроллер для аутентификации", description = "Позволяет аутентифицировать пользователя по логину и паролю" +
        " и получить Access и Refresh токены")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Получение Access и Refresh токенов по логину и паролю. Доступно для всех пользователей")
    public TokensResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/token")
    @Operation(summary = "Получение Access и Refresh токенов по Refresh токену. Доступно для всех пользователей")
    public TokensResponse getNewTokens(@RequestBody AccessTokenRequest accessTokenRequest) {
        return authenticationService.getNewTokensByRefresh(accessTokenRequest);
    }
}