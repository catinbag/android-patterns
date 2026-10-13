package org.demo.patterns.mvi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Rule;
import org.junit.Test;

public class MovieListViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final AppContainer app = new AppContainer();
    private final MovieListViewModel viewModel = new MovieListViewModel(
            app.getMovies(), app.addFavorite(), app.removeFavorite(), app.logout());

    @Test
    public void startLoadsAllMovies() {
        viewModel.process(MovieListIntent.start());
        assertEquals(8, viewModel.getState().getValue().movies.size());
    }

    @Test
    public void queryChangedUpdatesFilterAndList() {
        viewModel.process(MovieListIntent.queryChanged("matrix"));

        MovieListState state = viewModel.getState().getValue();
        assertEquals("matrix", state.filter.getQuery());
        assertEquals(1, state.movies.size());
    }

    @Test
    public void everyIntentCreatesNewState() {
        MovieListState before = viewModel.getState().getValue();

        viewModel.process(MovieListIntent.start());

        assertNotSame(before, viewModel.getState().getValue());
    }

    @Test
    public void filterIsKeptWhenScreenStartsAgain() {
        viewModel.process(MovieListIntent.queryChanged("matrix"));

        viewModel.process(MovieListIntent.start());

        assertEquals(1, viewModel.getState().getValue().movies.size());
    }

    @Test
    public void movieClickOpensMovieOnce() {
        viewModel.process(MovieListIntent.start());
        MovieItem item = viewModel.getState().getValue().movies.get(0);

        viewModel.process(MovieListIntent.movieClicked(item));

        Event<Long> event = viewModel.getOpenMovie().getValue();
        assertEquals(Long.valueOf(item.getMovie().getId()), event.getContentIfNotHandled());
        assertNull(event.getContentIfNotHandled());
    }

    @Test
    public void favoriteClickWithoutLoginSendsMessage() {
        viewModel.process(MovieListIntent.start());
        MovieItem item = viewModel.getState().getValue().movies.get(0);

        viewModel.process(MovieListIntent.favoriteClicked(item));

        assertEquals("Please log in",
                viewModel.getMessage().getValue().getContentIfNotHandled());
    }

    @Test
    public void favoriteClickAfterLoginMarksMovie() {
        app.login().execute("user", "1234");
        viewModel.process(MovieListIntent.start());
        MovieItem item = viewModel.getState().getValue().movies.get(0);

        viewModel.process(MovieListIntent.favoriteClicked(item));

        assertTrue(viewModel.getState().getValue().movies.get(0).isFavorite());
    }
}
