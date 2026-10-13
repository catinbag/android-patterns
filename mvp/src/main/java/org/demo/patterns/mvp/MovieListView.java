package org.demo.patterns.mvp;

import org.demo.patterns.core.model.MovieItem;

import java.util.List;

public interface MovieListView {
    void showMovies(List<MovieItem> items);

    void showError(String message);

    void openMovie(long movieId);

    void openLogin();
}
