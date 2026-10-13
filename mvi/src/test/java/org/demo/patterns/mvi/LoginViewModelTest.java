package org.demo.patterns.mvi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.demo.patterns.core.AppContainer;
import org.junit.Rule;
import org.junit.Test;

public class LoginViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final AppContainer app = new AppContainer();
    private final LoginViewModel viewModel = new LoginViewModel(app.login());

    @Test
    public void initialStateHasNoError() {
        assertNull(viewModel.getState().getValue().error);
    }

    @Test
    public void wrongPasswordGivesStateWithError() {
        viewModel.process(LoginIntent.loginClicked("user", "wrong"));
        assertEquals("Invalid login or password", viewModel.getState().getValue().error);
    }

    @Test
    public void validCredentialsOpenListOnce() {
        viewModel.process(LoginIntent.loginClicked("user", "1234"));

        Event<Boolean> event = viewModel.getOpenList().getValue();
        assertNotNull(event.getContentIfNotHandled());
        assertNull(event.getContentIfNotHandled());
    }
}
