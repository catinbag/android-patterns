package org.demo.patterns.mvvm;

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

        viewModel.getError().observe(this, message -> {
            errorText.setText(message);
            errorText.setVisibility(message == null ? View.GONE : View.VISIBLE);
        });
        viewModel.getOpenList().observe(this, event -> {
            if (event.getContentIfNotHandled() != null) openList();
        });

        findViewById(R.id.loginButton).setOnClickListener(v ->
                viewModel.onLoginClicked(
                        loginInput.getText().toString(),
                        passwordInput.getText().toString()));
    }

    private void openList() {
        startActivity(new Intent(this, MovieListActivity.class));
        finish();
    }
}
