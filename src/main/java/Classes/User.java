package Classes;

import java.util.ArrayList;

public class User {
    private String realName;
    private String email;
    private String displayName;
    private ArrayList<Movie> userMoviesWatched;
    private ArrayList<Review> userReviews;
    private ArrayList<Comment> userComments;

    //getters and setters
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public ArrayList<Movie> getUserMoviesWatched() {
        return userMoviesWatched;
    }
    public void setUserMoviesWatched(ArrayList<Movie> userMoviesWatched) {
        this.userMoviesWatched = userMoviesWatched;
    }
    public ArrayList<Review> getUserReviews() {
        return userReviews;
    }
    public void setUserReviews(ArrayList<Review> userReviews) {
        this.userReviews = userReviews;
    }
    public ArrayList<Comment> getUserComments() {
        return userComments;
    }
    public void setUserComments(ArrayList<Comment> userComments) {
        this.userComments = userComments;
    }
}
