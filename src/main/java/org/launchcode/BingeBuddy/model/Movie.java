package org.launchcode.BingeBuddy.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.util.InternalApi;


@Entity
public class Movie extends AbstractEntity {


    @Column
    @JsonProperty("Title")
    private String title;

    private int rating;

    @JsonProperty("Type")
    private String type;

//    @JsonProperty("imdbId")
//    private String imdbId;

    @Column(nullable = true)
    @JsonProperty("Year")
    private String year;

//    @Column(nullable = true)
//    @Size(min = 3, max = 500, message = "Location must be between 3 and 150 characters")
//    @JsonProperty("Description")
//    private String description;

    @Column(nullable = true)
    @JsonProperty("Poster")
    private String poster;

    @InternalApi
    @Column(nullable = true, unique = true)
    private String apiId; // External API identifier (e.g., TheTVDB ID)

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>(); // Association with reviews

//    public String getImdbId() {
//        return imdbId;
//    }
//
//    public void setImdbId(String imdbId) {
//        this.imdbId = imdbId;
//    }

    public Movie() {

    }

    public Movie(String title, int rating, String type, String imdbId, String releaseDate, String description, String posterUrl, String apiId) {
        this.title = title;
        this.rating = rating;
        this.type = type;
//        this.imdbId = imdbId;
        this.year = releaseDate;
//        this.description = description;
        this.poster = posterUrl;
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


    public String getYear() {
        return year;
    }

    public void setYear(String releaseYear) {
        this.year = releaseYear;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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


}