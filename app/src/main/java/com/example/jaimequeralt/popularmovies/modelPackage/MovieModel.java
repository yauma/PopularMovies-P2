package com.example.jaimequeralt.popularmovies.modelPackage;

import android.app.Activity;

import com.example.jaimequeralt.popularmovies.contentProviderPackage.MoviesProvider;

import java.util.ArrayList;

/**
 * Created by jaimequeralt on 15/11/15.
 */
public class MovieModel {
    private static MovieModel movieModel;
    private Movie movie;
    private ArrayList<Movie> listMovies;

    public static MovieModel getInstance() {
        if (movieModel == null) {
            movieModel = new MovieModel();
            return movieModel;
        } else {
            return movieModel;
        }
    }


    public ArrayList<Movie> getListMoviesFromDb(Activity activity) {
        return listMovies = MoviesProvider.getListMoviesFromContentProvider(activity);
    }
}
