package Classes;

import java.util.ArrayList;

public class Review {
    private String reviewDisplayName;
    private int score;
    private ArrayList<Comment> reviewComments;
    private String reviewText;

    //Getters and setters
    public String getReviewDisplayName() {
        return reviewDisplayName;
    }
    public void setReviewDisplayName(String reviewDisplayName) {
        this.reviewDisplayName = reviewDisplayName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public ArrayList<Comment> getReviewComments() {
        return reviewComments;
    }
    public void setReviewComments(ArrayList<Comment> reviewComments) {
        this.reviewComments = reviewComments;
    }
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
