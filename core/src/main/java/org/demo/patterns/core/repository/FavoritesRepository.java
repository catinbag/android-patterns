package org.demo.patterns.core.repository;

import java.util.Set;

public interface FavoritesRepository {
    Set<Long> getIds(String login);

    void add(String login, long movieId);

    void remove(String login, long movieId);
}
