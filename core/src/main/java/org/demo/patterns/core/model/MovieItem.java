package org.demo.patterns.core.model;

/** A movie together with the favourite flag of the current user. */
public final class MovieItem {
    private final Movie movie;
    private final boolean favorite;

    public MovieItem(Movie movie, boolean favorite) {
        this.movie = movie;
        this.favorite = favorite;
    }

    public Movie getMovie() { return movie; }
    public boolean isFavorite() { return favorite; }
}
