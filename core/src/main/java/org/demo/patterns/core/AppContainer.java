package org.demo.patterns.core;

import org.demo.patterns.core.data.InMemoryAuthRepository;
import org.demo.patterns.core.data.InMemoryFavoritesRepository;
import org.demo.patterns.core.data.InMemoryMovieRepository;
import org.demo.patterns.core.repository.AuthRepository;
import org.demo.patterns.core.repository.FavoritesRepository;
import org.demo.patterns.core.repository.MovieRepository;
import org.demo.patterns.core.usecase.AddFavoriteUseCase;
import org.demo.patterns.core.usecase.GetCurrentUserUseCase;
import org.demo.patterns.core.usecase.GetMovieUseCase;
import org.demo.patterns.core.usecase.GetMoviesUseCase;
import org.demo.patterns.core.usecase.LoginUseCase;
import org.demo.patterns.core.usecase.LogoutUseCase;
import org.demo.patterns.core.usecase.RemoveFavoriteUseCase;

/**
 * Creates repositories and use cases once. An Application class of each app
 * module owns one instance, so data survives screen changes.
 */
public class AppContainer {
    private final MovieRepository movieRepository = new InMemoryMovieRepository();
    private final FavoritesRepository favoritesRepository = new InMemoryFavoritesRepository();
    private final AuthRepository authRepository = new InMemoryAuthRepository();

    private final GetMoviesUseCase getMovies =
            new GetMoviesUseCase(movieRepository, favoritesRepository, authRepository);
    private final GetMovieUseCase getMovie =
            new GetMovieUseCase(movieRepository, favoritesRepository, authRepository);
    private final AddFavoriteUseCase addFavorite =
            new AddFavoriteUseCase(movieRepository, favoritesRepository, authRepository);
    private final RemoveFavoriteUseCase removeFavorite =
            new RemoveFavoriteUseCase(favoritesRepository, authRepository);
    private final LoginUseCase login = new LoginUseCase(authRepository);
    private final LogoutUseCase logout = new LogoutUseCase(authRepository);
    private final GetCurrentUserUseCase getCurrentUser = new GetCurrentUserUseCase(authRepository);

    public GetMoviesUseCase getMovies() { return getMovies; }
    public GetMovieUseCase getMovie() { return getMovie; }
    public AddFavoriteUseCase addFavorite() { return addFavorite; }
    public RemoveFavoriteUseCase removeFavorite() { return removeFavorite; }
    public LoginUseCase login() { return login; }
    public LogoutUseCase logout() { return logout; }
    public GetCurrentUserUseCase getCurrentUser() { return getCurrentUser; }
}
