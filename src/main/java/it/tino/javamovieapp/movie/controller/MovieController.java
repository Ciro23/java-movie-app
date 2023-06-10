package it.tino.javamovieapp.movie.controller;

import it.tino.javamovieapp.movie.model.MoviesCollection;
import it.tino.javamovieapp.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping
    public String showAllMovies(Model model) {
        MoviesCollection movies = movieRepository.findAll();
        model.addAttribute("movies", movies);

        return "movies";
    }
}
