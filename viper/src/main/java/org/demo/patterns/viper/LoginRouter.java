package org.demo.patterns.viper;

import android.app.Activity;
import android.content.Intent;

public class LoginRouter implements LoginContract.Router {
    private final Activity activity;

    public LoginRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void openList() {
        activity.startActivity(new Intent(activity, MovieListActivity.class));
        activity.finish();
    }
}
