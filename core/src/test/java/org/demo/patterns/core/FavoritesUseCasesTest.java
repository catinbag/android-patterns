package org.demo.patterns.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.error.NotLoggedInException;

import org.junit.Test;

public class FavoritesUseCasesTest {
    private final AppContainer app = new AppContainer();

    @Test(expected = NotLoggedInException.class)
    public void addRequiresLogin() {
        app.addFavorite().execute(1);
    }

    @Test(expected = NotLoggedInException.class)
    public void removeRequiresLogin() {
        app.removeFavorite().execute(1);
    }

    @Test(expected = MovieNotFoundException.class)
    public void addUnknownMovieFails() {
        app.login().execute("user", "1234");
        app.addFavorite().execute(999);
    }

    @Test
    public void addThenRemove() {
        app.login().execute("user", "1234");
        app.addFavorite().execute(2);
        assertTrue(app.getMovie().execute(2).isFavorite());
        app.removeFavorite().execute(2);
        assertFalse(app.getMovie().execute(2).isFavorite());
    }
}
