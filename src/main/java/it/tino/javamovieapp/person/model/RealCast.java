package it.tino.javamovieapp.person.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class RealCast implements Cast {

    @JsonProperty("id")
    private int id;

    @JsonProperty("cast")
    private Set<Person> people;
}
