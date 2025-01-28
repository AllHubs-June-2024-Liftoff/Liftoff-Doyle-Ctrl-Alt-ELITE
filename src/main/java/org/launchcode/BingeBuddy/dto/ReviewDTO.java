package org.launchcode.BingeBuddy.dto;

public class ReviewDTO {
    private Integer id;
    private String content;
    private int rating;
    private String username;


    public ReviewDTO(Integer id, String content, int rating, String username) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.username = username;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
