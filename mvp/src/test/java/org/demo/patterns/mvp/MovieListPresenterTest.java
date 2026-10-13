package org.demo.patterns.mvp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

public class MovieListPresenterTest {
    private final MovieListView view = mock(MovieListView.class);
    private final AppContainer app = new AppContainer();
    private final MovieListPresenter presenter = new MovieListPresenter(view,
            app.getMovies(), app.addFavorite(), app.removeFavorite(), app.logout());

    @Test
    public void queryFiltersList() {
        presenter.onQueryChanged("matrix");

        ArgumentCaptor<List<MovieItem>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(view).showMovies(captor.capture());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    public void favoriteClickWithoutLoginShowsError() {
        MovieItem item = app.getMovies().execute(MovieFilter.none()).get(0);

        presenter.onFavoriteClicked(item);

        verify(view).showError("Please log in");
    }
}
