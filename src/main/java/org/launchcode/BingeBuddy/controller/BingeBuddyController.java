package org.launchcode.BingeBuddy.controller;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.launchcode.BingeBuddy.config.APIConfiguration;
import org.launchcode.BingeBuddy.data.*;
import org.launchcode.BingeBuddy.dto.CommentDTO;
import org.launchcode.BingeBuddy.dto.MovieReviewDTO;
import org.launchcode.BingeBuddy.model.*;
import org.launchcode.BingeBuddy.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class BingeBuddyController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private APIConfiguration apiConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String homePage(){
        return "BingeBuddy";
    }

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<UserDashboard> getDashboard(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            UserDashboard dashboard = new UserDashboard();
            dashboard.setUser(user.get());
            dashboard.setWatchlist(watchlistRepository.findByUser_Id(userId));
            dashboard.setReviews(reviewRepository.findByUser_Id(userId));
            return ResponseEntity.ok(dashboard);
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        String apiUrl = String.format("%s?s=%s&apikey=%s", apiConfig.getApiUrl(), query, apiConfig.getApiKey());
        SearchResponse response = restTemplate.getForObject(apiUrl, SearchResponse.class);

        if (response != null && response.getMovies() != null) {
            return ResponseEntity.ok(response.getMovies());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/movie")
    public ResponseEntity<?> getMovieByApiId(@RequestParam String apiId) {
        Optional<Movie> movie = movieRepository.findByApiId(apiId);

        if (movie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        List<ReviewDTO> reviewDTOs = movie.get().getReviews().stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getUsername()
                ))
                .collect(Collectors.toList());

        MovieReviewDTO movieReviewDTO = new MovieReviewDTO(movie.get(), reviewDTOs);

        return ResponseEntity.ok(movieReviewDTO);
    }




    @PostMapping("/watchlist")
    public ResponseEntity<String> addToWatchlist(
            @RequestParam String apiId,
            @RequestParam Integer userId,
            @RequestParam WatchlistStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduledDate) {

        Optional<Movie> movie = movieRepository.findByApiId(apiId);
        if (movie.isEmpty()) {
            String apiUrl = String.format("%s?i=%s&apikey=%s", apiConfig.getApiUrl(), apiId, apiConfig.getApiKey());
            Movie fetchedMovie = restTemplate.getForObject(apiUrl, Movie.class);

            if (fetchedMovie != null) {
                fetchedMovie.setApiId(apiId);
                movieRepository.save(fetchedMovie);
                movie = Optional.of(fetchedMovie);
            } else {
                return ResponseEntity.badRequest().body("Movie not found in database or external API.");
            }
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found. Please provide a valid user ID.");
        }

        Watchlist watchlistEntry = new Watchlist();
        watchlistEntry.setMovie(movie.get());
        watchlistEntry.setUser(user.get());
        watchlistEntry.setStatus(status);
        watchlistEntry.setScheduledDate(scheduledDate);

        watchlistRepository.save(watchlistEntry);

        return ResponseEntity.ok("Movie added to watchlist with status: " + status +
                (scheduledDate != null ? " and scheduled for: " + scheduledDate : ""));
    }



    @GetMapping("/watchlist/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlist(@PathVariable Integer watchlistId) {
        return watchlistRepository.findById(watchlistId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/watchlist/{watchlistId}")
    public ResponseEntity<String> removeFromWatchlist(@RequestParam Integer watchlistId) {
        Optional<Watchlist> watchlistEntry = watchlistRepository.findById(watchlistId);
        if (watchlistEntry.isPresent()) {
            watchlistRepository.delete(watchlistEntry.get());
            return ResponseEntity.ok("Watchlist entry removed.");
        } else {
            return ResponseEntity.badRequest().body("Watchlist entry not found.");
        }
    }


    @PostMapping("/review/{movieId}")
    public ResponseEntity<String> createReview(
            @PathVariable Integer movieId,
            @RequestParam Integer userId,
            @RequestBody Review review) {

        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            return ResponseEntity.badRequest().body("Movie not found.");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        review.setMovie(movie.get());
        review.setUser(user.get());

        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);

        return ResponseEntity.ok("Review created successfully.");
    }



    @GetMapping("/review")
    public ResponseEntity<List<ReviewDTO>> getReviews(@RequestParam Integer movieId) {
        List<ReviewDTO> response = reviewRepository.findByMovieId(movieId).stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getUsername()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("review/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getUsername()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @PutMapping("review/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Integer reviewId, @RequestBody Review updatedReview) {
        Optional<Review> existingReview = reviewRepository.findById(reviewId);
        if (existingReview.isEmpty()) {
            return ResponseEntity.badRequest().body("Review not found.");
        }

        Review review = existingReview.get();
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        return ResponseEntity.ok("Review updated successfully.");
    }


    @DeleteMapping("review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            return ResponseEntity.badRequest().body("Review not found.");
        }

        reviewRepository.delete(review.get());
        return ResponseEntity.ok("Review deleted successfully.");
    }

    @PostMapping("/comments")
    public ResponseEntity<String> createComment(@RequestParam Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            return ResponseEntity.badRequest().body("Review not found.");
        }

        comment.setReview(review.get());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment added successfully.");
    }


    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByReview(@RequestParam Integer reviewId) {
        List<CommentDTO> response = commentRepository.findByReview_Id(reviewId).stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUser() != null ? comment.getUser().getUsername() : "Anonymous"
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Integer commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUser() != null ? comment.getUser().getUsername() : "Anonymous" // Handle null user
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Integer commentId, @RequestBody Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comment comment = existingComment.get();
        comment.setContent(updatedComment.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);

        return ResponseEntity.ok("Comment updated successfully.");
    }


    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        commentRepository.delete(comment.get());
        return ResponseEntity.ok("Comment deleted successfully.");
    }

}

