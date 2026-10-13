package org.demo.patterns.mvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.junit.Test;

public class LoginControllerTest {
    private final LoginActivity view = mock(LoginActivity.class);
    private final AppContainer app = new AppContainer();
    private final LoginController controller = new LoginController(view, app.login());

    @Test
    public void wrongPasswordShowsError() {
        controller.onLoginClicked("user", "wrong");
        verify(view).showError("Invalid login or password");
    }

    @Test
    public void validCredentialsOpenList() {
        controller.onLoginClicked("user", "1234");
        verify(view).showLoggedIn("user");
    }
}
