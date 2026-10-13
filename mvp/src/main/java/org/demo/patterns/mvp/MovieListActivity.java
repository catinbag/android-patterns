package org.demo.patterns.mvp;

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

public class MovieListActivity extends AppCompatActivity implements MovieListView {
    private MovieListPresenter presenter;
    private LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        listContainer = findViewById(R.id.listContainer);

        AppContainer app = ((MoviesApp) getApplication()).getContainer();
        presenter = new MovieListPresenter(this, app.getMovies(),
                app.addFavorite(), app.removeFavorite(), app.logout());

        EditText queryInput = findViewById(R.id.queryInput);
        queryInput.setOnEditorActionListener((v, actionId, event) -> {
            presenter.onQueryChanged(v.getText().toString());
            return true;
        });
        ((CheckBox) findViewById(R.id.favoritesOnly)).setOnCheckedChangeListener(
                (button, checked) -> presenter.onFavoritesOnlyChanged(checked));
        findViewById(R.id.logoutButton).setOnClickListener(v -> presenter.onLogoutClicked());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void showMovies(List<MovieItem> items) {
        listContainer.removeAllViews();
        for (MovieItem item : items) {
            View row = LayoutInflater.from(this).inflate(R.layout.item_movie, listContainer, false);
            TextView title = row.findViewById(R.id.titleText);
            title.setText(item.getMovie().getTitle() + " (" + item.getMovie().getYear() + ")");
            title.setOnClickListener(v -> presenter.onMovieClicked(item));
            TextView star = row.findViewById(R.id.favoriteButton);
            star.setText(item.isFavorite() ? "★" : "☆");
            star.setOnClickListener(v -> presenter.onFavoriteClicked(item));
            listContainer.addView(row);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openMovie(long movieId) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void openLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
