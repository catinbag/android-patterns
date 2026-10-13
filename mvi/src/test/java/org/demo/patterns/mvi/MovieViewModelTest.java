package org.demo.patterns.mvi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void startFillsWholeState() {
        viewModel.process(MovieIntent.start());

        MovieState state = viewModel.getState().getValue();
        assertEquals("The Matrix", state.title);
        assertEquals("1999 · Sci-Fi · 8.7", state.info);
    }

    @Test
    public void favoriteClickAfterLoginMarksMovie() {
        app.login().execute("user", "1234");

        viewModel.process(MovieIntent.favoriteClicked());

        assertTrue(viewModel.getState().getValue().favorite);
    }

    @Test
    public void favoriteClickWithoutLoginSendsMessage() {
        viewModel.process(MovieIntent.favoriteClicked());

        assertEquals("Please log in",
                viewModel.getMessage().getValue().getContentIfNotHandled());
    }
}
