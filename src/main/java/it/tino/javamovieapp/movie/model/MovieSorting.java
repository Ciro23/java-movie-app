package it.tino.javamovieapp.movie.model;

import org.springframework.lang.Nullable;

public enum MovieSorting {

    POPULARITY("popular"),
    NOW_PLAYING("now_playing"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),

    DEFAULT(POPULARITY.key);

    private final String key;

    MovieSorting(String key) {
        this.key = key;
    }

    @Nullable
    public static MovieSorting fromKey(String key) {
        for (MovieSorting value : MovieSorting.values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }
}
