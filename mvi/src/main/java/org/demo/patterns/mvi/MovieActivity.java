package org.demo.patterns.mvi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.demo.patterns.core.AppContainer;

public class MovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    private MovieViewModel viewModel;
    private TextView titleText;
    private TextView infoText;
    private TextView descriptionText;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        titleText = findViewById(R.id.titleText);
        infoText = findViewById(R.id.infoText);
        descriptionText = findViewById(R.id.descriptionText);
        favoriteButton = findViewById(R.id.favoriteButton);

        long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, -1);
        AppContainer app = ((MoviesApp) getApplication()).getContainer();
        viewModel = new ViewModelProvider(this, new MovieViewModelFactory(movieId,
                app.getMovie(), app.addFavorite(), app.removeFavorite()))
                .get(MovieViewModel.class);

        viewModel.getState().observe(this, this::render);
        viewModel.getMessage().observe(this, event -> {
            String text = event.getContentIfNotHandled();
            if (text != null) Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        });

        favoriteButton.setOnClickListener(
                v -> viewModel.process(MovieIntent.favoriteClicked()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.process(MovieIntent.start());
    }

    private void render(MovieState state) {
        titleText.setText(state.title);
        infoText.setText(state.info);
        descriptionText.setText(state.description);
        favoriteButton.setText(state.favorite ? "Remove from favorites" : "Add to favorites");
    }
}
