package com.example.mohamedniyaz.moviezapp.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.mohamedniyaz.moviezapp.dao.MovieDao;
import com.example.mohamedniyaz.moviezapp.executors.AppExecutor;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.example.mohamedniyaz.moviezapp.modules.MovieResponse;
import com.example.mohamedniyaz.moviezapp.moviezApp.AppConstants;
import com.example.mohamedniyaz.moviezapp.rest.ApiClient;
import com.example.mohamedniyaz.moviezapp.rest.ApiInterface;
import com.example.mohamedniyaz.moviezapp.roomDatabase.MovieDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private String TAG = "MovieRepository";
    private static MovieDao movieDao;
    private static MovieRepository mMovieRepository;
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();
    private AppExecutor appExecutor;

    public MovieRepository(MovieDatabase movieDatabase, AppExecutor mAppExecutor) {
        movieDao = movieDatabase.movieDao();
        appExecutor = mAppExecutor;
    }

    public static synchronized MovieRepository getMovieRepository(MovieDatabase movieDatabase, AppExecutor mAppExecutor) {
        if (mMovieRepository == null) {
            mMovieRepository = new MovieRepository(movieDatabase, mAppExecutor);
        }
        return mMovieRepository;
    }

    public void loadMovieList(final int page) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        appExecutor.getAppExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Call<MovieResponse> call = apiService.getResults(AppConstants.API_KEY, page);
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        //int statusCode = response.code();
                        movieList.postValue(response.body().getResults());

                        Log.d(TAG, "onResponse: " + page);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: ");
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });

    }

    public MutableLiveData<List<Movie>> getMovieList() {
        return movieList;
    }
}
