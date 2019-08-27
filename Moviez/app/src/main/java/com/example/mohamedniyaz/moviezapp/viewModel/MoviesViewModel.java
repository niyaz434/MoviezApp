package com.example.mohamedniyaz.moviezapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mohamedniyaz.moviezapp.dao.MovieDao;
import com.example.mohamedniyaz.moviezapp.modules.Movie;
import com.example.mohamedniyaz.moviezapp.moviezApp.MoviezApp;
import com.example.mohamedniyaz.moviezapp.repository.MovieRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private MovieDao movieDao;
    private LiveData<List<Movie>> mMovieArrayList;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        movieRepository = ((MoviezApp) application).getMovieRepository();
        mMovieArrayList = movieRepository.getMovieList();
    }

    public void fetchRecentMoviesApi(int page) {
        movieRepository.loadMovieList(page);
    }

    public LiveData<List<Movie>> getMovieArrayList() {
        if (mMovieArrayList == null) {
            mMovieArrayList = movieRepository.getMovieList();
        }
        return mMovieArrayList;
    }
}
