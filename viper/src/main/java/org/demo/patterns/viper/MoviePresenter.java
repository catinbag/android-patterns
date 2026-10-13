package org.demo.patterns.viper;

import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieItem;

public class MoviePresenter implements MovieContract.Presenter, MovieContract.InteractorOutput {
    private final MovieContract.View view;
    private final MovieContract.Interactor interactor;

    public MoviePresenter(MovieContract.View view, MovieContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onStart() {
        interactor.loadMovie();
    }

    @Override
    public void onFavoriteClicked() {
        interactor.toggleFavorite();
    }

    @Override
    public void onMovieLoaded(MovieItem item) {
        Movie movie = item.getMovie();
        view.showTitle(movie.getTitle());
        view.showInfo(movie.getYear() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
        view.showDescription(movie.getDescription());
        view.showFavorite(item.isFavorite());
    }

    @Override
    public void onMovieNotFound() {
        view.showError("Movie not found");
    }

    @Override
    public void onNotLoggedIn() {
        view.showError("Please log in");
    }
}
