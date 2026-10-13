package org.demo.patterns.viper;

public interface LoginContract {

    interface View {
        void showError(String message);
    }

    interface Presenter {
        void onLoginClicked(String login, String password);
    }

    interface Interactor {
        void login(String login, String password);
    }

    interface InteractorOutput {
        void onLoginSucceeded();

        void onLoginFailed();
    }

    interface Router {
        void openList();
    }
}
