package it.tino.javamovieapp.movie.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.network.ConnectionProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class MovieDataSourceTest {

    @InjectMocks
    MovieDataSource movieDataSource;

    @Mock
    ConnectionProvider connectionProvider;

    @Mock
    HttpURLConnection httpURLConnection;

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void find_all_successful() throws IOException, URISyntaxException {
        Mockito
                .when(connectionProvider.getConnection(Mockito.any()))
                .thenReturn(httpURLConnection);

        URL url = Objects.requireNonNull(classLoader.getResource("movies_collection.json"));
        List<String> fileLines = Files.readAllLines(Paths.get(url.toURI()));
        String responseInJson = String.join("\n", fileLines);

        Mockito
                .when(connectionProvider.getResponseBody(httpURLConnection))
                .thenReturn(responseInJson);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MoviesCollection expected = objectMapper.readValue(responseInJson, MoviesCollection.class);

        MoviesCollection actual = movieDataSource.findAll();

        Assertions.assertEquals(expected, actual);
    }
}
