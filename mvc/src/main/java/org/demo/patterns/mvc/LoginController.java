package org.demo.patterns.mvc;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginController {
    private final LoginActivity view;
    private final LoginUseCase loginUseCase;

    public LoginController(LoginActivity view, LoginUseCase loginUseCase) {
        this.view = view;
        this.loginUseCase = loginUseCase;
    }

    public void onLoginClicked(String login, String password) {
        try {
            User user = loginUseCase.execute(login, password);
            view.showLoggedIn(user.getLogin());
        } catch (InvalidCredentialsException e) {
            view.showError("Invalid login or password");
        }
    }
}
