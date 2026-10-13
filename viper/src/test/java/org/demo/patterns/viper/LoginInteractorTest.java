package org.demo.patterns.viper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.demo.patterns.core.AppContainer;
import org.junit.Test;

public class LoginInteractorTest {
    private final LoginContract.InteractorOutput output =
            mock(LoginContract.InteractorOutput.class);
    private final LoginInteractor interactor =
            new LoginInteractor(new AppContainer().login());

    {
        interactor.setOutput(output);
    }

    @Test
    public void validCredentialsReportSuccess() {
        interactor.login("user", "1234");
        verify(output).onLoginSucceeded();
    }

    @Test
    public void wrongPasswordReportsFailure() {
        interactor.login("user", "wrong");

        verify(output).onLoginFailed();
        verify(output, never()).onLoginSucceeded();
    }
}
