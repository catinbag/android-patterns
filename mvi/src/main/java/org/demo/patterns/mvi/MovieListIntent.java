package org.demo.patterns.mvi;

import org.demo.patterns.core.model.MovieItem;

public final class MovieListIntent {
    public enum Type {
        START, QUERY_CHANGED, FAVORITES_ONLY_CHANGED, FAVORITE_CLICKED, MOVIE_CLICKED, LOGOUT_CLICKED
    }

    public final Type type;
    public final String query;            // for QUERY_CHANGED
    public final boolean favoritesOnly;   // for FAVORITES_ONLY_CHANGED
    public final MovieItem item;          // for FAVORITE_CLICKED, MOVIE_CLICKED

    private MovieListIntent(Type type) {
        this(type, null, false, null);
    }

    private MovieListIntent(Type type, String query) {
        this(type, query, false, null);
    }

    private MovieListIntent(Type type, boolean favoritesOnly) {
        this(type, null, favoritesOnly, null);
    }

    private MovieListIntent(Type type, MovieItem item) {
        this(type, null, false, item);
    }

    private MovieListIntent(Type type, String query, boolean favoritesOnly, MovieItem item) {
        this.type = type;
        this.query = query;
        this.favoritesOnly = favoritesOnly;
        this.item = item;
    }

    public static MovieListIntent start() {
        return new MovieListIntent(Type.START);
    }

    public static MovieListIntent queryChanged(String query) {
        return new MovieListIntent(Type.QUERY_CHANGED, query);
    }

    public static MovieListIntent favoritesOnlyChanged(boolean favoritesOnly) {
        return new MovieListIntent(Type.FAVORITES_ONLY_CHANGED, favoritesOnly);
    }

    public static MovieListIntent favoriteClicked(MovieItem item) {
        return new MovieListIntent(Type.FAVORITE_CLICKED, item);
    }

    public static MovieListIntent movieClicked(MovieItem item) {
        return new MovieListIntent(Type.MOVIE_CLICKED, item);
    }

    public static MovieListIntent logoutClicked() {
        return new MovieListIntent(Type.LOGOUT_CLICKED);
    }
}
