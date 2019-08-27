package com.example.mohamedniyaz.moviezapp.roomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohamedniyaz.moviezapp.dao.MovieDao;
import com.example.mohamedniyaz.moviezapp.entity.MovieEntity;

@Database(entities = MovieEntity.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "FavouriteMoviesDb";
    private static MovieDatabase movieDatabase;

    public static synchronized MovieDatabase getMovieDatabase(Context mContext) {
        if (movieDatabase == null) {
            movieDatabase = MovieDatabase.buildMovieDatabase(mContext);
        }
        return movieDatabase;
    }

    private static MovieDatabase buildMovieDatabase(Context mContext) {
        return Room.databaseBuilder(mContext, MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract MovieDao movieDao();

}
