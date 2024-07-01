package dev.sosnovsky.spring_security_jwt.exception;

public class LoginOrPasswordException extends RuntimeException {
    public LoginOrPasswordException(String message) {
        super(message);
    }
}
