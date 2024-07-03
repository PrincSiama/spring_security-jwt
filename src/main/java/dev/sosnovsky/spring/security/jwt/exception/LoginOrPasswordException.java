package dev.sosnovsky.spring.security.jwt.exception;

public class LoginOrPasswordException extends RuntimeException {
    public LoginOrPasswordException(String message) {
        super(message);
    }
}
