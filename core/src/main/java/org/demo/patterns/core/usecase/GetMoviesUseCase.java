package org.demo.patterns.core.usecase;

import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;
import org.demo.patterns.core.repository.FavoritesRepository;
import org.demo.patterns.core.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GetMoviesUseCase {
    private final MovieRepository movies;
    private final FavoritesRepository favorites;
    private final AuthRepository auth;

    public GetMoviesUseCase(MovieRepository movies, FavoritesRepository favorites, AuthRepository auth) {
        this.movies = movies;
        this.favorites = favorites;
        this.auth = auth;
    }

    public List<MovieItem> execute(MovieFilter filter) {
        User user = auth.getCurrentUser();
        Set<Long> favoriteIds = user == null
                ? Collections.<Long>emptySet()
                : favorites.getIds(user.getLogin());
        String query = filter.getQuery().trim().toLowerCase(Locale.ROOT);

        List<MovieItem> result = new ArrayList<>();
        for (Movie movie : movies.getAll()) {
            if (filter.getGenre() != null && movie.getGenre() != filter.getGenre()) continue;
            if (!query.isEmpty() && !movie.getTitle().toLowerCase(Locale.ROOT).contains(query)) continue;
            boolean favorite = favoriteIds.contains(movie.getId());
            if (filter.isFavoritesOnly() && !favorite) continue;
            result.add(new MovieItem(movie, favorite));
        }
        return result;
    }
}
