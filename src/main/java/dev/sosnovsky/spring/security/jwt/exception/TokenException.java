package dev.sosnovsky.spring.security.jwt.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
