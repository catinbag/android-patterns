package org.demo.patterns.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> openList = new MutableLiveData<>();

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Event<Boolean>> getOpenList() {
        return openList;
    }

    public void onLoginClicked(String login, String password) {
        try {
            loginUseCase.execute(login, password);
            error.setValue(null);
            openList.setValue(new Event<>(true));
        } catch (InvalidCredentialsException e) {
            error.setValue("Invalid login or password");
        }
    }
}
