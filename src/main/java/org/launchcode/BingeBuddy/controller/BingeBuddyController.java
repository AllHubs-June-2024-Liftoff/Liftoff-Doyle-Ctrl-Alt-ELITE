package org.launchcode.BingeBuddy.controller;


import org.json.JSONObject;
import org.launchcode.BingeBuddy.config.APIConfiguration;
import org.launchcode.BingeBuddy.data.*;
import org.launchcode.BingeBuddy.model.*;
import org.launchcode.BingeBuddy.data.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class BingeBuddyController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserActivityDTORepository userActivityDTORepository;

    @Autowired
    private APIConfiguration apiConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public String homePage(){
        return "BingeBuddy";
    }


    // For charts:

    // 1. Returns all Review objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-reviews/{userId}
    @GetMapping("user-reviews/{userId}")
    public ResponseEntity<List<Review>> getAllReviewsByUserID(@PathVariable Integer userId){

        List<Review> reviewList = reviewRepository.findByUserId(userId);
        return ResponseEntity.ok(reviewList);
    }


    // 2. Returns all comment objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-comments/2
    @GetMapping("user-comments/{userId}")
    public ResponseEntity<List<Comment>> getAllCommentsByUserID(@PathVariable Integer userId) {

        List<Comment> commentList = commentRepository.findByUserId(userId);
        return ResponseEntity.ok(commentList);
    }


    // 3. Returns all Watchlist objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-watchlists/{userId}
    @GetMapping("user-watchlists/{userId}")
    public ResponseEntity<List<Watchlist>> getAllWatchlistsByUserID(@PathVariable Integer userId){
        List<Watchlist> watchlistList = watchlistRepository.findAllByUserId(userId);
        return ResponseEntity.ok(watchlistList);
    }



    // 4. Returns count of all Review objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-reviews-count/{userId}
    @GetMapping("user-reviews-count/{userId}")
    public ResponseEntity<Integer> getCountOfAllReviewsByUserID(@PathVariable Integer userId){

        List<Review> reviewList = reviewRepository.findByUserId(userId);
        return ResponseEntity.ok(reviewList.size());
    }


    // 5. Returns all comment objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-comments-count/2
    @GetMapping("user-comments-count/{userId}")
    public ResponseEntity<Integer> getCountOfAllCommentsByUserID(@PathVariable Integer userId) {

        List<Comment> commentList = commentRepository.findByUserId(userId);
        return ResponseEntity.ok(commentList.size());

//        if (!commentList.isEmpty()) {       // if not empty
//            return ResponseEntity.ok(commentList.size());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }


    // 6. Returns all Watchlist objects by UserId  -- K-WORKS!!
    // http://localhost:8080/user-watchlists-count/{userId}
    @GetMapping("user-watchlists-count/{userId}")
    public ResponseEntity<Integer> getWatchlistsByUserID(@PathVariable Integer userId){
        List<Watchlist> watchlistList = watchlistRepository.findAllByUserId(userId);
        return ResponseEntity.ok(watchlistList.size());
    }



    // 7. Returns json string for user activity
    // http://localhost:8080/user-activity/{userId}
    @GetMapping("/user-activity/{userId}")
    public ResponseEntity<String> getActivity(@PathVariable Integer userId) {
        List<JSONObject> jsonList = new ArrayList<>();
        List<Object[]> activities = userActivityDTORepository.getCurrentUserActivity(userId);
        if (! activities.isEmpty()) {
            Object[] row = activities.get(0); //get first row
            jsonList.add(createObject("Reviews", (row[0] != null) ? row[0].toString() : "0"));
            jsonList.add(createObject("Comments", (row[1] != null) ? row[1].toString() : "0"));
            jsonList.add(createObject("Watchlist", (row[2] != null) ? row[2].toString() : "0"));
            return ResponseEntity.ok(jsonList.toString());
        }
        else return ResponseEntity.ok("[{\"label\":\"Reviews\",\"value\":\"0\"}, {\"label\":\"Comments\",\"value\":\"0\"}, {\"label\":\"Watchlist\",\"value\":\"0\"}]");

    }

    private static JSONObject createObject(String label, String value)
    {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("label",label);
        jsonObj.put("value",value);
        return jsonObj;
    }





    // ------------
@GetMapping("comments/{commentId}")
public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId) {
    Optional<Comment> comment = commentRepository.findById(commentId);
    return comment.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
}


