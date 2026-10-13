package org.demo.patterns.core.error;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(long id) {
        super("Movie not found: " + id);
    }
}
