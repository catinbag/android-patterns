package org.demo.patterns.core.error;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("User is not logged in");
    }
}
