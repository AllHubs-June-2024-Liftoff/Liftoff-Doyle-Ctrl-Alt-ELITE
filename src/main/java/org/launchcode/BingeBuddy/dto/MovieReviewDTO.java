package org.launchcode.BingeBuddy.dto;

import org.launchcode.BingeBuddy.model.Movie;

import java.util.List;

public class MovieReviewDTO {

    private String title;
    private String type;
    private String year;
    private String poster;
    private List<ReviewDTO> reviews;

    public MovieReviewDTO(Movie movie, List<ReviewDTO> reviews) {
        this.title = movie.getTitle();
        this.type = movie.getType();
        this.year = movie.getYear();
        this.poster = movie.getPoster();
        this.reviews = reviews;
    }

    public String getTitle() { return title; }
    public String getType() { return type; }
    public String getYear() { return year; }
    public String getPoster() { return poster; }
    public List<ReviewDTO> getReviews() { return reviews; }
}
