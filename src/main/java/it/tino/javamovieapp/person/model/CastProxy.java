package it.tino.javamovieapp.person.model;

import it.tino.javamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class CastProxy implements Cast {

    private final MovieRepository movieRepository;
    private final int movieId;
    private Set<Person> cast;

    @Override
    public Set<Person> getPeople() {
        if (cast == null) {
            cast = movieRepository.getCredits(movieId);
        }
        return cast;
    }
}
