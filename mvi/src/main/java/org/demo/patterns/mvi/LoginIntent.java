package org.demo.patterns.mvi;

public final class LoginIntent {
    public enum Type {
        LOGIN_CLICKED
    }

    public final Type type;
    public final String login;      // for LOGIN_CLICKED
    public final String password;   // for LOGIN_CLICKED

    private LoginIntent(Type type, String login, String password) {
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public static LoginIntent loginClicked(String login, String password) {
        return new LoginIntent(Type.LOGIN_CLICKED, login, password);
    }
}
