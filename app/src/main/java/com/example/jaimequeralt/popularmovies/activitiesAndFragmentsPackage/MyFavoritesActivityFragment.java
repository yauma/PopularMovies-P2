package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
    private ArrayList<byte[]> mListArrayBitmap;
    private MainActivityFragment.OnFragmentInteractionListenerMain mListener;

    public MyFavoritesActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListMoviesFromContentProvider();

        mListener = (MainActivityFragment.OnFragmentInteractionListenerMain) getActivity();
        if (MainActivity.mTwoPane) {

            mListener.refreshDetailFragment(moviesList.get(0));
        }

    }

    private void getListMoviesFromContentProvider() {
        String[] projection = new String[]{
                MoviesProvider.Movies._ID,
                MoviesProvider.Movies.COL_TITLE,
                MoviesProvider.Movies.COL_OVERVIEW,
                MoviesProvider.Movies.COL_POSTER,
                MoviesProvider.Movies.COL_RATING,
                MoviesProvider.Movies.COL_RELEASE,
                MoviesProvider.Movies.COL_IMAGE_DATA,
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
            byte[] byteArrayBitmap;
            moviesList = new ArrayList<>();
            mListImages = new ArrayList<>();
            mListArrayBitmap = new ArrayList<>();

            int colTitle = cur.getColumnIndex(MoviesProvider.Movies.COL_TITLE);
            int colOverview = cur.getColumnIndex(MoviesProvider.Movies.COL_OVERVIEW);
            int colPoster = cur.getColumnIndex(MoviesProvider.Movies.COL_POSTER);
            int colRelease = cur.getColumnIndex(MoviesProvider.Movies.COL_RELEASE);
            int colRating = cur.getColumnIndex(MoviesProvider.Movies.COL_RATING);
            int colImageData = cur.getColumnIndex(MoviesProvider.Movies.COL_IMAGE_DATA);


            do {
                title = cur.getString(colTitle);
                overview = cur.getString(colOverview);
                poster = cur.getString(colPoster);
                releaseDate = cur.getString(colRelease);
                rating = cur.getFloat(colRating);
                byteArrayBitmap = cur.getBlob(colImageData);

                Movie movie = new Movie(title, poster, overview, releaseDate, rating);
                moviesList.add(movie);
                mListImages.add(poster);
                mListArrayBitmap.add(byteArrayBitmap);
            } while (cur.moveToNext());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_favorites, container, false);

        gridview = (GridView) rootView.findViewById(R.id.gridviewFavorites);

        if (moviesList != null) {


        } else {
            Toast.makeText(getActivity(), "No favorite movies saved", Toast.LENGTH_LONG).show();
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (MainActivity.mTwoPane) {
                    mListener.refreshDetailFragment(moviesList.get(position));

                } else {
                    Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                    intent.putExtra("Movie", moviesList.get(position));
                    intent.putExtra("activity", "favorites");
                    startActivity(intent);
                }

            }
        });

        return rootView;
    }


}
