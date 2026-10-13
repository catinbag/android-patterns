package org.demo.patterns.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMoviesUseCase;
import org.demo.patterns.core.usecase.LogoutUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private final GetMoviesUseCase getMovies;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private final LogoutUseCase logout;
    private MovieFilter filter = MovieFilter.none();

    private final MutableLiveData<List<MovieItem>> movies = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();
    private final MutableLiveData<Event<Long>> openMovie = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> openLogin = new MutableLiveData<>();

    public MovieListViewModel(GetMoviesUseCase getMovies, AddFavoriteUseCase addFavorite,
                              RemoveFavoriteUseCase removeFavorite, LogoutUseCase logout) {
        this.getMovies = getMovies;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
        this.logout = logout;
    }

    public LiveData<List<MovieItem>> getMovies() { return movies; }
    public LiveData<Event<String>> getMessage() { return message; }
    public LiveData<Event<Long>> getOpenMovie() { return openMovie; }
    public LiveData<Event<Boolean>> getOpenLogin() { return openLogin; }

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
            message.setValue(new Event<>("Please log in"));
        }
    }

    public void onMovieClicked(MovieItem item) {
        openMovie.setValue(new Event<>(item.getMovie().getId()));
    }

    public void onLogoutClicked() {
        logout.execute();
        openLogin.setValue(new Event<>(true));
    }

    private void refresh() {
        movies.setValue(getMovies.execute(filter));
    }
}
