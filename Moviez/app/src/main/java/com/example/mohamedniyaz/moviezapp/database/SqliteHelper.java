package com.example.mohamedniyaz.moviezapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SqliteHelper extends SQLiteOpenHelper {

    //Database name and version details
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME =  "MovieDatabase.db";

    //Table details
    private static final String TABLE_NAME = "FAVOURITE_MOVIES";
    private static final String COLUMN_MOVIE_ID = "MOVIE_ID";
    private static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    private static final String COLUMN_MOVIE_ISFAVOURITE = "IS_FAVOURITE";



    public SqliteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    final String movieSqliteDatabase  = "CREATE TABLE " + TABLE_NAME + " ( "
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

    public void insert(int movie_id, String movie_name, boolean isFavourite){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_MOVIE_ID,movie_id);
            contentValues.put(COLUMN_MOVIE_NAME,movie_name);
            contentValues.put(COLUMN_MOVIE_ISFAVOURITE,isTrue(isFavourite));

            sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void update( int movie_id, boolean isFavourite){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_MOVIE_ISFAVOURITE,isTrue(isFavourite));
            sqLiteDatabase.update(TABLE_NAME,contentValues,COLUMN_MOVIE_ID+"=" + movie_id,null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int isTrue (boolean isFavourite){
        return isFavourite ? 1 : 0 ;
    }

    public boolean data(int movie_id){
        int yesMovieID = 0 ;
        boolean yesItis = false;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_MOVIE_ID + " = " + movie_id,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    yesMovieID = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
        }
        if (yesMovieID == movie_id){
            yesItis = true;
        }else
            yesItis = false;
        return yesItis;
    }


    public boolean itsFavourite(int movie_id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int isTrue = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME+ " WHERE " + COLUMN_MOVIE_ID + "=" + movie_id,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    isTrue =  cursor.getInt(2);
                }while (cursor.moveToNext());
            }
        }
        if (isTrue == 1){
            return true;
        }else {
            return false;
        }

    }
}
