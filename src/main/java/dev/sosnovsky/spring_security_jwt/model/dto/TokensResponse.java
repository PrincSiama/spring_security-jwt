package dev.sosnovsky.spring_security_jwt.model.dto;

public record TokensResponse(String accessToken, String refreshToken) {
}