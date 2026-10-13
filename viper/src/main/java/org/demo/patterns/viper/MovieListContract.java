package org.demo.patterns.viper;

import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;

import java.util.List;

public interface MovieListContract {

    interface View {
        void showMovies(List<MovieItem> items);

        void showError(String message);
    }

    interface Presenter {
        void onStart();

        void onQueryChanged(String query);

        void onFavoritesOnlyChanged(boolean favoritesOnly);

        void onFavoriteClicked(MovieItem item);

        void onMovieClicked(MovieItem item);

        void onLogoutClicked();
    }

    interface Interactor {
        void loadMovies(MovieFilter filter);

        void toggleFavorite(MovieItem item);

        void logout();
    }

    interface InteractorOutput {
        void onMoviesLoaded(List<MovieItem> items);

        void onFavoriteToggled();

        void onNotLoggedIn();
    }

    interface Router {
        void openMovie(long movieId);

        void openLogin();
    }
}
