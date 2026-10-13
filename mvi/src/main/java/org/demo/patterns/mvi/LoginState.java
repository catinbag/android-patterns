package org.demo.patterns.mvi;

/** Everything the login screen shows. The object is immutable. */
public final class LoginState {
    public final String error;   // null: no error

    private LoginState(String error) {
        this.error = error;
    }

    public static LoginState initial() {
        return new LoginState(null);
    }

    public LoginState withError(String error) {
        return new LoginState(error);
    }
}
