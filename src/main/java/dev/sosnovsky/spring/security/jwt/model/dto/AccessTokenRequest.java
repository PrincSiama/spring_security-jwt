package dev.sosnovsky.spring.security.jwt.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос токенов с использованием refresh токена")
public record AccessTokenRequest(String refreshToken) {
}
