package org.demo.patterns.core.repository;

import org.demo.patterns.core.model.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> getAll();

    /** @throws org.demo.patterns.core.error.MovieNotFoundException when the id is unknown */
    Movie getById(long id);
}
