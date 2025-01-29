package org.launchcode.BingeBuddy.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.util.InternalApi;


@Entity
public class Movie extends AbstractEntity {


    @Column
    @JsonProperty("Title")
    private String title;



    @JsonProperty("Type")
    private String type;

    @Column(nullable = true)
    @JsonProperty("Year")
    private String year;

    @Column(nullable = true)
    @JsonProperty("Poster")
    private String poster;

    @InternalApi
    @Column(nullable = true, unique = true)
    private String apiId;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public Movie() {

    }

    public Movie(String title, String type, String releaseDate, String posterUrl, String apiId) {
        this.title = title;

        this.type = type;
        this.year = releaseDate;
        this.poster = posterUrl;
        this.apiId = apiId;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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