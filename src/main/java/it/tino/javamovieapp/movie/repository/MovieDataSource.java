package it.tino.javamovieapp.movie.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
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

    private final String BASE_URL = "https://api.themoviedb.org/3/";

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Override
    public MoviesCollection findAll(MovieSorting sorting, int page) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Movie.class, new MovieDeserializer(this));

        try {
            URL url = new URL(BASE_URL + "movie/" + sorting.getKey() + "?page=" + page);
            return makeHttpCall(url, MoviesCollection.class, Optional.of(module));
        } catch (IOException e) {
            System.out.println(e);
            return new MoviesCollection();
        }
    }

    @Override
    public MoviesCollection search(String query, int page) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Movie.class, new MovieDeserializer(this));

        try {
            URL url = new URL(BASE_URL + "search/movie?query=" + query + "&page=" + page);
            return makeHttpCall(url, MoviesCollection.class, Optional.of(module));
        } catch (IOException e) {
            System.out.println(e);
            return new MoviesCollection();
        }
    }

    @Override
    public Optional<Movie> getMovieDetails(int id) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Movie.class, new MovieDeserializer(this));

        try {
            URL url = new URL(BASE_URL + "movie/" + id);
            return Optional.ofNullable(makeHttpCall(url, Movie.class, Optional.of(module)));
        } catch (IOException e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    @Override
    public Set<Person> getCredits(int movieId) {
        try {
            URL url = new URL(BASE_URL + "movie/" + movieId + "/credits");
            Cast cast = makeHttpCall(url, RealCast.class);
            return new TreeSet<>(cast.getPeople());
        } catch (IOException e) {
            System.out.println(e);
            return Collections.emptySet();
        }
    }

    private <T> T makeHttpCall(URL url, Class<T> returnType, Optional<Module> module) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) connectionProvider.getConnection(url);
        connection.addRequestProperty("Authorization", "Bearer " + apiKey);

        String responseBody = connectionProvider.getResponseBody(connection);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        module.ifPresent(objectMapper::registerModule);

        return objectMapper.readValue(responseBody, returnType);
    }

    private <T> T makeHttpCall(URL url, Class<T> returnType) throws IOException {
        return makeHttpCall(url, returnType, Optional.empty());
    }
}
