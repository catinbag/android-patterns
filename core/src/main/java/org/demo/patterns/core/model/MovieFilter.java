package org.demo.patterns.core.model;

import java.util.Objects;

/** Immutable filter for the movie list. Each "with" method returns a new object. */
public final class MovieFilter {
    private static final MovieFilter NONE = new MovieFilter(null, "", false);

    private final Genre genre;
    private final String query;
    private final boolean favoritesOnly;

    private MovieFilter(Genre genre, String query, boolean favoritesOnly) {
        this.genre = genre;
        this.query = query;
        this.favoritesOnly = favoritesOnly;
    }

    public static MovieFilter none() {
        return NONE;
    }

    public MovieFilter withGenre(Genre genre) {
        return new MovieFilter(genre, query, favoritesOnly);
    }

    public MovieFilter withQuery(String query) {
        return new MovieFilter(genre, query == null ? "" : query, favoritesOnly);
    }

    public MovieFilter withFavoritesOnly(boolean favoritesOnly) {
        return new MovieFilter(genre, query, favoritesOnly);
    }

    /** Returns null when the genre is not filtered. */
    public Genre getGenre() { return genre; }
    public String getQuery() { return query; }
    public boolean isFavoritesOnly() { return favoritesOnly; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MovieFilter)) return false;
        MovieFilter f = (MovieFilter) o;
        return genre == f.genre && query.equals(f.query) && favoritesOnly == f.favoritesOnly;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre, query, favoritesOnly);
    }
}
