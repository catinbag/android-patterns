package org.demo.patterns.viper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

public class MovieListInteractorTest {
    private final AppContainer app = new AppContainer();
    private final MovieListContract.InteractorOutput output =
            mock(MovieListContract.InteractorOutput.class);
    private final MovieListInteractor interactor = new MovieListInteractor(
            app.getMovies(), app.addFavorite(), app.removeFavorite(), app.logout());

    {
        interactor.setOutput(output);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void loadMoviesReportsFilteredList() {
        interactor.loadMovies(MovieFilter.none().withQuery("matrix"));

        ArgumentCaptor<List<MovieItem>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(output).onMoviesLoaded(captor.capture());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    public void toggleWithoutLoginReportsNotLoggedIn() {
        MovieItem item = app.getMovie().execute(1);

        interactor.toggleFavorite(item);

        verify(output).onNotLoggedIn();
    }

    @Test
    public void toggleAfterLoginReportsToggled() {
        app.login().execute("user", "1234");
        MovieItem item = app.getMovie().execute(1);

        interactor.toggleFavorite(item);

        verify(output).onFavoriteToggled();
    }
}
