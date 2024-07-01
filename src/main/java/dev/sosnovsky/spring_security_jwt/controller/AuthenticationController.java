package dev.sosnovsky.spring_security_jwt.controller;

import dev.sosnovsky.spring_security_jwt.model.dto.AccessTokenRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.LoginRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.TokensResponse;
import dev.sosnovsky.spring_security_jwt.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // Доступно всем пользователям
    @PostMapping("/login")
    public TokensResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    // Доступно всем пользователям
    @PostMapping("/token")
    public TokensResponse getNewTokens(@RequestBody AccessTokenRequest accessTokenRequest) {
        return authenticationService.getNewTokensByRefresh(accessTokenRequest);
    }
}