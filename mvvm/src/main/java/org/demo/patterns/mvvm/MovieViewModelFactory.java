package org.demo.patterns.mvvm;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
    private final long movieId;
    private final GetMovieUseCase getMovie;
    private final AddFavoriteUseCase addFavorite;
    private final RemoveFavoriteUseCase removeFavorite;

    public MovieViewModelFactory(long movieId, GetMovieUseCase getMovie,
                                 AddFavoriteUseCase addFavorite,
                                 RemoveFavoriteUseCase removeFavorite) {
        this.movieId = movieId;
        this.getMovie = getMovie;
        this.addFavorite = addFavorite;
        this.removeFavorite = removeFavorite;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> type) {
        return (T) new MovieViewModel(movieId, getMovie, addFavorite, removeFavorite);
    }
}
