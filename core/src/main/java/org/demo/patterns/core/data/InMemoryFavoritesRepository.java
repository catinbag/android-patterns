package org.demo.patterns.core.data;

import org.demo.patterns.core.repository.FavoritesRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryFavoritesRepository implements FavoritesRepository {
    private final Map<String, Set<Long>> byUser = new HashMap<>();

    @Override
    public Set<Long> getIds(String login) {
        Set<Long> ids = byUser.get(login);
        return ids == null ? Collections.<Long>emptySet() : new HashSet<>(ids);
    }

    @Override
    public void add(String login, long movieId) {
        Set<Long> ids = byUser.get(login);
        if (ids == null) {
            ids = new HashSet<>();
            byUser.put(login, ids);
        }
        ids.add(movieId);
    }

    @Override
    public void remove(String login, long movieId) {
        Set<Long> ids = byUser.get(login);
        if (ids != null) ids.remove(movieId);
    }
}
