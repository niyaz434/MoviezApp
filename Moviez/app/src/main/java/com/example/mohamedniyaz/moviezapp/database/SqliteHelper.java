package com.example.mohamedniyaz.moviezapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mohamedniyaz.moviezapp.interfaces.HandlerResultListener;
import com.example.mohamedniyaz.moviezapp.moviezApp.ConstantMethods;

import java.util.ArrayList;


public class SqliteHelper extends SQLiteOpenHelper {

    //Database name and version details
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "MovieDatabase.db";
    //Table details
    private static final String TABLE_NAME = "FAVOURITE_MOVIES";
    private static final String COLUMN_MOVIE_ID = "MOVIE_ID";
    private static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    private static final String COLUMN_MOVIE_ISFAVOURITE = "IS_FAVOURITE";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String movieSqliteDatabase = "CREATE TABLE " + TABLE_NAME + " ( "
                + COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_MOVIE_NAME + " TEXT, "
                + COLUMN_MOVIE_ISFAVOURITE + " INTEGER DEFAULT 0 "
                + " ) ";

        db.execSQL(movieSqliteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void insert(int movie_id, String movie_name, boolean isFavourite) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_MOVIE_ID, movie_id);
            contentValues.put(COLUMN_MOVIE_NAME, movie_name);
            contentValues.put(COLUMN_MOVIE_ISFAVOURITE, isTrue(isFavourite));

            sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(int movie_id, boolean isFavourite) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_MOVIE_ISFAVOURITE, isTrue(isFavourite));
            sqLiteDatabase.updateWithOnConflict(TABLE_NAME, contentValues, COLUMN_MOVIE_ID + "=" + movie_id, null,SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int isTrue(boolean isFavourite) {
        return isFavourite ? 1 : 0;
    }

    public boolean data(final int movie_id) {
        final int[] yesMovieID = {0};
        final boolean[] yesItis = {false};
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + " = " + movie_id, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            yesMovieID[0] = cursor.getInt(0);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (yesMovieID[0] == movie_id) {
                    yesItis[0] = true;
                } else
                    yesItis[0] = false;
            }
        });
        return yesItis[0];
    }

    //implement interface for the data insert
    public void data(final HandlerResultListener handlerResultListener, final int movieId) {
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                int yesMovieId = 0;
                boolean yesItIs;
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + " = " + movieId, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            yesMovieId = cursor.getInt(0);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (yesMovieId == movieId) {
                    yesItIs = true;
                } else {
                    yesItIs = false;
                }
                handlerResultListener.onResult(yesItIs);
            }
        });
    }

    //TODO proper naming convention, Background thread, Cursor open should always be close
    public boolean isMovieFavourite(final int movie_id) {
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        final int isTrue[] = {1};
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + "=" + movie_id, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            isTrue[0] = cursor.getInt(2);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
        return isTrue[0] == 1;
    }

    //implement interface for the ui thread update
    public void isMovieFavouriteByHandler(final HandlerResultListener handlerResultListener, final int movie_id) {
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                int isTrue;
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + "=" + movie_id, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            isTrue = cursor.getInt(2);
                            handlerResultListener.onResult(isTrue == 1);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
    }

    //changes for running a sqlite query
    public ArrayList<Integer> getFavouriteMovies() {
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        final ArrayList<Integer> movieIdFavourite = new ArrayList<>();
        final int[] movieId = new int[1];
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ISFAVOURITE + "=" + "0", null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            movieId[0] = cursor.getInt(0);
                            movieIdFavourite.add(movieId[0]);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        });
        return movieIdFavourite;
    }

    public void getFavouriteMovie(final HandlerResultListener handlerResultListener) {
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ConstantMethods.newInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> movieIdFavourite = new ArrayList<>();
                int movieId;
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ISFAVOURITE + "=" + "1", null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            movieId = cursor.getInt(0);
                            movieIdFavourite.add(movieId);
                        } while (cursor.moveToNext());
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                handlerResultListener.onResult(movieIdFavourite);
            }
        });
    }
}
