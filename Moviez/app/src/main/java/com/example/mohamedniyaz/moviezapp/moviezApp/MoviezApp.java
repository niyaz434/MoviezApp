package com.example.mohamedniyaz.moviezapp.moviezApp;

import android.app.Application;

import com.example.mohamedniyaz.moviezapp.executors.AppExecutor;
import com.example.mohamedniyaz.moviezapp.repository.MovieRepository;
import com.example.mohamedniyaz.moviezapp.roomDatabase.MovieDatabase;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MoviezApp extends Application {
    private final String TAG = "MoviezApp";
    private static MoviezApp moviezApp = null;
    private AppExecutor mAppExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
        mAppExecutor = new AppExecutor();
    }

    public static synchronized MoviezApp getInstance() {
        if (moviezApp == null) {
            return moviezApp = new MoviezApp();
        }
        return moviezApp;
    }


    public MovieDatabase getMovieDataBase() {
        return MovieDatabase.getMovieDatabase(this);
    }

    public MovieRepository getMovieRepository() { // need to pass mAppExecutor for background process
        return MovieRepository.getMovieRepository(getMovieDataBase(), mAppExecutor);
    }
}
