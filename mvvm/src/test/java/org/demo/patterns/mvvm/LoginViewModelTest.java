package org.demo.patterns.mvvm;

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
    public void wrongPasswordSetsError() {
        viewModel.onLoginClicked("user", "wrong");
        assertEquals("Invalid login or password", viewModel.getError().getValue());
    }

    @Test
    public void validCredentialsOpenListOnce() {
        viewModel.onLoginClicked("user", "1234");

        Event<Boolean> event = viewModel.getOpenList().getValue();
        assertNotNull(event.getContentIfNotHandled());
        assertNull(event.getContentIfNotHandled());
    }
}
