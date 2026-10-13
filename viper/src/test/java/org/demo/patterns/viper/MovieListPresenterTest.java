package org.demo.patterns.viper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class MovieListPresenterTest {
    private final MovieListContract.View view = mock(MovieListContract.View.class);
    private final MovieListContract.Interactor interactor =
            mock(MovieListContract.Interactor.class);
    private final MovieListContract.Router router = mock(MovieListContract.Router.class);
    private final MovieListPresenter presenter =
            new MovieListPresenter(view, interactor, router);

    @Test
    public void queryChangedAsksInteractorForFilteredMovies() {
        presenter.onQueryChanged("matrix");

        ArgumentCaptor<MovieFilter> captor = ArgumentCaptor.forClass(MovieFilter.class);
        verify(interactor).loadMovies(captor.capture());
        assertEquals("matrix", captor.getValue().getQuery());
    }

    @Test
    public void movieClickOpensMovieThroughRouter() {
        MovieItem item = new AppContainer().getMovie().execute(6);

        presenter.onMovieClicked(item);

        verify(router).openMovie(6);
    }

    @Test
    public void logoutLogsOutAndOpensLogin() {
        presenter.onLogoutClicked();

        verify(interactor).logout();
        verify(router).openLogin();
    }

    @Test
    public void notLoggedInShowsError() {
        presenter.onNotLoggedIn();
        verify(view).showError("Please log in");
    }
}
