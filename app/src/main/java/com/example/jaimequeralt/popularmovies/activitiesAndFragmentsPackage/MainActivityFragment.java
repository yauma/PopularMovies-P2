package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.jaimequeralt.popularmovies.adaptersPackage.ImageAdapter;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;
import com.example.jaimequeralt.popularmovies.modelPackage.MovieModel;
import com.example.jaimequeralt.popularmovies.modelPackage.MySingleton;
import com.example.jaimequeralt.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private GridView gridview;
    private ImageAdapter imageAdapter;
    private ArrayList<String> mListImages;
    public static final String API_KEY = "9bc3a7bc8d59c59f5ce6afa05f9a3d60";
    private String filter;
    private JsonObjectRequest jsObjRequest;
    private ActionBar mActionBar;
    private Movie movie;
    private ArrayList<Movie> listMovies;
    private String url;
    private int itemPosition = 0;
    private DetailMovieActivityFragment detailMovieActivityFragment;
    private OnFragmentInteractionListenerMain mListener;
    private MovieModel movieModel;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchDataSharedPreferences();

        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            mListImages = (ArrayList<String>) savedInstanceState.get("listImages");
            listMovies = (ArrayList<Movie>) savedInstanceState.get("listMovies");
            itemPosition = savedInstanceState.getInt("itemPosition");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Context c = getActivity();
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (filter.equals("popular")) {
            mActionBar.setTitle("Most Popular Movies");
        } else {
            mActionBar.setTitle("Top Rated Movies");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.most_polular) {
            if (filter.equals("top_rated")) {
                filter = "popular";
                url = buildUrl(filter);
                loadGridViewFromAPI(url);
                mActionBar.setTitle("Most Popular Movies");
                itemPosition = 0;

            }
        }
        if (id == R.id.top_rated) {
            if (filter.equals("popular")) {
                filter = "top_rated";
                url = buildUrl(filter);
                loadGridViewFromAPI(url);
                mActionBar.setTitle("Top Rated Movies");
                itemPosition = 0;
            }
        }

        if (id == R.id.favorites) {
            loadGridViewFromDb();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gridview, container, false);


        gridview = (GridView) rootView.findViewById(R.id.gridview);

        mListener = (OnFragmentInteractionListenerMain) getActivity();

        if (mListImages != null) {
            imageAdapter = new ImageAdapter(getActivity(), mListImages);
            gridview.setAdapter(imageAdapter);
            gridview.setSelection(itemPosition);
        } else {
            url = buildUrl(filter);
            loadGridViewFromAPI(url);
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (MainActivity.mTwoPane) {
                    mListener.refreshDetailFragment(listMovies.get(position));
                } else {
                    Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                    intent.putExtra("Movie", listMovies.get(position));
                    intent.putExtra("activity", "main");
                    startActivity(intent);
                }
            }
        });


        return rootView;
    }

    private void loadGridViewFromAPI(String url) {


        jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mListImages = parseJsonObject(response);
                        imageAdapter = new ImageAdapter(getActivity(), mListImages);
                        gridview.setAdapter(imageAdapter);
                        gridview.setSelection(itemPosition);
                        if (MainActivity.mTwoPane) {

                            mListener.refreshDetailFragment(listMovies.get(0));
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }

    private void loadGridViewFromDb() {
        movieModel = MovieModel.getInstance();
        listMovies =movieModel.getListMoviesFromDb(getActivity());
        imageAdapter = new ImageAdapter(getActivity(), listMovies);
        gridview.setAdapter(imageAdapter);
        gridview.setSelection(itemPosition);
        if (MainActivity.mTwoPane) {

            mListener.refreshDetailFragment(listMovies.get(0));
        }
    }


    private String buildUrl(String filter) {
        final String POPULAR_MOVIES_BASE_URL =
                "http://api.themoviedb.org/3/movie/" + filter + "?";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        return builtUri.toString();
    }

    private ArrayList<String> parseJsonObject(JSONObject response) {
        movie = new Movie();
        mListImages = new ArrayList<>();
        listMovies = new ArrayList<>();
        try {
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject posterObj = results.getJSONObject(i);
                int id = posterObj.getInt("id");
                String originalTitle = posterObj.getString("original_title");
                String posterPath = posterObj.getString("poster_path");
                String overview = posterObj.getString("overview");
                String releaseDate = posterObj.getString("release_date");
                float average = Float.parseFloat(posterObj.getString("vote_average"));
                movie = new Movie(id, originalTitle, posterPath, overview, releaseDate, average);
                listMovies.add(movie);
                mListImages.add(posterPath);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mListImages;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("listImages", mListImages);
        outState.putParcelableArrayList("listMovies", listMovies);
        outState.putString("filter", filter);
        itemPosition = gridview.getFirstVisiblePosition();
        outState.putInt("itemPosition", itemPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (jsObjRequest != null) {
            jsObjRequest.cancel();
        }
        //Saving filter to SharedPreferneces
        SaveDataSharedPreferences();
    }

    private void SaveDataSharedPreferences() {
        SharedPreferences data = getActivity().getSharedPreferences("items", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.putString("filter", filter);
        //editor.putInt("itemPosition", gridview.getFirstVisiblePosition());
        editor.commit();
    }

    private void fetchDataSharedPreferences() {
        SharedPreferences data = getActivity().getSharedPreferences("items", getActivity().MODE_PRIVATE);
        filter = data.getString("filter", new String());
        //itemPosition = data.getInt("itemPosition", 0);
        if (filter.isEmpty()) {
            filter = "popular";
        }
    }

    public interface OnFragmentInteractionListenerMain {

        void refreshDetailFragment(Movie movie);

        void showReviews(Movie movie);

        void showDetails(Movie movie);

        void showFavorites();
    }
}