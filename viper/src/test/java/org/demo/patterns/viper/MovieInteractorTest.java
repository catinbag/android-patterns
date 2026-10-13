package org.demo.patterns.viper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.junit.Test;

public class MovieInteractorTest {
    private final AppContainer app = new AppContainer();
    private final MovieContract.InteractorOutput output =
            mock(MovieContract.InteractorOutput.class);

    private MovieInteractor interactorFor(long movieId) {
        MovieInteractor interactor = new MovieInteractor(
                movieId, app.getMovie(), app.addFavorite(), app.removeFavorite());
        interactor.setOutput(output);
        return interactor;
    }

    @Test
    public void knownMovieIsReported() {
        interactorFor(6).loadMovie();
        verify(output).onMovieLoaded(org.mockito.ArgumentMatchers.any());
    }

    @Test
    public void unknownMovieReportsNotFound() {
        interactorFor(999).loadMovie();
        verify(output).onMovieNotFound();
    }

    @Test
    public void toggleWithoutLoginReportsNotLoggedIn() {
        interactorFor(6).toggleFavorite();
        verify(output).onNotLoggedIn();
    }
}
