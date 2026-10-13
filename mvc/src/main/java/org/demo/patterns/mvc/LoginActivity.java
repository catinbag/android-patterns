package org.demo.patterns.mvc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginController controller;
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
        controller = new LoginController(this, app.getContainer().login());

        findViewById(R.id.loginButton).setOnClickListener(v ->
                controller.onLoginClicked(
                        loginInput.getText().toString(),
                        passwordInput.getText().toString()));
    }

    public void showError(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }

    public void showLoggedIn(String login) {
        startActivity(new Intent(this, MovieListActivity.class));
        finish();
    }
}
