package dev.sosnovsky.spring_security_jwt.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
