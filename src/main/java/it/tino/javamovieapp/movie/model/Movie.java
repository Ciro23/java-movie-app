package it.tino.javamovieapp.movie.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.tino.javamovieapp.person.model.Cast;
import it.tino.javamovieapp.person.model.Person;
import lombok.*;

import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("vote_average")
    private float voteAverage;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("genres")
    private Set<Genre> genres;

    private Cast cast;

    @JsonProperty("budget")
    private int budget;

    @JsonProperty("runtime")
    private int runtime;

    public Set<Person> getCast() {
        return cast.getPeople();
    }

    /**
     * Budget may contain many numbers, so dots should
     * for decimal separators.
     * @param locale The decimal separator differs based on the
     *               locale. E.g. for {@link Locale#US} it's ",",
     *               but for {@link Locale#ITALY} it's "."
     * @return The budget with decimal separators, as "1.000.000"
     */
    public String getFormattedBudget(Locale locale) {
        return String.format(locale, "%,d", budget);
    }

    public String getFormattedBudget() {
        return getFormattedBudget(Locale.US);
    }
    
    public String getFormattedRuntime() {
        int hours = (int) Math.floor((double)runtime / 60);
        int minutes = runtime - hours * 60;

        return hours + "h " + minutes + "m";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
