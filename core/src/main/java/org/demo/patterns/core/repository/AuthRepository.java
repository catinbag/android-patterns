package org.demo.patterns.core.repository;

import org.demo.patterns.core.model.User;

public interface AuthRepository {
    /** @throws org.demo.patterns.core.error.InvalidCredentialsException on wrong data */
    User login(String login, String password);

    void logout();

    /** Returns null when nobody is logged in. */
    User getCurrentUser();
}
