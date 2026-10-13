package org.demo.patterns.mvi;

import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;

import java.util.Collections;
import java.util.List;

/** Everything the list screen shows. The object is immutable. */
public final class MovieListState {
    public final List<MovieItem> movies;
    public final MovieFilter filter;

    private MovieListState(List<MovieItem> movies, MovieFilter filter) {
        this.movies = movies;
        this.filter = filter;
    }

    public static MovieListState initial() {
        return new MovieListState(Collections.<MovieItem>emptyList(), MovieFilter.none());
    }

    public MovieListState withMovies(List<MovieItem> movies) {
        return new MovieListState(movies, filter);
    }

    public MovieListState withFilter(MovieFilter filter) {
        return new MovieListState(movies, filter);
    }
}
