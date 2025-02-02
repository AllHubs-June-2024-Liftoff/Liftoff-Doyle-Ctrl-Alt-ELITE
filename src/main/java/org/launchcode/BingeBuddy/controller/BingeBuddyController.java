package org.launchcode.BingeBuddy.controller;


import org.launchcode.BingeBuddy.config.APIConfiguration;
import org.launchcode.BingeBuddy.data.*;
import org.launchcode.BingeBuddy.dto.CommentDTO;
import org.launchcode.BingeBuddy.dto.MovieReviewDTO;
import org.launchcode.BingeBuddy.dto.WatchlistDTO;
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

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final WatchlistRepository watchlistRepository;
    private final APIConfiguration apiConfig;
    private final RestTemplate restTemplate;

    public BingeBuddyController(
            CommentRepository commentRepository,
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            MovieRepository movieRepository,
            WatchlistRepository watchlistRepository,
            APIConfiguration apiConfig
    ) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.watchlistRepository = watchlistRepository;
        this.apiConfig = apiConfig;
        this.restTemplate = new RestTemplate();
    }

    @GetMapping
    public String homePage(){
        return "BingeBuddy";
    }
    @GetMapping("/user-dashboard/{userId}")
    public ResponseEntity<?> getUserDashboard(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserDashboard response = new UserDashboard();
        response.setUser(user.get());
        response.setWatchlist(watchlistRepository.findByUser_Id(userId));
        response.setReviews(reviewRepository.findByUser_Id(userId));

        return ResponseEntity.ok(response);
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
        Optional<Movie> movieOpt = movieRepository.findByApiId(apiId);

        if (movieOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
        }

        Movie movie = movieOpt.get();
        String posterUrl = movie.getPoster();

        List<ReviewDTO> reviewDTOs = movie.getReviews().stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getUsername(),
                        posterUrl
                ))
                .collect(Collectors.toList());

        MovieReviewDTO movieReviewDTO = new MovieReviewDTO(movie, reviewDTOs);

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




    @GetMapping("/watchlist/user/{userId}")
    public ResponseEntity<List<WatchlistDTO>> getWatchlistByUser(@PathVariable Integer userId) {
        List<Watchlist> watchlists = watchlistRepository.findByUser_Id(userId);
        if (watchlists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<WatchlistDTO> watchlistDTOs = watchlists.stream()
                .map(watchlist -> new WatchlistDTO(
                        watchlist.getId(),
                        watchlist.getMovie().getTitle(),
                        watchlist.getMovie().getPoster(),
                        watchlist.getScheduledDate(),
                        watchlist.getStatus().toString()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(watchlistDTOs);
    }



    @DeleteMapping("/watchlist/{watchlistId}")
    public ResponseEntity<String> removeFromWatchlist(@PathVariable Integer watchlistId) {
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
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String posterUrl = movie.get().getPoster();

        List<ReviewDTO> response = reviewRepository.findByMovieId(movieId).stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getUser().getUsername(),
                        posterUrl
                ))
                .collect(Collectors.toList());


        return ResponseEntity.ok(response);
    }



    @GetMapping("review/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Integer reviewId) {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);

        if (reviewOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review review = reviewOpt.get();
        Movie movie = review.getMovie();
        String posterUrl = (movie != null) ? movie.getPoster() : null;

        ReviewDTO reviewDTO = new ReviewDTO(
                review.getId(),
                review.getContent(),
                review.getRating(),
                review.getUser().getUsername(),
                posterUrl
        );

        return ResponseEntity.ok(reviewDTO);
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

