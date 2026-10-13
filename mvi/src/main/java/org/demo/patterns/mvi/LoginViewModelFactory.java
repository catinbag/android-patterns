package org.demo.patterns.mvi;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final LoginUseCase loginUseCase;

    public LoginViewModelFactory(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> type) {
        return (T) new LoginViewModel(loginUseCase);
    }
}
