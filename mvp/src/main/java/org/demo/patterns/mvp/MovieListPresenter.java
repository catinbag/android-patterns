package org.demo.patterns.mvp;

import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMoviesUseCase;
import org.demo.patterns.core.usecase.LogoutUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieListPresenter {
    private final MovieListView view;
    private final GetMoviesUseCase getMovies;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private final LogoutUseCase logout;
    private MovieFilter filter = MovieFilter.none();

    public MovieListPresenter(MovieListView view, GetMoviesUseCase getMovies,
                              AddFavoriteUseCase addFavorite,
                              RemoveFavoriteUseCase removeFavorite, LogoutUseCase logout) {
        this.view = view;
        this.getMovies = getMovies;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
        this.logout = logout;
    }

    public void onStart() {
        refresh();
    }

    public void onQueryChanged(String query) {
        filter = filter.withQuery(query);
        refresh();
    }

    public void onFavoritesOnlyChanged(boolean favoritesOnly) {
        filter = filter.withFavoritesOnly(favoritesOnly);
        refresh();
    }

    public void onFavoriteClicked(MovieItem item) {
        long id = item.getMovie().getId();
        try {
            if (item.isFavorite()) removeFavorite.execute(id);
            else addFavorite.execute(id);
            refresh();
        } catch (NotLoggedInException e) {
            view.showError("Please log in");
        }
    }

    public void onMovieClicked(MovieItem item) {
        view.openMovie(item.getMovie().getId());
    }

    public void onLogoutClicked() {
        logout.execute();
        view.openLogin();
    }

    private void refresh() {
        view.showMovies(getMovies.execute(filter));
    }
}
