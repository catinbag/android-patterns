package org.demo.patterns.mvc;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieController {
    private final MovieActivity view;
    private final long movieId;
    private final GetMovieUseCase getMovie;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;

    public MovieController(MovieActivity view, long movieId, GetMovieUseCase getMovie,
                           AddFavoriteUseCase addFavorite, RemoveFavoriteUseCase removeFavorite) {
        this.view = view;
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    public void onStart() {
        try {
            view.showMovie(getMovie.execute(movieId));
        } catch (MovieNotFoundException e) {
            view.showError("Movie not found");
        }
    }

    public void onFavoriteClicked() {
        try {
            MovieItem item = getMovie.execute(movieId);
            if (item.isFavorite()) removeFavorite.execute(movieId);
            else addFavorite.execute(movieId);
            onStart();
        } catch (NotLoggedInException e) {
            view.showError("Please log in");
        }
    }
}
