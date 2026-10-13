package org.demo.patterns.viper;

public class LoginPresenter
        implements LoginContract.Presenter, LoginContract.InteractorOutput {
    private final LoginContract.View view;
    private final LoginContract.Interactor interactor;
    private final LoginContract.Router router;

    public LoginPresenter(LoginContract.View view, LoginContract.Interactor interactor,
                          LoginContract.Router router) {
        this.view = view;
        this.interactor = interactor;
        this.router = router;
    }

    @Override
    public void onLoginClicked(String login, String password) {
        interactor.login(login, password);
    }

    @Override
    public void onLoginSucceeded() {
        router.openList();
    }

    @Override
    public void onLoginFailed() {
        view.showError("Invalid login or password");
    }
}
