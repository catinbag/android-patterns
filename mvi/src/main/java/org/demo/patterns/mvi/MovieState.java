package org.demo.patterns.mvi;

/** Everything the movie screen shows. The object is immutable. */
public final class MovieState {
    public final String title;
    public final String info;
    public final String description;
    public final boolean favorite;

    private MovieState(String title, String info, String description, boolean favorite) {
        this.title = title;
        this.info = info;
        this.description = description;
        this.favorite = favorite;
    }

    public static MovieState initial() {
        return new MovieState("", "", "", false);
    }

    public static MovieState of(String title, String info, String description, boolean favorite) {
        return new MovieState(title, info, description, favorite);
    }
}
