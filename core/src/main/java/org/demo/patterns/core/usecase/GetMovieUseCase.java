package org.demo.patterns.core.usecase;

import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;
import org.demo.patterns.core.repository.FavoritesRepository;
import org.demo.patterns.core.repository.MovieRepository;

public class GetMovieUseCase {
    private final MovieRepository movies;
    private final FavoritesRepository favorites;
    private final AuthRepository auth;

    public GetMovieUseCase(MovieRepository movies, FavoritesRepository favorites, AuthRepository auth) {
        this.movies = movies;
        this.favorites = favorites;
        this.auth = auth;
    }

    /** @throws org.demo.patterns.core.error.MovieNotFoundException when the id is unknown */
    public MovieItem execute(long id) {
        Movie movie = movies.getById(id);
        User user = auth.getCurrentUser();
        boolean favorite = user != null && favorites.getIds(user.getLogin()).contains(id);
        return new MovieItem(movie, favorite);
    }
}
