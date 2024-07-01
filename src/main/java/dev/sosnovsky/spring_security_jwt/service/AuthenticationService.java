package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.model.dto.AccessTokenRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.LoginRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.TokensResponse;

public interface AuthenticationService {

    TokensResponse login(LoginRequest loginRequest);

    TokensResponse getNewTokensByRefresh(AccessTokenRequest accessTokenRequest);
}