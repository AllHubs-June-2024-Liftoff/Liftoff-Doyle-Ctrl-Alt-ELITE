package org.launchcode.BingeBuddy.dto;

import java.time.LocalDate;

public class WatchlistDTO {

    private Integer id;
    private String movieTitle;
    private String posterUrl;
    private LocalDate scheduledDate;
    private String status;


    public WatchlistDTO() {
    }

    public WatchlistDTO(Integer id, String movieTitle, String posterUrl, LocalDate scheduledDate, String status) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.posterUrl = posterUrl;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
