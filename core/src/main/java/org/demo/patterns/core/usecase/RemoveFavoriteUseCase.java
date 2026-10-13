package org.demo.patterns.core.usecase;

import org.demo.patterns.core.error.NotLoggedInException;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;
import org.demo.patterns.core.repository.FavoritesRepository;

public class RemoveFavoriteUseCase {
    private final FavoritesRepository favorites;
    private final AuthRepository auth;

    public RemoveFavoriteUseCase(FavoritesRepository favorites, AuthRepository auth) {
        this.favorites = favorites;
        this.auth = auth;
    }

    /** @throws NotLoggedInException when nobody is logged in */
    public void execute(long movieId) {
        User user = auth.getCurrentUser();
        if (user == null) throw new NotLoggedInException();
        favorites.remove(user.getLogin(), movieId);
    }
}
