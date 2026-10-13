package org.demo.patterns.viper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class LoginPresenterTest {
    private final LoginContract.View view = mock(LoginContract.View.class);
    private final LoginContract.Interactor interactor = mock(LoginContract.Interactor.class);
    private final LoginContract.Router router = mock(LoginContract.Router.class);
    private final LoginPresenter presenter = new LoginPresenter(view, interactor, router);

    @Test
    public void loginClickAsksInteractor() {
        presenter.onLoginClicked("user", "1234");
        verify(interactor).login("user", "1234");
    }

    @Test
    public void successOpensListThroughRouter() {
        presenter.onLoginSucceeded();
        verify(router).openList();
    }

    @Test
    public void failureShowsError() {
        presenter.onLoginFailed();
        verify(view).showError("Invalid login or password");
    }
}
