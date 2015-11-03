package com.example.jaimequeralt.popularmovies.databasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.jaimequeralt.popularmovies.modelPackage.Movie;

import java.util.ArrayList;

/**
 * Created by jaimequeralt on 10/12/15.
 */
public class DbMovies {

    private static DbMovies dbMovies;
    private DbHelper dbHelper;
    SQLiteDatabase db;
    private ArrayList<Movie> listMovies;

    public DbMovies() {
    }

    public static DbMovies getInstance() {
        if (dbMovies == null) {
            dbMovies = new DbMovies();
            return dbMovies;
        }
        return dbMovies;
    }

    public void insertMovie(Context context, Movie movie, byte[] byteArrayBitmap) {
        dbHelper = new DbHelper(context, "MPM", null, 1);
        db = dbHelper.getWritableDatabase();
        ContentValues newMovie = new ContentValues();

        newMovie.put("movie_id", movie.getId());
        newMovie.put("title", movie.getTitle());
        newMovie.put("poster_path", movie.getPoster_path());
        newMovie.put("overview", movie.getOverview());
        newMovie.put("releaseDate", movie.getReleaseDate());
        newMovie.put("rating", movie.getRating());
        newMovie.put("image_data", byteArrayBitmap);

        try {
            db.insertOrThrow("movies", null, newMovie);
            Toast.makeText(context, "Movie Saved", Toast.LENGTH_LONG).show();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(context, "Movie ALREADY Saved", Toast.LENGTH_LONG).show();
        }
    }

}

