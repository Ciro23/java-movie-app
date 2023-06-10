package it.tino.javamovieapp.movie.controller;

import it.tino.javamovieapp.movie.model.MovieSorting;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping

    public String showAllMovies(@RequestParam(required = false) String filter, Model model) {
        MoviesCollection movies;

        if (filter == null) {
            filter = MovieSorting.DEFAULT.getKey();
            movies = movieRepository.findAll();
        } else {
            movies = movieRepository.findAll(MovieSorting.fromKey(filter));
        }

        model.addAttribute("movies", movies);
        model.addAttribute("filter", filter);

        return "movies";
    }
}
