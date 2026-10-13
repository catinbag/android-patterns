package org.demo.patterns.mvi;

public final class MovieIntent {
    public enum Type {
        START, FAVORITE_CLICKED
    }

    public final Type type;

    private MovieIntent(Type type) {
        this.type = type;
    }

    public static MovieIntent start() {
        return new MovieIntent(Type.START);
    }

    public static MovieIntent favoriteClicked() {
        return new MovieIntent(Type.FAVORITE_CLICKED);
    }
}
