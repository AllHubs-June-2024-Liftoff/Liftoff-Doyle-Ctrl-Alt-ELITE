package org.launchcode.BingeBuddy.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.launchcode.BingeBuddy.data.MovieRepository;
import org.launchcode.BingeBuddy.data.ReviewRepository;
import org.launchcode.BingeBuddy.models.Movie;
import org.launchcode.BingeBuddy.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    // Handle GET /reviews
    @GetMapping
    public String index(Model model) {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        System.out.println("Reviews fetched: " + reviews.size());
        model.addAttribute("reviews", reviews);
        return "review";
    }


    @GetMapping("/add")
    public String displayAddReviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("movies", movieRepository.findAll());
        return "review-add";
    }

    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute Review review, Errors errors, Model model, Movie movie) {
        if (errors.hasErrors() || review.getMovie() == null || review.getMovie().getId() == null) {
            model.addAttribute("error", "All fields are required, and a movie must be selected.");
            model.addAttribute("movies", movieRepository.findAll());
            return "review-add";
        }

        model.addAttribute("review", reviewRepository.findAll());
        review.setMovie(movie);
        reviewRepository.save(review);

        return "redirect:/reviews";
    }


    // Handle PUT /reviews/{id}
    @PutMapping("/{id}")
    public String updateReview(
            @PathVariable Long id,
            Review updatedReview,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Review");
            model.addAttribute("errors", errors.getAllErrors());
            return "review/edit";
        }

        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            model.addAttribute("message", "Review not found");
            return "error/404";
        }

        Review review = optionalReview.get();
        review.setContent(updatedReview.getContent());
        review.setRating(updatedReview.getRating());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
        return "redirect:/reviews/" + id;
    }

    // Handle DELETE /reviews/{id}
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id, Model model) {
        reviewRepository.deleteById(id);
        model.addAttribute("reviews", reviewRepository.findAll());
        return "review" + id;
    }

    // Handle GET /reviews/{id}
    @GetMapping("/{id}")
    public String getReviewById(@PathVariable Long id, Model model) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            model.addAttribute("message", "Review not found");
            return "error/404";
        }
        model.addAttribute("review", optionalReview.get());
        return "review-detail";
    }

    @GetMapping("/movies/{movieId}/reviews")
    public String getReviewsByMovie(@PathVariable Long movieId, Model model) {
        List<Review> reviews = reviewRepository.findByMovie_Id(movieId);
        System.out.println("Reviews fetched: " + reviews.size()); // Log the count of reviews fetched
        model.addAttribute("reviews", reviews);
        return "review-detail";
    }

    @GetMapping("/reviews")
    public String showAllReviews(Model model) {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        model.addAttribute("movies", movieRepository.findAll());
        return "review";
    }

    @Transactional
    public void saveReview(Review review) {
        Movie movie = movieRepository.findById(review.getMovie().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        review.setMovie(movie);
        reviewRepository.save(review);
    }
}
