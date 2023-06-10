package it.tino.javamovieapp.movie.controller;

import it.tino.javamovieapp.movie.model.MovieSorting;
import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping({"/", "movies/{filter}", "movies/{filter}/{page}"})
    public String showAllMovies(
        @PathVariable(required = false) String filter,
        @PathVariable(required = false) Integer page,
        Model model
    ) {
        MoviesCollection movies;

        if (filter == null) {
            filter = MovieSorting.DEFAULT.getKey();
            movies = movieRepository.findAll();
        } else if (page == null) {
            movies = movieRepository.findAll(MovieSorting.fromKey(filter));
        } else {
            movies = movieRepository.findAll(MovieSorting.fromKey(filter), page);
        }

        model.addAttribute("movies", movies);
        model.addAttribute("filter", filter);

        return "movies";
    }
}
