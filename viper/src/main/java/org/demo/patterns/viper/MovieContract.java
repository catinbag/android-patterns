package org.demo.patterns.viper;

import org.demo.patterns.core.model.MovieItem;

public interface MovieContract {

    interface View {
        void showTitle(String title);

        void showInfo(String info);

        void showDescription(String description);

        void showFavorite(boolean favorite);

        void showError(String message);
    }

    interface Presenter {
        void onStart();

        void onFavoriteClicked();
    }

    interface Interactor {
        void loadMovie();

        void toggleFavorite();
    }

    interface InteractorOutput {
        void onMovieLoaded(MovieItem item);

        void onMovieNotFound();

        void onNotLoggedIn();
    }
}
