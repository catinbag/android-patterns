package org.demo.patterns.mvvm;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        TextView titleText = findViewById(R.id.titleText);
        TextView infoText = findViewById(R.id.infoText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        Button favoriteButton = findViewById(R.id.favoriteButton);

        long movieId = getIntent().getLongExtra(EXTRA_MOVIE_ID, -1);
        AppContainer app = ((MoviesApp) getApplication()).getContainer();
        viewModel = new ViewModelProvider(this, new MovieViewModelFactory(movieId,
                app.getMovie(), app.addFavorite(), app.removeFavorite()))
                .get(MovieViewModel.class);

        viewModel.getTitle().observe(this, titleText::setText);
        viewModel.getInfo().observe(this, infoText::setText);
        viewModel.getDescription().observe(this, descriptionText::setText);
        viewModel.getFavorite().observe(this, favorite ->
                favoriteButton.setText(favorite ? "Remove from favorites" : "Add to favorites"));
        viewModel.getMessage().observe(this, event -> {
            String text = event.getContentIfNotHandled();
            if (text != null) Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        });

        favoriteButton.setOnClickListener(v -> viewModel.onFavoriteClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }
}
