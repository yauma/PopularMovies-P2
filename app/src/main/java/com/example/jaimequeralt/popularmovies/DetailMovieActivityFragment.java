package com.example.jaimequeralt.popularmovies;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private Movie movie;
    private TextView textViewTitle, textViewDate, textViewOverview, textViewRating;
    private ImageView imageViewPoster;
    private RatingBar ratingBar;

    public DetailMovieActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object ObjectMovie = getActivity().getIntent().getExtras().getParcelable("Movie");
        movie = (Movie) ObjectMovie;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        textViewDate = (TextView) rootview.findViewById(R.id.textViewDate);
        textViewOverview = (TextView) rootview.findViewById(R.id.textViewOverview);
        textViewTitle = (TextView) rootview.findViewById(R.id.textViewTitle);
        textViewRating = (TextView) rootview.findViewById(R.id.textViewRating);

        imageViewPoster = (ImageView) rootview.findViewById(R.id.imageViewPoster);
        ratingBar = (RatingBar) rootview.findViewById(R.id.ratingBar);


        textViewTitle.setText(movie.getTitle());
        textViewDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());
        textViewRating.setText( String.valueOf(movie.getRating())+"/10");
        ratingBar.setRating(movie.getRating()/(10/ratingBar.getNumStars()));

        String url = "http://image.tmdb.org/t/p/w342/" + movie.getPoster_path();

        Picasso.with(getActivity())
                .load(url)
                .into(imageViewPoster);

        return rootview;
    }


}
