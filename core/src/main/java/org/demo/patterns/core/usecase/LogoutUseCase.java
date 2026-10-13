package org.demo.patterns.core.usecase;

import org.demo.patterns.core.repository.AuthRepository;

public class LogoutUseCase {
    private final AuthRepository auth;

    public LogoutUseCase(AuthRepository auth) {
        this.auth = auth;
    }

    public void execute() {
        auth.logout();
    }
}
