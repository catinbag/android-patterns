package org.demo.patterns.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.demo.patterns.core.error.InvalidCredentialsException;

import org.junit.Test;

public class LoginUseCaseTest {
    private final AppContainer app = new AppContainer();

    @Test
    public void validCredentialsSetCurrentUser() {
        app.login().execute("user", "1234");
        assertEquals("user", app.getCurrentUser().execute().getLogin());
    }

    @Test(expected = InvalidCredentialsException.class)
    public void wrongPasswordFails() {
        app.login().execute("user", "wrong");
    }

    @Test(expected = InvalidCredentialsException.class)
    public void emptyLoginFails() {
        app.login().execute("", "1234");
    }

    @Test
    public void logoutClearsCurrentUser() {
        app.login().execute("user", "1234");
        app.logout().execute();
        assertNull(app.getCurrentUser().execute());
    }
}
