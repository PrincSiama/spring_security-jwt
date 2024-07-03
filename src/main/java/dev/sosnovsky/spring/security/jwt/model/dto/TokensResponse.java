package dev.sosnovsky.spring.security.jwt.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ с access и refresh токенами")
public record TokensResponse(String accessToken, String refreshToken) {
}