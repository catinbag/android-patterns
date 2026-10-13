package org.demo.patterns.core.model;

public final class User {
    private final String login;

    public User(String login) {
        this.login = login;
    }

    public String getLogin() { return login; }

    @Override
    public boolean equals(Object o) {
        return o instanceof User && ((User) o).login.equals(login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
