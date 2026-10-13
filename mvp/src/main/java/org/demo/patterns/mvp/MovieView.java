package org.demo.patterns.mvp;

public interface MovieView {
    void showTitle(String title);

    void showInfo(String info);

    void showDescription(String description);

    void showFavorite(boolean favorite);

    void showError(String message);
}
