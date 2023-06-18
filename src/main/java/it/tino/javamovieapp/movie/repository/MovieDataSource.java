package it.tino.javamovieapp.movie.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.tino.javamovieapp.movie.model.Movie;
import it.tino.javamovieapp.movie.model.MovieDeserializer;
import it.tino.javamovieapp.movie.model.MovieSorting;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.network.ConnectionProvider;
import it.tino.javamovieapp.person.model.Cast;
import it.tino.javamovieapp.person.model.Person;
import it.tino.javamovieapp.person.model.RealCast;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MovieDataSource implements MovieRepository {

    private final ConnectionProvider connectionProvider;

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Override
    public MoviesCollection findAll(MovieSorting sorting, int page) {
        try {
            String path = "movie/" + sorting.getKey() + "?page=" + page;
            return makeHttpCall(path, MoviesCollection.class);
        } catch (IOException e) {
            System.out.println(e);
            return new MoviesCollection();
        }
    }

    @Override
    public MoviesCollection search(String query, int page) {
        try {
            String path = "search/movie?query=" + query + "&page=" + page;
            return makeHttpCall(path, MoviesCollection.class);
        } catch (IOException e) {
            System.out.println(e);
            return new MoviesCollection();
        }
    }

    @Override
    public Optional<Movie> getMovieDetails(int id) {
        try {
            String path = "movie/" + id;
            return Optional.ofNullable(makeHttpCall(path, Movie.class));
        } catch (IOException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    @Override
    public Set<Person> getCredits(int movieId) {
        try {
            String path = "movie/" + movieId + "/credits";
            Cast cast = makeHttpCall(path, RealCast.class);
            return new TreeSet<>(cast.getPeople());
        } catch (IOException e) {
            System.out.println(e);
            return Collections.emptySet();
        }
    }

    private <T> T makeHttpCall(String path, Class<T> returnType) throws IOException {
        String BASE_URL = "https://api.themoviedb.org/3/";
        URL url = new URL(BASE_URL + path);

        HttpURLConnection connection = (HttpURLConnection) connectionProvider.getConnection(url);
        connection.addRequestProperty("Authorization", "Bearer " + apiKey);

        String responseBody = connectionProvider.getResponseBody(connection);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Movie.class, new MovieDeserializer(this));
        objectMapper.registerModule(module);

        return objectMapper.readValue(responseBody, returnType);
    }
}
