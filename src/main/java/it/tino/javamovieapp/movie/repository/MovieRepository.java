package it.tino.javamovieapp.movie.repository;

import it.tino.javamovieapp.movie.model.MovieSorting;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    MoviesCollection findAll(MovieSorting sorting, int page);

    default MoviesCollection findAll(MovieSorting sorting) {
        return findAll(sorting, 1);
    }

    default MoviesCollection findAll() {
        return findAll(MovieSorting.DEFAULT, 1);
    }
}
