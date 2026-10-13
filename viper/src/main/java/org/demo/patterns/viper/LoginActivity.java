package org.demo.patterns.viper;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private LoginContract.Presenter presenter;
    private EditText loginInput;
    private EditText passwordInput;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInput = findViewById(R.id.loginInput);
        passwordInput = findViewById(R.id.passwordInput);
        errorText = findViewById(R.id.errorText);

        MoviesApp app = (MoviesApp) getApplication();
        LoginInteractor interactor = new LoginInteractor(app.getContainer().login());
        LoginPresenter loginPresenter =
                new LoginPresenter(this, interactor, new LoginRouter(this));
        interactor.setOutput(loginPresenter);
        presenter = loginPresenter;

        findViewById(R.id.loginButton).setOnClickListener(v ->
                presenter.onLoginClicked(
                        loginInput.getText().toString(),
                        passwordInput.getText().toString()));
    }

    @Override
    public void showError(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }
}
