package Classes;

import java.util.ArrayList;

public class Movie {
    private String movieName;
    private int rating;
    private ArrayList<Review> movieReviews;

    //Getters and setters
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public ArrayList<Review> getMovieReviews() {
        return movieReviews;
    }
    public void setMovieReviews(ArrayList<Review> movieReviews) {
        this.movieReviews = movieReviews;
    }
}
