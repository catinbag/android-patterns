package org.demo.patterns.core.usecase;

import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;

public class LoginUseCase {
    private final AuthRepository auth;

    public LoginUseCase(AuthRepository auth) {
        this.auth = auth;
    }

    public User execute(String login, String password) {
        return auth.login(login, password);
    }
}
