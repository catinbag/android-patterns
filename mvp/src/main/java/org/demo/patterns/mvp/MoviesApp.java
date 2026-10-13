package org.demo.patterns.mvp;

import android.app.Application;

import org.demo.patterns.core.AppContainer;

public class MoviesApp extends Application {
    private final AppContainer container = new AppContainer();

    public AppContainer getContainer() {
        return container;
    }
}
