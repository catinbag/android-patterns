package org.demo.patterns.mvvm;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieFilter;
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
    public void queryFiltersList() {
        viewModel.onQueryChanged("matrix");
        assertEquals(1, viewModel.getMovies().getValue().size());
    }

    @Test
    public void filterIsKeptWhenScreenStartsAgain() {
        viewModel.onQueryChanged("matrix");

        viewModel.onStart();

        assertEquals(1, viewModel.getMovies().getValue().size());
    }

    @Test
    public void favoriteClickWithoutLoginSendsMessage() {
        MovieItem item = app.getMovies().execute(MovieFilter.none()).get(0);

        viewModel.onFavoriteClicked(item);

        assertEquals("Please log in",
                viewModel.getMessage().getValue().getContentIfNotHandled());
    }
}
