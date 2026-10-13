package org.demo.patterns.mvi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.usecase.LoginUseCase;

public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    private final MutableLiveData<LoginState> state =
            new MutableLiveData<>(LoginState.initial());
    private final MutableLiveData<Event<Boolean>> openList = new MutableLiveData<>();

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public LiveData<LoginState> getState() {
        return state;
    }

    public LiveData<Event<Boolean>> getOpenList() {
        return openList;
    }

    public void process(LoginIntent intent) {
        switch (intent.type) {
            case LOGIN_CLICKED:
                login(intent.login, intent.password);
                break;
        }
    }

    private void login(String login, String password) {
        try {
            loginUseCase.execute(login, password);
            state.setValue(LoginState.initial());
            openList.setValue(new Event<>(true));
        } catch (InvalidCredentialsException e) {
            state.setValue(LoginState.initial().withError("Invalid login or password"));
        }
    }
}
