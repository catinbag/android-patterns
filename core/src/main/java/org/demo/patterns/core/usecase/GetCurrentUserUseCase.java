package org.demo.patterns.core.usecase;

import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;

public class GetCurrentUserUseCase {
    private final AuthRepository auth;

    public GetCurrentUserUseCase(AuthRepository auth) {
        this.auth = auth;
    }

    /** Returns null when nobody is logged in. */
    public User execute() {
        return auth.getCurrentUser();
    }
}
