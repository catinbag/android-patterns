package org.demo.patterns.core.usecase;

import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;
import org.demo.patterns.core.repository.FavoritesRepository;
import org.demo.patterns.core.repository.MovieRepository;

public class AddFavoriteUseCase {
    private final MovieRepository movies;
    private final FavoritesRepository favorites;
    private final AuthRepository auth;

    public AddFavoriteUseCase(MovieRepository movies, FavoritesRepository favorites, AuthRepository auth) {
        this.movies = movies;
        this.favorites = favorites;
        this.auth = auth;
    }

    /**
     * @throws NotLoggedInException when nobody is logged in
     * @throws org.demo.patterns.core.error.MovieNotFoundException when the id is unknown
     */
    public void execute(long movieId) {
        User user = auth.getCurrentUser();
        if (user == null) throw new NotLoggedInException();
        movies.getById(movieId);
        favorites.add(user.getLogin(), movieId);
    }
}
