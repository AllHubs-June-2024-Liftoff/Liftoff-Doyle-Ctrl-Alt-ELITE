package org.launchcode.BingeBuddy.services;

import org.launchcode.BingeBuddy.data.MovieRepository;
import org.launchcode.BingeBuddy.data.UserRepository;
import org.launchcode.BingeBuddy.data.WatchlistRepository;
import org.launchcode.BingeBuddy.models.*;
import org.launchcode.BingeBuddy.utils.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private APIService apiService;

    public String associateMovieWithUser(String apiId, String userId) {
        // Fetch user by ID
        User user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch movie by API ID or add to repository
        Movie movie = movieRepository.findByApiId(apiId)
                .orElseGet(() -> {
                    Movie fetchedMovie = apiService.fetchMovie(apiId);
                    movieRepository.save(fetchedMovie);
                    return fetchedMovie;
                });

        // Add movie to user's watchlist
        Watchlist watchlist = new Watchlist();
        watchlist.setMovie(movie);
        watchlist.setStatus(WatchlistStatus.PLANNED);
        watchlistRepository.save(watchlist);

        // Generate token
        Map<String, Object> claims = new HashMap<>();
        claims.put("movieId", movie.getId());

        return jwtUtility.generateToken(claims, user.getId().toString());
    }

    public Movie fetchMovieFromToken(String token) {
        // Extract movie ID from token
        String movieId = jwtUtility.extractAllClaims(token).get("movieId", String.class);

        // Fetch movie by ID
        return movieRepository.findById(Integer.parseInt(movieId))
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Review addReview(Review review) {
        // Logic to save review (implement as needed)
        return review;
    }

    public Comment addComment(Comment comment) {
        // Logic to save comment (implement as needed)
        return comment;
    }

    public Watchlist addToWatchlist(Watchlist watchlist) {
        // Logic to save watchlist
        return watchlistRepository.save(watchlist);
    }

    public List<Watchlist> getWatchlistForUser(String userId) {
        // Fetch watchlist for the user
        return watchlistRepository.findAllByUserId(Integer.parseInt(userId));
    }
}

