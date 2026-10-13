package org.demo.patterns.mvvm;

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

    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<String> info = new MutableLiveData<>();
    private final MutableLiveData<String> description = new MutableLiveData<>();
    private final MutableLiveData<Boolean> favorite = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();

    public MovieViewModel(long movieId, GetMovieUseCase getMovie,
                          AddFavoriteUseCase addFavorite, RemoveFavoriteUseCase removeFavorite) {
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    public LiveData<String> getTitle() { return title; }
    public LiveData<String> getInfo() { return info; }
    public LiveData<String> getDescription() { return description; }
    public LiveData<Boolean> getFavorite() { return favorite; }
    public LiveData<Event<String>> getMessage() { return message; }

    public void onStart() {
        try {
            MovieItem item = getMovie.execute(movieId);
            Movie movie = item.getMovie();
            title.setValue(movie.getTitle());
            info.setValue(movie.getYear() + " · " + movie.getGenre().getTitle()
                    + " · " + movie.getRating());
            description.setValue(movie.getDescription());
            favorite.setValue(item.isFavorite());
        } catch (MovieNotFoundException e) {
            message.setValue(new Event<>("Movie not found"));
        }
    }

    public void onFavoriteClicked() {
        try {
            if (getMovie.execute(movieId).isFavorite()) removeFavorite.execute(movieId);
            else addFavorite.execute(movieId);
            onStart();
        } catch (NotLoggedInException e) {
            message.setValue(new Event<>("Please log in"));
        }
    }
}
