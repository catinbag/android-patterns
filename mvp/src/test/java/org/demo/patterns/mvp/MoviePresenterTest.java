package org.demo.patterns.mvp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.junit.Test;

public class MoviePresenterTest {
    private static final long MATRIX_ID = 6;

    private final MovieView view = mock(MovieView.class);
    private final AppContainer app = new AppContainer();
    private final MoviePresenter presenter = new MoviePresenter(view, MATRIX_ID,
            app.getMovie(), app.addFavorite(), app.removeFavorite());

    @Test
    public void startShowsFormattedInfo() {
        presenter.onStart();

        verify(view).showTitle("The Matrix");
        verify(view).showInfo("1999 · Sci-Fi · 8.7");
    }

    @Test
    public void favoriteClickAfterLoginMarksMovie() {
        app.login().execute("user", "1234");

        presenter.onFavoriteClicked();

        verify(view).showFavorite(true);
    }
}
