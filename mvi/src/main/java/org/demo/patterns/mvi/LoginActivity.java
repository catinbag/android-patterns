package org.demo.patterns.mvi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
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
        viewModel = new ViewModelProvider(this,
                new LoginViewModelFactory(app.getContainer().login()))
                .get(LoginViewModel.class);

        viewModel.getState().observe(this, this::render);
        viewModel.getOpenList().observe(this, event -> {
            if (event.getContentIfNotHandled() != null) openList();
        });

        findViewById(R.id.loginButton).setOnClickListener(v ->
                viewModel.process(LoginIntent.loginClicked(
                        loginInput.getText().toString(),
                        passwordInput.getText().toString())));
    }

    private void render(LoginState state) {
        errorText.setText(state.error);
        errorText.setVisibility(state.error == null ? View.GONE : View.VISIBLE);
    }

    private void openList() {
        startActivity(new Intent(this, MovieListActivity.class));
        finish();
    }
}
