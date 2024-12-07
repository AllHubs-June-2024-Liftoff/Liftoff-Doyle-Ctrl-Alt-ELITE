package org.launchcode.BingeBuddy.controllers;

import org.launchcode.BingeBuddy.data.MovieRepository;
import org.launchcode.BingeBuddy.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Display all movies
    @GetMapping
    public String index(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movie-list";
    }

    // Display the form to add a movie
    @GetMapping("/add")
    public String displayAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-add"; // Returns the `movie-add.html` template
    }

    // Handle the form submission to add a movie
    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieRepository.save(movie); // Save the movie to the database
        return "redirect:/movies"; // Redirect to the movies index page
    }

    @GetMapping("/list")
    public String movieList(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return "redirect:/movies/list";
    }

    @GetMapping("/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + id));
        model.addAttribute("movie", movie);
        return "movie-add"; // Reuse your `movie-add.html` template for editing
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movie.setId(id); // Ensure the ID is preserved
        movieRepository.save(movie);
        return "redirect:/movies/list";
    }
}
