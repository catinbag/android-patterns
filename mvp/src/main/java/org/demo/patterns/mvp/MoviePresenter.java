package org.demo.patterns.mvp;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MoviePresenter {
    private final MovieView view;
    private final long movieId;
    private final GetMovieUseCase getMovie;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;

    public MoviePresenter(MovieView view, long movieId, GetMovieUseCase getMovie,
                          AddFavoriteUseCase addFavorite, RemoveFavoriteUseCase removeFavorite) {
        this.view = view;
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    public void onStart() {
        try {
            MovieItem item = getMovie.execute(movieId);
            Movie movie = item.getMovie();
            view.showTitle(movie.getTitle());
            view.showInfo(movie.getYear() + " · " + movie.getGenre().getTitle()
                    + " · " + movie.getRating());
            view.showDescription(movie.getDescription());
            view.showFavorite(item.isFavorite());
        } catch (MovieNotFoundException e) {
            view.showError("Movie not found");
        }
    }

    public void onFavoriteClicked() {
        try {
            if (getMovie.execute(movieId).isFavorite()) removeFavorite.execute(movieId);
            else addFavorite.execute(movieId);
            onStart();
        } catch (NotLoggedInException e) {
            view.showError("Please log in");
        }
    }
}
