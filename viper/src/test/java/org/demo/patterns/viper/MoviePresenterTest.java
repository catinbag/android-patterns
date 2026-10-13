package org.demo.patterns.viper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Test;

public class MoviePresenterTest {
    private final MovieContract.View view = mock(MovieContract.View.class);
    private final MovieContract.Interactor interactor = mock(MovieContract.Interactor.class);
    private final MoviePresenter presenter = new MoviePresenter(view, interactor);

    @Test
    public void startAsksInteractorToLoadMovie() {
        presenter.onStart();
        verify(interactor).loadMovie();
    }

    @Test
    public void loadedMovieIsShownWithFormattedInfo() {
        MovieItem item = new AppContainer().getMovie().execute(6);

        presenter.onMovieLoaded(item);

        verify(view).showTitle("The Matrix");
        verify(view).showInfo("1999 · Sci-Fi · 8.7");
    }
}
