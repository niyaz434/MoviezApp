package com.example.mohamedniyaz.moviezapp.rest;


import com.example.mohamedniyaz.moviezapp.modules.MovieId;
import com.example.mohamedniyaz.moviezapp.modules.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("discover/movie")
    Call<MovieResponse>getResults(@Query("api_key") String apikey);

    @GET("movie/{id}")
    Call<MovieId> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


}


