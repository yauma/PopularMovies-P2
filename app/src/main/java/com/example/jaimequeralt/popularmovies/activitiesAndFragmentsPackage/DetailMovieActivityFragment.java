package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jaimequeralt.popularmovies.adaptersPackage.ReviewsAdapter;
import com.example.jaimequeralt.popularmovies.databasePackage.DbBitmapUtility;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;
import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.databasePackage.DbMovies;
import com.example.jaimequeralt.popularmovies.modelPackage.MySingleton;
import com.example.jaimequeralt.popularmovies.modelPackage.Review;
import com.example.jaimequeralt.popularmovies.modelPackage.VideoTrailer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailMovieActivityFragment extends Fragment {

    private Movie movie;
    private TextView textViewTitle, textViewDate, textViewOverview, textViewRating;
    private ImageView imageViewPoster, imageViewFavorite, imageViewPlayBack, imageViewPlayBack2;
    private RatingBar ratingBar;
    private String url;
    private JsonObjectRequest jsObjTrailerRequest;
    private ArrayList<VideoTrailer> mListVideoTrailers;
    private VideoTrailer videoTrailer;
    private int playBackButtonNumber;
    private JsonObjectRequest jsObjReviewRequest;
    private ArrayList<Review> mListVideoReviews;
    private LinearLayout linearLayoutTrailers, linearLayoutPlayButton2;
    private Review review;
    private ListView listViewReview;


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
        imageViewFavorite = (ImageView) rootview.findViewById(R.id.imageViewFavorite);
        ratingBar = (RatingBar) rootview.findViewById(R.id.ratingBar);
        imageViewPlayBack = (ImageView) rootview.findViewById(R.id.imageViewPlayBack);
        imageViewPlayBack2 = (ImageView) rootview.findViewById(R.id.imageViewPlayBack2);
        linearLayoutTrailers = (LinearLayout) rootview.findViewById(R.id.linearLayoutTrailers);
        linearLayoutPlayButton2 = (LinearLayout) rootview.findViewById(R.id.linearLayoutPlayButton2);
        listViewReview = (ListView) rootview.findViewById(R.id.listViewReview);

        linearLayoutTrailers.setVisibility(View.GONE);

        textViewTitle.setText(movie.getTitle());
        textViewDate.setText(movie.getReleaseDate());
        textViewOverview.setText(movie.getOverview());
        textViewRating.setText(String.valueOf(movie.getRating()) + "/10");
        ratingBar.setRating(movie.getRating() / (10 / ratingBar.getNumStars()));


        url = "http://image.tmdb.org/t/p/w342/" + movie.getPoster_path();

        Picasso.with(getActivity())
                .load(url)
                .into(imageViewPoster);

        String urlTrailers = buildUrlTrailers(movie.getId());
        String urlReviews = buildUrlReviews(movie.getId());
        fetchVideosTrailersfromWeb(urlTrailers);
        fetchReviewsfromWeb(urlReviews);

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picasso.with(getActivity())
                        .load(url)
                        .into(target);

            }
        });

        imageViewPlayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBackButtonNumber = 0;
                Uri videoUrl = generateUrlWithKey(playBackButtonNumber);
                startActivity(new Intent(Intent.ACTION_VIEW, videoUrl));
            }
        });

        imageViewPlayBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBackButtonNumber = 1;
                Uri videoUrl = generateUrlWithKey(playBackButtonNumber);
                startActivity(new Intent(Intent.ACTION_VIEW, videoUrl));
            }
        });


        return rootview;
    }


    private Uri generateUrlWithKey(int i) {
        VideoTrailer videoTrailer = movie.getVideoTrailerList().get(i);
        return Uri.parse("https://www.youtube.com/watch?v=" + videoTrailer.getKey());
    }

    private void fetchVideosTrailersfromWeb(String url) {

        jsObjTrailerRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonTrailerObject(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjTrailerRequest);
    }

    private void fetchReviewsfromWeb(String urlReviews) {
        jsObjReviewRequest = new JsonObjectRequest
                (Request.Method.GET, urlReviews, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonReviewObject(response);
                        if(movie.getReviews() == null){
                            listViewReview.setVisibility(View.GONE);
                        }
                        else{
                            ReviewsAdapter adapter = new ReviewsAdapter(getActivity(), movie.getReviews());
                            listViewReview.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjReviewRequest);
    }


    private void parseJsonTrailerObject(JSONObject response) {
        mListVideoTrailers = new ArrayList<>();
        try {
            JSONArray results = response.getJSONArray("results");
            if (results.length() >= 1) {
                linearLayoutTrailers.setVisibility(View.VISIBLE);
                if (results.length() == 1) {
                    linearLayoutPlayButton2.setVisibility(View.GONE);
                }
                for (int i = 0; i < results.length(); i++) {

                    JSONObject posterObj = results.getJSONObject(i);
                    String key = posterObj.getString("key");
                    String name = posterObj.getString("name");
                    videoTrailer = new VideoTrailer(key, name);
                    mListVideoTrailers.add(videoTrailer);
                    movie.setVideoTrailerList(mListVideoTrailers);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void parseJsonReviewObject(JSONObject response) {
        mListVideoReviews = new ArrayList<>();
        try {
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject posterObj = results.getJSONObject(i);
                String author = posterObj.getString("author");
                String content = posterObj.getString("content");
                review = new Review(author, content);
                mListVideoReviews.add(review);
                movie.setReviews(mListVideoReviews);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String buildUrlTrailers(int id) {
        final String POPULAR_MOVIES_BASE_URL =
                "http://api.themoviedb.org/3/movie/" + id + "/videos" + "?";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MainActivityFragment.API_KEY)
                .build();

        return builtUri.toString();
    }

    private String buildUrlReviews(int id) {
        final String POPULAR_MOVIES_BASE_URL =
                "http://api.themoviedb.org/3/movie/" + id + "/reviews" + "?";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MainActivityFragment.API_KEY)
                .build();

        return builtUri.toString();
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            byte[] byteArrayBitmap = DbBitmapUtility.getBytes(bitmap);
            DbMovies.getInstance().insertMovie(getActivity(), movie,byteArrayBitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

}
