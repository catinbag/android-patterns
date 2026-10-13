package org.demo.patterns.mvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieItem;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

public class MovieListControllerTest {
    private final MovieListActivity view = mock(MovieListActivity.class);
    private final AppContainer app = new AppContainer();
    private final MovieListController controller = new MovieListController(view,
            app.getMovies(), app.addFavorite(), app.removeFavorite(), app.logout());

    @Test
    public void queryFiltersList() {
        controller.onQueryChanged("matrix");

        ArgumentCaptor<List<MovieItem>> captor = ArgumentCaptor.forClass((Class) List.class);
        verify(view).showMovies(captor.capture());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    public void favoriteClickWithoutLoginShowsError() {
        MovieItem item = app.getMovies().execute(
                org.demo.patterns.core.model.MovieFilter.none()).get(0);

        controller.onFavoriteClicked(item);

        verify(view).showError("Please log in");
    }
}
