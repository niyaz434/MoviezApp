package com.example.mohamedniyaz.moviezapp.modules;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieId {
    @SerializedName("original_title")
    private
    String original_title;

    @SerializedName("overview")
    private
    String overview;

    @SerializedName("vote_average")
    private
    float vote_average;

    @SerializedName("vote_count")
    private
    int vote_count;

    @SerializedName("genres")
    private List<GenereClass> genres;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("spoken_languages")
    private List<SpokenClass> spoken_languages;


    public MovieId(String original_title, String overview, float vote_average, int vote_count, List<GenereClass> genres, String backdropPath, List<SpokenClass> spoken_languages) {
        this.original_title = original_title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genres = genres;
        this.backdropPath = backdropPath;
        this.spoken_languages = spoken_languages;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public List<GenereClass> getGenres() {
        return genres;
    }

    public void setGenres(List<GenereClass> genres) {
        this.genres = genres;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<SpokenClass> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(List<SpokenClass> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }
}
