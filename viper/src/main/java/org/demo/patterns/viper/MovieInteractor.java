package org.demo.patterns.viper;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieInteractor implements MovieContract.Interactor {
    private final long movieId;
    private final GetMovieUseCase getMovie;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private MovieContract.InteractorOutput output;

    public MovieInteractor(long movieId, GetMovieUseCase getMovie,
                           AddFavoriteUseCase addFavorite, RemoveFavoriteUseCase removeFavorite) {
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    public void setOutput(MovieContract.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public void loadMovie() {
        try {
            output.onMovieLoaded(getMovie.execute(movieId));
        } catch (MovieNotFoundException e) {
            output.onMovieNotFound();
        }
    }

    @Override
    public void toggleFavorite() {
        try {
            if (getMovie.execute(movieId).isFavorite()) removeFavorite.execute(movieId);
            else addFavorite.execute(movieId);
            loadMovie();
        } catch (NotLoggedInException e) {
            output.onNotLoggedIn();
        }
    }
}
