package org.demo.patterns.viper;

import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;

import java.util.List;

public class MovieListPresenter
        implements MovieListContract.Presenter, MovieListContract.InteractorOutput {
    private final MovieListContract.View view;
    private final MovieListContract.Interactor interactor;
    private final MovieListContract.Router router;
    private MovieFilter filter = MovieFilter.none();

    public MovieListPresenter(MovieListContract.View view, MovieListContract.Interactor interactor,
                              MovieListContract.Router router) {
        this.view = view;
        this.interactor = interactor;
        this.router = router;
    }

    @Override
    public void onStart() {
        interactor.loadMovies(filter);
    }

    @Override
    public void onQueryChanged(String query) {
        filter = filter.withQuery(query);
        interactor.loadMovies(filter);
    }

    @Override
    public void onFavoritesOnlyChanged(boolean favoritesOnly) {
        filter = filter.withFavoritesOnly(favoritesOnly);
        interactor.loadMovies(filter);
    }

    @Override
    public void onFavoriteClicked(MovieItem item) {
        interactor.toggleFavorite(item);
    }

    @Override
    public void onMovieClicked(MovieItem item) {
        router.openMovie(item.getMovie().getId());
    }

    @Override
    public void onLogoutClicked() {
        interactor.logout();
        router.openLogin();
    }

    @Override
    public void onMoviesLoaded(List<MovieItem> items) {
        view.showMovies(items);
    }

    @Override
    public void onFavoriteToggled() {
        interactor.loadMovies(filter);
    }

    @Override
    public void onNotLoggedIn() {
        view.showError("Please log in");
    }
}
