package org.demo.patterns.viper;

import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMoviesUseCase;
import org.demo.patterns.core.usecase.LogoutUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieListInteractor implements MovieListContract.Interactor {
    private final GetMoviesUseCase getMovies;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private final LogoutUseCase logout;
    private MovieListContract.InteractorOutput output;

    public MovieListInteractor(GetMoviesUseCase getMovies, AddFavoriteUseCase addFavorite,
                               RemoveFavoriteUseCase removeFavorite, LogoutUseCase logout) {
        this.getMovies = getMovies;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
        this.logout = logout;
    }

    public void setOutput(MovieListContract.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public void loadMovies(MovieFilter filter) {
        output.onMoviesLoaded(getMovies.execute(filter));
    }

    @Override
    public void toggleFavorite(MovieItem item) {
        long id = item.getMovie().getId();
        try {
            if (item.isFavorite()) removeFavorite.execute(id);
            else addFavorite.execute(id);
            output.onFavoriteToggled();
        } catch (NotLoggedInException e) {
            output.onNotLoggedIn();
        }
    }

    @Override
    public void logout() {
        logout.execute();
    }
}
