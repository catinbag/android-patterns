package org.demo.patterns.mvp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.junit.Test;

public class LoginPresenterTest {
    private final LoginView view = mock(LoginView.class);
    private final AppContainer app = new AppContainer();
    private final LoginPresenter presenter = new LoginPresenter(view, app.login());

    @Test
    public void wrongPasswordShowsError() {
        presenter.onLoginClicked("user", "wrong");
        verify(view).showError("Invalid login or password");
    }

    @Test
    public void validCredentialsOpenList() {
        presenter.onLoginClicked("user", "1234");
        verify(view).openList();
    }
}
