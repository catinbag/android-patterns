package org.demo.patterns.viper;

import android.app.Activity;
import android.content.Intent;

public class MovieListRouter implements MovieListContract.Router {
    private final Activity activity;

    public MovieListRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openMovie(long movieId) {
        Intent intent = new Intent(activity, MovieActivity.class);
        intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movieId);
        activity.startActivity(intent);
    }

    @Override
    public void openLogin() {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }
}
