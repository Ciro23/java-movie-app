package it.tino.javamovieapp.movie.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.tino.javamovieapp.movie.model.Movie;
import it.tino.javamovieapp.movie.model.MovieSorting;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.network.ConnectionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Component
public class MovieDataSource implements MovieRepository {

    private final ConnectionProvider connectionProvider;

    private final String BASE_URL = "https://api.themoviedb.org/3/";

    @Value("${tmdb.api-key}")
    private String apiKey;

    public MovieDataSource(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public MoviesCollection findAll(MovieSorting sorting, int page) {
        try {
            URL url = new URL(BASE_URL + "movie/" + sorting.getKey() + "?page=" + page);
            return makeHttpCall(url, MoviesCollection.class);
        } catch (IOException e) {
            return new MoviesCollection();
        }
    }

    @Override
    public MoviesCollection search(String query, int page) {
        try {
            URL url = new URL(BASE_URL + "search/movie?query=" + query + "&page=" + page);
            return makeHttpCall(url, MoviesCollection.class);
        } catch (IOException e) {
            return new MoviesCollection();
        }
    }

    @Override
    public Optional<Movie> getMovieDetails(int id) {
        try {
            URL url = new URL(BASE_URL + "movie/" + id);
            return Optional.ofNullable(makeHttpCall(url, Movie.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private <T> T makeHttpCall(URL url, Class<T> returnType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) connectionProvider.getConnection(url);
        connection.addRequestProperty("Authorization", "Bearer " + apiKey);

        String responseBody = connectionProvider.getResponseBody(connection);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(responseBody, returnType);
    }
}
