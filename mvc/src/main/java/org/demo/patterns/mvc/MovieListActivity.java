package org.demo.patterns.mvc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieItem;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    private MovieListController controller;
    private LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        listContainer = findViewById(R.id.listContainer);

        AppContainer app = ((MoviesApp) getApplication()).getContainer();
        controller = new MovieListController(this, app.getMovies(),
                app.addFavorite(), app.removeFavorite(), app.logout());

        EditText queryInput = findViewById(R.id.queryInput);
        queryInput.setOnEditorActionListener((v, actionId, event) -> {
            controller.onQueryChanged(v.getText().toString());
            return true;
        });
        ((CheckBox) findViewById(R.id.favoritesOnly)).setOnCheckedChangeListener(
                (button, checked) -> controller.onFavoritesOnlyChanged(checked));
        findViewById(R.id.logoutButton).setOnClickListener(v -> controller.onLogoutClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.onStart();
    }

    public void showMovies(List<MovieItem> items) {
        listContainer.removeAllViews();
        for (MovieItem item : items) {
            View row = LayoutInflater.from(this).inflate(R.layout.item_movie, listContainer, false);
            TextView title = row.findViewById(R.id.titleText);
            title.setText(item.getMovie().getTitle() + " (" + item.getMovie().getYear() + ")");
            title.setOnClickListener(v -> controller.onMovieClicked(item));
            TextView star = row.findViewById(R.id.favoriteButton);
            star.setText(item.isFavorite() ? "★" : "☆");
            star.setOnClickListener(v -> controller.onFavoriteClicked(item));
            listContainer.addView(row);
        }
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void openMovie(long movieId) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    public void openLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
