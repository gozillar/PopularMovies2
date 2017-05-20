package com.example.relearn.popularmovies2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.relearn.popularmovies2.model.FavMovie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FavoriteMoviesDB.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " +
                FavoriteMoviesContract.FavoriteMovies.TABLE_NAME + " (" +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_ID + " INTEGER PRIMARY KEY," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_TITLE + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_MOVIE_PATH + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_OVERVIEW + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_REL_DATE + " TEXT," +
                FavoriteMoviesContract.FavoriteMovies.COLUMN_RATING + " TEXT)";
        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMovies.TABLE_NAME);
        onCreate(db);
    }

    public void insertIntoDB(int id, String title, String moviePath, String overview, String relDate, double rating){
        Log.d("insert", "before insert");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_ID, id);
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_TITLE, title);
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_MOVIE_PATH, moviePath);
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_OVERVIEW, overview);
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_REL_DATE, relDate);
        cv.put(FavoriteMoviesContract.FavoriteMovies.COLUMN_RATING, rating);

        db.insert(FavoriteMoviesContract.FavoriteMovies.TABLE_NAME, null, cv);

        db.close();

        Log.i("insert into DB", "After insert");

    }

    public List<FavMovie> getDataFromDB() {
        List<FavMovie> modelList = new ArrayList<FavMovie>();
        String query = "select * from " + FavoriteMoviesContract.FavoriteMovies.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                FavMovie favMovie = new FavMovie();

                favMovie.setOriginal_title(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_TITLE)));
                favMovie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_MOVIE_PATH)));
                favMovie.setId(cursor.getInt(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_TITLE)));
                favMovie.setRelease_date(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_REL_DATE)));
                favMovie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_OVERVIEW)));
                favMovie.setVote_average(cursor.getDouble(cursor.getColumnIndex(FavoriteMoviesContract.FavoriteMovies.COLUMN_RATING)));

                modelList.add(favMovie);
            } while (cursor.moveToNext());
        }
        Log.d("Movie data", modelList.toString());

        return modelList;
    }

    public void removeMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteMoviesContract.FavoriteMovies.TABLE_NAME, "id" + " = ?", new String[] { Integer.toString(id) });
        db.close();
    }

}
