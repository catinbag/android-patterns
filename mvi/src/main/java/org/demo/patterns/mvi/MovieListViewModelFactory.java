package org.demo.patterns.mvi;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMoviesUseCase;
import org.demo.patterns.core.usecase.LogoutUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieListViewModelFactory implements ViewModelProvider.Factory {
    private final GetMoviesUseCase getMovies;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;
    private final LogoutUseCase logout;

    public MovieListViewModelFactory(GetMoviesUseCase getMovies, AddFavoriteUseCase addFavorite,
                                     RemoveFavoriteUseCase removeFavorite, LogoutUseCase logout) {
        this.getMovies = getMovies;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
        this.logout = logout;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> type) {
        return (T) new MovieListViewModel(getMovies, addFavorite, removeFavorite, logout);
    }
}
