package org.demo.patterns.core.data;

import org.demo.patterns.core.error.InvalidCredentialsException;
import org.demo.patterns.core.model.User;
import org.demo.patterns.core.repository.AuthRepository;

import java.util.HashMap;
import java.util.Map;

/** Test users: user / 1234 and admin / 1234. */
public class InMemoryAuthRepository implements AuthRepository {
    private final Map<String, String> passwords = new HashMap<>();
    private User current;

    public InMemoryAuthRepository() {
        passwords.put("user", "1234");
        passwords.put("admin", "1234");
    }

    @Override
    public User login(String login, String password) {
        if (login == null || password == null || !password.equals(passwords.get(login))) {
            throw new InvalidCredentialsException();
        }
        current = new User(login);
        return current;
    }

    @Override
    public void logout() {
        current = null;
    }

    @Override
    public User getCurrentUser() {
        return current;
    }
}
