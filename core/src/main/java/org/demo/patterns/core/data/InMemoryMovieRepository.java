package org.demo.patterns.core.data;

import org.demo.patterns.core.error.MovieNotFoundException;
import org.demo.patterns.core.model.Genre;
import org.demo.patterns.core.model.Movie;
import org.demo.patterns.core.repository.MovieRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InMemoryMovieRepository implements MovieRepository {
    private final List<Movie> movies = Collections.unmodifiableList(Arrays.asList(
            new Movie(1, "The Shawshank Redemption", 1994, Genre.DRAMA, 9.3,
                    "Banker Andy Dufresne gets a life sentence for a crime he did not commit."),
            new Movie(2, "The Godfather", 1972, Genre.CRIME, 9.2,
                    "The aging head of a mafia family passes control to his reluctant son."),
            new Movie(3, "Pulp Fiction", 1994, Genre.CRIME, 8.9,
                    "Several stories of Los Angeles criminals cross each other."),
            new Movie(4, "Inception", 2010, Genre.SCI_FI, 8.8,
                    "A thief enters people's dreams to steal and to plant ideas."),
            new Movie(5, "Forrest Gump", 1994, Genre.DRAMA, 8.8,
                    "A kind man with a low IQ lives through key events of American history."),
            new Movie(6, "The Matrix", 1999, Genre.SCI_FI, 8.7,
                    "A hacker learns that the world around him is a simulation."),
            new Movie(7, "Spirited Away", 2001, Genre.ANIMATION, 8.6,
                    "A girl works in a bathhouse for spirits to free her parents."),
            new Movie(8, "The Grand Budapest Hotel", 2014, Genre.COMEDY, 8.1,
                    "A hotel concierge and his lobby boy get involved in a theft and a murder.")
    ));

    @Override
    public List<Movie> getAll() {
        return movies;
    }

    @Override
    public Movie getById(long id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) return movie;
        }
        throw new MovieNotFoundException(id);
    }
}
