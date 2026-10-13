package org.demo.patterns.core.error;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid login or password");
    }
}
