package it.tino.javamovieapp.movie.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.tino.javamovieapp.movie.repository.MovieRepository;
import it.tino.javamovieapp.person.model.CastProxy;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class MovieDeserializer extends JsonDeserializer<Movie> {

    private final MovieRepository repository;

    public MovieDeserializer(MovieRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Movie deserialize(JsonParser p, DeserializationContext context) throws IOException {
        Movie movie = new Movie();

        JsonNode node = p.readValueAsTree();

        movie.setId(node.get("id").asInt());
        movie.setTitle(node.get("title").asText());
        movie.setVoteAverage(node.get("vote_average").floatValue());
        movie.setReleaseDate(node.get("release_date").asText());
        movie.setPosterPath(node.get("poster_path").asText());
        movie.setOverview(node.get("overview").asText());

        JsonNode genresNode = node.get("genres");
        if (genresNode != null) {
            JsonParser genresParser = genresNode.traverse();
            ObjectMapper objectMapper = new ObjectMapper();

            Genre[] genres = objectMapper.readValue(genresParser, Genre[].class);
            movie.setGenres(new HashSet<>(List.of(genres)));
        }

        JsonNode budgetNode = node.get("budget");
        if (budgetNode != null) {
            movie.setBudget(budgetNode.asInt());
        }

        JsonNode runtimeNode = node.get("runtime");
        if (runtimeNode != null) {
            movie.setRuntime(runtimeNode.asInt());
        }

        CastProxy castProxy = new CastProxy(repository, movie.getId());
        movie.setCast(castProxy);

        return movie;
    }
}
