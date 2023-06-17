package it.tino.javamovieapp.movie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoviesCollection {

    @JsonProperty("page")
    int currentPage = 1;

    @JsonProperty("total_pages")
    int totalPages = 1;

    @JsonProperty("total_results")
    int totalResults = 0;

    @JsonProperty("results")
    Collection<Movie> movies = new ArrayList<>();
}