//
// Works!!
// http://localhost:8080/comments?reviewId=1
    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getCommentsByReview(@RequestParam Integer reviewId) {
        List<Comment> comments = commentRepository.findByReview_Id(reviewId);
        return ResponseEntity.ok(comments);
    }

// D.S:
// Works!! (Doesn't return comments)
// http://localhost:8080/dashboard/3
@GetMapping("/dashboard/{userId}")
public ResponseEntity<UserDashboard> getDashboard(@PathVariable Integer userId) {
    Optional<User> user = userEntityRepository.findById(userId);

    if (user.isPresent()) {
        UserDashboard dashboard = new UserDashboard();
        dashboard.setUser(user.get());
        dashboard.setWatchlist(watchlistRepository.findByUser_Id(userId));
        dashboard.setReviews(reviewRepository.findByUserId(userId));
        return ResponseEntity.ok(dashboard);
    }
    return ResponseEntity.notFound().build();
}

// Works!!
// http://localhost:8080/search?query=jaws
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
public ResponseEntity<Movie> getMovieDetails(@RequestParam String apiId) {
    Optional<Movie> existingMovie = movieRepository.findByApiId(apiId);
    if (existingMovie.isPresent()) {
        return ResponseEntity.ok(existingMovie.get());
    }

    String apiUrl = String.format("%s?i=%s&apikey=%s", apiConfig.getApiUrl(), apiId, apiConfig.getApiKey());
    Movie movie = restTemplate.getForObject(apiUrl, Movie.class);

    if (movie != null) {
        movie.setApiId(apiId);
        movieRepository.save(movie);
        return ResponseEntity.ok(movie);
    } else {
        return ResponseEntity.notFound().build();
    }
}

// Works!! (Tested by sending info as JSON & without any JSON but with request params)
// http://localhost:8080/watchlist?apiId=121233&userId=3&status=COMPLETED
// http://localhost:8080/watchlist?apiId=121233&userId=1&status=PLANNED
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


    Optional<User> user = userEntityRepository.findById(userId);
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

// Works!!   (Response is too long, have to check out relationship mapping)
// http://localhost:8080/watchlist/1
@GetMapping("/watchlist/{watchlistId}")
public ResponseEntity<List<Watchlist>> getWatchlist() {
    List<Watchlist> watchlist = watchlistRepository.findAll();
    return ResponseEntity.ok(watchlist);
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

    Optional<User> user = userEntityRepository.findById(userId);
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

// Works!! (Response is too long, have to check out relationship mapping)
// http://localhost:8080/review?movieId=1
@GetMapping("/review")
public ResponseEntity<List<Review>> getReviews(@RequestParam Integer movieId) {
    List<Review> reviews = reviewRepository.findByMovieId(movieId);
    return ResponseEntity.ok(reviews);
}


// (Response is too long, have to check out relationship mapping)
// http://localhost:8080/review/1
@GetMapping("review/{reviewId}")
public ResponseEntity<Review> getReviewById(@PathVariable Integer reviewId) {
    Optional<Review> review = reviewRepository.findById(reviewId);
    return review.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
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

// Works!! (Needed JSON in body)
// http://localhost:8080/comments?reviewId=1&comment=Thriller
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




@PutMapping("comments/{commentId}")
public ResponseEntity<String> updateComment(@PathVariable Integer commentId, @RequestBody Comment updatedComment) {
    Optional<Comment> existingComment = commentRepository.findById(commentId);
    if (existingComment.isEmpty()) {
        return ResponseEntity.badRequest().body("Comment not found.");
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
        return ResponseEntity.badRequest().body("Comment not found.");
    }

    commentRepository.delete(comment.get());
    return ResponseEntity.ok("Comment deleted successfully.");
}


//    // Get Comments for a particular User
//    @GetMapping("/comments/all")
//    public ResponseEntity<List<Comment> getAllUserComments(@RequestParam int userid) {
//        List<Comment> userComments = commentRepository.findByReview_Id()
//        return ResponseEntity.ok(users);
//    }
//
//
//    // Get Reviews for a particular User
//    @GetMapping("/reviews/all")
//
//
//    // Get Watchlists for a particular User
//    @GetMapping("/watchlists/all")

//@GetMapping("/email")  // In the UserController

}

