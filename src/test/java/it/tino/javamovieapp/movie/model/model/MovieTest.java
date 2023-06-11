package it.tino.javamovieapp.movie.model.model;

import it.tino.javamovieapp.movie.model.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class MovieTest {

    @Test
    void get_formatted_budget_under_1000_test() {
        makeAssertion(100, "100", Locale.US);
    }

    @Test
    void get_formatted_budget_for_us_test() {
        makeAssertion(1000, "1,000", Locale.US);
    }

    @Test
    void get_formatted_budget_for_italy_test() {
        makeAssertion(1000, "1.000", Locale.ITALY);
    }

    @Test
    void get_formatted_budget_test() {
        makeAssertion(1000000, "1.000.000", Locale.ITALY);
    }

    private void makeAssertion(int budget, String expected, Locale locale) {
        Movie movie = new Movie();
        movie.setBudget(budget);

        String actual = movie.getFormattedBudget(locale);
        Assertions.assertEquals(expected, actual);
    }
}
