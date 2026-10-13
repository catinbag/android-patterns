package org.demo.patterns.viper;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginInteractor implements LoginContract.Interactor {
    private final LoginUseCase loginUseCase;
    private LoginContract.InteractorOutput output;

    public LoginInteractor(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void setOutput(LoginContract.InteractorOutput output) {
        this.output = output;
    }

    @Override
    public void login(String login, String password) {
        try {
            loginUseCase.execute(login, password);
            output.onLoginSucceeded();
        } catch (InvalidCredentialsException e) {
            output.onLoginFailed();
        }
    }
}
