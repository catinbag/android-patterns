package org.demo.patterns.core.model;

public enum Genre {
    DRAMA("Drama"),
    CRIME("Crime"),
    SCI_FI("Sci-Fi"),
    COMEDY("Comedy"),
    ANIMATION("Animation");

    private final String title;

    Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
