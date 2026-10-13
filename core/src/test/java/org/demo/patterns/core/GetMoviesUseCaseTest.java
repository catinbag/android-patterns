package org.demo.patterns.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.model.Genre;
import org.demo.patterns.core.model.MovieFilter;
import org.demo.patterns.core.model.MovieItem;

import org.junit.Test;

import java.util.List;

public class GetMoviesUseCaseTest {
    private final AppContainer app = new AppContainer();

    @Test
    public void noFilterReturnsAllMovies() {
        assertEquals(8, app.getMovies().execute(MovieFilter.none()).size());
    }

    @Test
    public void genreFilterKeepsOnlyThatGenre() {
        List<MovieItem> items = app.getMovies().execute(MovieFilter.none().withGenre(Genre.CRIME));
        assertEquals(2, items.size());
        for (MovieItem item : items) assertEquals(Genre.CRIME, item.getMovie().getGenre());
    }

    @Test
    public void queryIgnoresCaseAndSpaces() {
        List<MovieItem> items = app.getMovies().execute(MovieFilter.none().withQuery("  MATRIX "));
        assertEquals(1, items.size());
        assertEquals("The Matrix", items.get(0).getMovie().getTitle());
    }

    @Test
    public void favoritesOnlyIsEmptyWhenLoggedOut() {
        assertTrue(app.getMovies().execute(MovieFilter.none().withFavoritesOnly(true)).isEmpty());
    }

    @Test
    public void favoritesOnlyReturnsMarkedMovies() {
        app.login().execute("user", "1234");
        app.addFavorite().execute(4);
        List<MovieItem> items = app.getMovies().execute(MovieFilter.none().withFavoritesOnly(true));
        assertEquals(1, items.size());
        assertTrue(items.get(0).isFavorite());
    }

    @Test
    public void favoritesBelongToUser() {
        app.login().execute("user", "1234");
        app.addFavorite().execute(4);
        app.logout().execute();
        app.login().execute("admin", "1234");
        assertFalse(app.getMovie().execute(4).isFavorite());
    }

    @Test(expected = MovieNotFoundException.class)
    public void unknownMovieFails() {
        app.getMovie().execute(999);
    }
}
