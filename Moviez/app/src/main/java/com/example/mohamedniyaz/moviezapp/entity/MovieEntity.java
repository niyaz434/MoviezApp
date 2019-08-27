package com.example.mohamedniyaz.moviezapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favouriteMovieList")
public class MovieEntity {

    @PrimaryKey
    private int movieId;
    private String movieName;
    private boolean favouriteMovie;

    public MovieEntity(int movieId, String movieName, boolean favouriteMovie) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.favouriteMovie = favouriteMovie;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public boolean isFavouriteMovie() {
        return favouriteMovie;
    }

    public void setFavouriteMovie(boolean favouriteMovie) {
        this.favouriteMovie = favouriteMovie;
    }
}
