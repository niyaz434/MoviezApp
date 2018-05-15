package com.example.mohamedniyaz.moviezapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SqliteHelper extends SQLiteOpenHelper {

    //Database name and version details
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME =  "MovieDatabase.db";

    //Table details
    private static final String TABLE_NAME = "FAVURITE MOVIES";
    private static final String COLUMN_MOVIE_ID = "MOVIE_ID";
    private static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    private static final String COLUMN_MOVIE_ISFAVOURITE = "IS_FAVOURITE";



    public SqliteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    final String movieSqliteDatabase  = "CREATE TABLE " + TABLE_NAME + " ( "
                                            + COLUMN_MOVIE_ID + "INTEGER, "
                                            + COLUMN_MOVIE_NAME + "TEXT, "
                                            + COLUMN_MOVIE_ISFAVOURITE + "INTEGER DEFAULT 0 "
                                            + ") ";

        db.execSQL(movieSqliteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }




}
