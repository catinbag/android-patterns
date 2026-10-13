package org.demo.patterns.mvp;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginPresenter {
    private final LoginView view;
    private final LoginUseCase loginUseCase;

    public LoginPresenter(LoginView view, LoginUseCase loginUseCase) {
        this.view = view;
        this.loginUseCase = loginUseCase;
    }

    public void onLoginClicked(String login, String password) {
        try {
            loginUseCase.execute(login, password);
            view.openList();
        } catch (InvalidCredentialsException e) {
            view.showError("Invalid login or password");
        }
    }
}
