package dev.sosnovsky.spring.security.jwt.service;

import dev.sosnovsky.spring.security.jwt.model.dto.AccessTokenRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.LoginRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.TokensResponse;

public interface AuthenticationService {

    TokensResponse login(LoginRequest loginRequest);

    TokensResponse getNewTokensByRefresh(AccessTokenRequest accessTokenRequest);
}