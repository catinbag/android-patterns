package org.demo.patterns.mvi;

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

public class MovieListViewModel extends ViewModel {
    private final GetMoviesUseCase getMovies;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private final LogoutUseCase logout;

    private final MutableLiveData<MovieListState> state =
            new MutableLiveData<>(MovieListState.initial());
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

    public LiveData<MovieListState> getState() { return state; }
    public LiveData<Event<String>> getMessage() { return message; }
    public LiveData<Event<Long>> getOpenMovie() { return openMovie; }
    public LiveData<Event<Boolean>> getOpenLogin() { return openLogin; }

    public void process(MovieListIntent intent) {
        switch (intent.type) {
            case START:
                reload();
                break;
            case QUERY_CHANGED:
                changeFilter(current().filter.withQuery(intent.query));
                break;
            case FAVORITES_ONLY_CHANGED:
                changeFilter(current().filter.withFavoritesOnly(intent.favoritesOnly));
                break;
            case FAVORITE_CLICKED:
                toggleFavorite(intent.item);
                break;
            case MOVIE_CLICKED:
                openMovie.setValue(new Event<>(intent.item.getMovie().getId()));
                break;
            case LOGOUT_CLICKED:
                logout.execute();
                openLogin.setValue(new Event<>(true));
                break;
        }
    }

    private MovieListState current() {
        return state.getValue();
    }

    private void reload() {
        changeFilter(current().filter);
    }

    private void changeFilter(MovieFilter filter) {
        state.setValue(current().withFilter(filter).withMovies(getMovies.execute(filter)));
    }

    private void toggleFavorite(MovieItem item) {
        long id = item.getMovie().getId();
        try {
            if (item.isFavorite()) removeFavorite.execute(id);
            else addFavorite.execute(id);
            reload();
        } catch (NotLoggedInException e) {
            message.setValue(new Event<>("Please log in"));
        }
    }
}
