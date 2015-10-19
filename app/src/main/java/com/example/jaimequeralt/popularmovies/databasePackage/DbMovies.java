package com.example.jaimequeralt.popularmovies.databasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaimequeralt.popularmovies.Movie;

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

    public void insertMovie(Context context, Movie movie) {
        dbHelper = new DbHelper(context, "MPM", null, 1);
        db = dbHelper.getWritableDatabase();
        ContentValues newMovie = new ContentValues();

        newMovie.put("title", movie.getTitle());
        newMovie.put("poster_path", movie.getPoster_path());
        newMovie.put("overview", movie.getOverview());
        newMovie.put("releaseDate", movie.getReleaseDate());
        newMovie.put("rating", movie.getRating());
        try {
                db.insert("movies", null, newMovie);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }

    }

