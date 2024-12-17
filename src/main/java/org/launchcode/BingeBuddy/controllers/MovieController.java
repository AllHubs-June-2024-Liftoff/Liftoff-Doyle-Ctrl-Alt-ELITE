package org.launchcode.BingeBuddy.controllers;

import org.launchcode.BingeBuddy.data.MovieRepository;
import org.launchcode.BingeBuddy.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Object getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).map(movie -> ResponseEntity.ok().body(movie));

    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(201).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Movie not found");
        }
        movie.setId(id);
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Movie not found");
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
