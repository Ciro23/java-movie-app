package it.tino.javamovieapp.movie.repository;

import it.tino.javamovieapp.movie.model.MoviesCollection;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository {

    MoviesCollection findAll();
}
