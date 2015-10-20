package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.adaptersPackage.ImageAdapter;
import com.example.jaimequeralt.popularmovies.contentProviderPackage.MoviesProvider;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;

import java.text.BreakIterator;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyFavoritesActivityFragment extends Fragment {

    private ArrayList<Movie> moviesList;
    private ArrayList<String> mListImages;
    private GridView gridview;
    private ImageAdapter imageAdapter;

    public MyFavoritesActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListMoviesFromContentProvider();
    }

    private void getListMoviesFromContentProvider() {
        String[] projection = new String[]{
                MoviesProvider.Movies._ID,
                MoviesProvider.Movies.COL_TITLE,
                MoviesProvider.Movies.COL_OVERVIEW,
                MoviesProvider.Movies.COL_POSTER,
                MoviesProvider.Movies.COL_RATING,
                MoviesProvider.Movies.COL_RELEASE,
        };

        Uri moviesUri = MoviesProvider.CONTENT_URI;

        ContentResolver cr = getActivity().getContentResolver();

        //We perform query
        Cursor cur = cr.query(moviesUri,
                projection, //Columns
                null,       //where clause query
                null,       //Selection Argumentos query
                null);

        if (cur.moveToFirst()) {
            String title;
            String overview;
            String poster;
            String releaseDate;
            float rating;
            moviesList = new ArrayList<>();
            mListImages = new ArrayList<>();

            int colTitle = cur.getColumnIndex(MoviesProvider.Movies.COL_TITLE);
            int colOverview = cur.getColumnIndex(MoviesProvider.Movies.COL_OVERVIEW);
            int colPoster = cur.getColumnIndex(MoviesProvider.Movies.COL_POSTER);
            int colRelease = cur.getColumnIndex(MoviesProvider.Movies.COL_RELEASE);
            int colRating = cur.getColumnIndex(MoviesProvider.Movies.COL_RATING);


            do {
                title = cur.getString(colTitle);
                overview = cur.getString(colOverview);
                poster = cur.getString(colPoster);
                releaseDate = cur.getString(colRelease);
                rating = cur.getFloat(colRating);

                Movie movie = new Movie(title, poster, overview, releaseDate, rating);
                moviesList.add(movie);
                mListImages.add(poster);
            } while (cur.moveToNext());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_favorites, container, false);

        gridview = (GridView) rootView.findViewById(R.id.gridviewFavorites);

        if (moviesList != null) {
            imageAdapter = new ImageAdapter(getActivity(), mListImages);
            gridview.setAdapter(imageAdapter);

        } else {
            Toast.makeText(getActivity(),"No favorite movies saved",Toast.LENGTH_LONG).show();
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("Movie", moviesList.get(position));
                intent.putExtra("activity", "favorites");
                startActivity(intent);
            }
        });

        return rootView;
    }
}
