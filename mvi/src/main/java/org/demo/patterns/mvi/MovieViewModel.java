package org.demo.patterns.mvi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieViewModel extends ViewModel {
    private final long movieId;
    private final GetMovieUseCase getMovie;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;

    private final MutableLiveData<MovieState> state =
            new MutableLiveData<>(MovieState.initial());
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();

    public MovieViewModel(long movieId, GetMovieUseCase getMovie,
                          AddFavoriteUseCase addFavorite, RemoveFavoriteUseCase removeFavorite) {
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    public LiveData<MovieState> getState() { return state; }
    public LiveData<Event<String>> getMessage() { return message; }

    public void process(MovieIntent intent) {
        switch (intent.type) {
            case START:
                load();
                break;
            case FAVORITE_CLICKED:
                toggleFavorite();
                break;
        }
    }

    private void load() {
        try {
            MovieItem item = getMovie.execute(movieId);
            Movie movie = item.getMovie();
            state.setValue(MovieState.of(
                    movie.getTitle(),
                    movie.getYear() + " · " + movie.getGenre().getTitle()
                            + " · " + movie.getRating(),
                    movie.getDescription(),
                    item.isFavorite()));
        } catch (MovieNotFoundException e) {
            message.setValue(new Event<>("Movie not found"));
        }
    }

    private void toggleFavorite() {
        try {
            if (getMovie.execute(movieId).isFavorite()) removeFavorite.execute(movieId);
            else addFavorite.execute(movieId);
            load();
        } catch (NotLoggedInException e) {
            message.setValue(new Event<>("Please log in"));
        }
    }
}
