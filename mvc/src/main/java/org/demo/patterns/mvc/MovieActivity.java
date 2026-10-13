package org.demo.patterns.mvc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.model.MovieItem;

public class MovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    private MovieController controller;
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
        controller = new MovieController(this, movieId, app.getMovie(),
                app.addFavorite(), app.removeFavorite());
        favoriteButton.setOnClickListener(v -> controller.onFavoriteClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.onStart();
    }

    public void showMovie(MovieItem item) {
        Movie movie = item.getMovie();
        titleText.setText(movie.getTitle());
        infoText.setText(movie.getYear() + " · " + movie.getGenre().getTitle()
                + " · " + movie.getRating());
        descriptionText.setText(movie.getDescription());
        favoriteButton.setText(item.isFavorite() ? "Remove from favorites" : "Add to favorites");
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
