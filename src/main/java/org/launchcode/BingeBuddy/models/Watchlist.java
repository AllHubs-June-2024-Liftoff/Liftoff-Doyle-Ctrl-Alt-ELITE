package org.launchcode.BingeBuddy.models;

import jakarta.persistence.*;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Watchlist extends AbstractEntity {


    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Enumerated(EnumType.STRING) // Ensures the enum is stored as a string in the database
    @Column(nullable = false)
    private WatchlistStatus status;


    public Watchlist() {
    }

    public Watchlist(Movie movie, WatchlistStatus status) {

        this.movie = movie;
        this.status = status;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public WatchlistStatus getStatus() {
        return status;
    }

    public void setStatus(WatchlistStatus status) {
        this.status = status;
    }

}
