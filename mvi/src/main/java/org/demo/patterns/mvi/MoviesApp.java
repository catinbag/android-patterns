package org.demo.patterns.mvi;

import android.app.Application;

import org.demo.patterns.core.AppContainer;

public class MoviesApp extends Application {
    private final AppContainer container = new AppContainer();

    public AppContainer getContainer() {
        return container;
    }
}
