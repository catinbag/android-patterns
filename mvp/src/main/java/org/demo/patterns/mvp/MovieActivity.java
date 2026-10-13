package org.demo.patterns.mvp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.demo.patterns.core.AppContainer;

public class MovieActivity extends AppCompatActivity implements MovieView {
    public static final String EXTRA_MOVIE_ID = "movie_id";

    private MoviePresenter presenter;
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
        presenter = new MoviePresenter(this, movieId, app.getMovie(),
                app.addFavorite(), app.removeFavorite());
        favoriteButton.setOnClickListener(v -> presenter.onFavoriteClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void showTitle(String title) {
        titleText.setText(title);
    }

    @Override
    public void showInfo(String info) {
        infoText.setText(info);
    }

    @Override
    public void showDescription(String description) {
        descriptionText.setText(description);
    }

    @Override
    public void showFavorite(boolean favorite) {
        favoriteButton.setText(favorite ? "Remove from favorites" : "Add to favorites");
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
