package org.demo.patterns.mvvm;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.demo.patterns.core.AppContainer;
import org.junit.Rule;
import org.junit.Test;

public class MovieViewModelTest {
    private static final long MATRIX_ID = 6;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final AppContainer app = new AppContainer();
    private final MovieViewModel viewModel = new MovieViewModel(MATRIX_ID,
            app.getMovie(), app.addFavorite(), app.removeFavorite());

    @Test
    public void startSetsFormattedInfo() {
        viewModel.onStart();

        assertEquals("The Matrix", viewModel.getTitle().getValue());
        assertEquals("1999 · Sci-Fi · 8.7", viewModel.getInfo().getValue());
    }

    @Test
    public void favoriteClickAfterLoginMarksMovie() {
        app.login().execute("user", "1234");

        viewModel.onFavoriteClicked();

        assertEquals(Boolean.TRUE, viewModel.getFavorite().getValue());
    }
}
