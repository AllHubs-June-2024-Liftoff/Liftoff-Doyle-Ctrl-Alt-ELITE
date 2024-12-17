package org.launchcode.BingeBuddy.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.util.InternalApi;


@Entity
public class Movie extends AbstractEntity {


    @Column
    private String title;

    private int rating;

    private String type;

    @Column(nullable = true)
    private String genre;

    @Column(nullable = true)
    private String releaseYear;

    @Column(nullable = true)
    @Size(min = 3, max = 500, message = "Location must be between 3 and 150 characters")
    private String description;

    @Column(nullable = true)
    private String posterUrl;

    @InternalApi
    @Column(nullable = true, unique = true)
    private String apiId; // External API identifier (e.g., TheTVDB ID)

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // Association with reviews


    public Movie() {
    }

    public Movie(String title, int rating, String type, String genre, String releaseDate, String description, String posterUrl, String apiId) {
        this.title = title;
        this.rating = rating;
        this.type = type;
        this.genre = genre;
        this.releaseYear = releaseDate;
        this.description = description;
        this.posterUrl = posterUrl;
        this.apiId = apiId;

    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> review) {
        this.reviews = review;
    }

    public void addReview(Review newReview) {
        this.reviews.add(newReview);
        newReview.setMovie(this); // Establish bidirectional relationship
    }

    public void removeReview(Review reviewToRemove) {
        this.reviews.remove(reviewToRemove);
        reviewToRemove.setMovie(null); // Remove association
    }
}