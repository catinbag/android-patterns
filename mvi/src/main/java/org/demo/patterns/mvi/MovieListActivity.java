package org.demo.patterns.mvi;

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
import androidx.lifecycle.ViewModelProvider;

import org.demo.patterns.core.AppContainer;
import org.demo.patterns.core.model.MovieItem;

public class MovieListActivity extends AppCompatActivity {
    private MovieListViewModel viewModel;
    private LinearLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        listContainer = findViewById(R.id.listContainer);

        AppContainer app = ((MoviesApp) getApplication()).getContainer();
        viewModel = new ViewModelProvider(this, new MovieListViewModelFactory(
                app.getMovies(), app.addFavorite(), app.removeFavorite(), app.logout()))
                .get(MovieListViewModel.class);

        viewModel.getState().observe(this, this::render);
        viewModel.getMessage().observe(this, event -> {
            String text = event.getContentIfNotHandled();
            if (text != null) Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        });
        viewModel.getOpenMovie().observe(this, event -> {
            Long id = event.getContentIfNotHandled();
            if (id != null) openMovie(id);
        });
        viewModel.getOpenLogin().observe(this, event -> {
            if (event.getContentIfNotHandled() != null) openLogin();
        });

        EditText queryInput = findViewById(R.id.queryInput);
        queryInput.setOnEditorActionListener((v, actionId, event) -> {
            viewModel.process(MovieListIntent.queryChanged(v.getText().toString()));
            return true;
        });
        ((CheckBox) findViewById(R.id.favoritesOnly)).setOnCheckedChangeListener(
                (button, checked) -> viewModel.process(MovieListIntent.favoritesOnlyChanged(checked)));
        findViewById(R.id.logoutButton).setOnClickListener(
                v -> viewModel.process(MovieListIntent.logoutClicked()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.process(MovieListIntent.start());
    }

    private void render(MovieListState state) {
        listContainer.removeAllViews();
        for (MovieItem item : state.movies) {
            View row = LayoutInflater.from(this).inflate(R.layout.item_movie, listContainer, false);
            TextView title = row.findViewById(R.id.titleText);
            title.setText(item.getMovie().getTitle() + " (" + item.getMovie().getYear() + ")");
            title.setOnClickListener(v -> viewModel.process(MovieListIntent.movieClicked(item)));
            TextView star = row.findViewById(R.id.favoriteButton);
            star.setText(item.isFavorite() ? "★" : "☆");
            star.setOnClickListener(v -> viewModel.process(MovieListIntent.favoriteClicked(item)));
            listContainer.addView(row);
        }
    }

    private void openMovie(long movieId) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    private void openLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
