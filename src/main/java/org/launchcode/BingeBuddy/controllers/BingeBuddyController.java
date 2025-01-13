package org.launchcode.BingeBuddy.controllers;

import jakarta.validation.Valid;
import org.launchcode.BingeBuddy.models.Comment;
import org.launchcode.BingeBuddy.models.Movie;
import org.launchcode.BingeBuddy.models.Review;
import org.launchcode.BingeBuddy.models.Watchlist;
import org.launchcode.BingeBuddy.services.APIService;
import org.launchcode.BingeBuddy.services.AuthService;
import org.launchcode.BingeBuddy.utils.JWTUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class BingeBuddyController {

    @Autowired
    private APIService apiService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JWTUtility jwtUtility;

    // Movies
    @GetMapping("/movies/{apiId}")
    public ResponseEntity<Movie> getMovieByApiId(@PathVariable String apiId) {
        Movie movie = apiService.fetchMovie(apiId);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/movies/details")
    public ResponseEntity<Movie> postMovieDetails(@RequestParam String apiId) {
        Movie movie = apiService.fetchMovie(apiId);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }


    @PostMapping("/movies/associate")
    public ResponseEntity<String> associateMovieWithUser(@RequestParam String apiId, @RequestParam String userId) {
        String token = authService.associateMovieWithUser(apiId, userId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/movies/fromToken")
    public ResponseEntity<Movie> getMovieFromToken(@RequestParam String token) {
        Movie movie = authService.fetchMovieFromToken(token);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    // Reviews
    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        // Add logic to save review associated with a movie
        if (review == null || review.getMovie() == null) {
            return ResponseEntity.badRequest().build();
        }
        Review savedReview = authService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    // Comments
    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment) {
        // Add logic to save comment associated with a review
        if (comment == null || comment.getReview() == null) {
            return ResponseEntity.badRequest().build();
        }
        Comment savedComment = authService.addComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    // Watchlist
    @PostMapping("/watchlists")
    public ResponseEntity<Watchlist> addToWatchlist(@RequestBody Watchlist watchlist) {
        // Logic to save watchlist (assuming proper associations are set in the request)
        if (watchlist == null || watchlist.getMovie() == null) {
            return ResponseEntity.badRequest().build();
        }
        Watchlist savedWatchlist = authService.addToWatchlist(watchlist);
        return ResponseEntity.ok(savedWatchlist);
    }

    @GetMapping("/watchlists")
    public ResponseEntity<List<Watchlist>> getWatchlist(@RequestParam String userId) {
        // Logic to fetch watchlist
        List<Watchlist> watchlist = authService.getWatchlistForUser(userId);
        if (watchlist == null || watchlist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/")
    public ResponseEntity<Movie> getRandomMovie() {
        // Example random titles
        String[] randomTitles = {"Matrix", "Avengers", "Forrest Gump", "Inception", "Titanic"};
        String randomTitle = randomTitles[new Random().nextInt(randomTitles.length)];

        List<Movie> movies = apiService.searchMoviesByTitle(randomTitle);
        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Return the first movie (or pick randomly from results)
        Movie randomMovie = movies.get(0);
        return ResponseEntity.ok(randomMovie);
    }
}