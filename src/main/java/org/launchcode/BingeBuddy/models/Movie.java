package org.launchcode.BingeBuddy.models;

import java.util.List;

import jakarta.persistence.*;


@Entity
public class Movie extends AbstractEntity {


    @Column
    private String title;

    private int rating;

    @Column(nullable = true)
    private String genre;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Column(nullable = true)
    private String releaseYear;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String posterUrl;

    @Column(nullable = true, unique = true)
    private String apiId; // External API identifier (e.g., TheTVDB ID)

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews; // Association with reviews


    public Movie() {
    }

    public Movie(String title, String genre, String releaseDate, String description, String posterUrl, String apiId) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseDate;
        this.description = description;
        this.posterUrl = posterUrl;
        this.apiId = apiId;
    }

    // Getters and Setters

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

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setMovie(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setMovie(null);
    }
}

