package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;


import android.app.Activity;
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
import android.widget.Toast;

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
    public static final String API_KEY = "9bc3a7bc8d59c59f5ce6afa05f9a3d60";
    private String filter;
    private JsonObjectRequest jsObjRequest;
    private ActionBar mActionBar;
    private Movie movie;
    private ArrayList<Movie> listMovies;
    private String url;
    private int itemPosition = 0, moviePosition = 0;
    private OnFragmentInteractionListenerMain mListener;
    private MovieModel movieModel;
    private static MainActivityFragment mainActivityFragment;

    public MainActivityFragment() {
    }

    public static MainActivityFragment getInstance() {
        if (mainActivityFragment == null) {
            mainActivityFragment = new MainActivityFragment();
            return mainActivityFragment;
        }
        return mainActivityFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchDataSharedPreferences();

        if(MainActivity.INTERNET_CONNECTION == false){
            filter = "favorites";
        }

        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            listMovies = (ArrayList<Movie>) savedInstanceState.get("listMovies");
            itemPosition = savedInstanceState.getInt("itemPosition");
            moviePosition = savedInstanceState.getInt("moviePosition");
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (filter.equals("popular")) {
            mActionBar.setTitle("Most Popular Movies");
        } else if (filter.equals("top_rated")) {
            mActionBar.setTitle("Top Rated Movies");
        } else if (filter.equals("favorites")) {
            mActionBar.setTitle("Favorites Movies");
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
            if (filter != "popular") {
                filter = "popular";
                mActionBar.setTitle("Most Popular Movies");
                itemPosition = 0;
                moviePosition = 0;
                url = buildUrl(filter);
                loadGridViewFromAPI(url);


            }
        }
        if (id == R.id.top_rated) {
            if (filter != "top_rated") {
                filter = "top_rated";
                mActionBar.setTitle("Top Rated Movies");
                itemPosition = 0;
                moviePosition = 0;
                url = buildUrl(filter);
                loadGridViewFromAPI(url);

            }
        }

        if (id == R.id.favorites) {
            if (filter != "favorites") {

                loadGridViewFromDb();

            }


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gridview, container, false);


        gridview = (GridView) rootView.findViewById(R.id.gridview);

        mListener = (OnFragmentInteractionListenerMain) getActivity();

        if (listMovies != null) {
            imageAdapter = new ImageAdapter(getActivity(), listMovies);
            gridview.setAdapter(imageAdapter);
            gridview.setSelection(itemPosition);
            if (MainActivity.mTwoPane) {
                mListener.refreshDetailFragment(listMovies.get(moviePosition));
            }
        } else {
            if (filter.equals("favorites")) {
                loadGridViewFromDb();
            } else {
                url = buildUrl(filter);
                loadGridViewFromAPI(url);
            }

        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                moviePosition = position;
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
                        listMovies = parseJsonObject(response);
                        imageAdapter = new ImageAdapter(getActivity(), listMovies);
                        gridview.setAdapter(imageAdapter);
                        gridview.setSelection(itemPosition);
                        if (MainActivity.mTwoPane) {

                            mListener.refreshDetailFragment(listMovies.get(moviePosition));
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
        ArrayList<Movie> listMoviesDb = movieModel.getListMoviesFromDb(getActivity());
        if (listMoviesDb == null) {
            Toast.makeText(getActivity(), "No Movies Saved", Toast.LENGTH_LONG).show();
        } else {

            listMovies = listMoviesDb;
            imageAdapter = new ImageAdapter(getActivity(), listMovies);
            gridview.setAdapter(imageAdapter);
            gridview.setSelection(itemPosition);
            filter = "favorites";
            if(mActionBar != null){
                mActionBar.setTitle("Favorites Movies");
            }
            itemPosition = 0;
            moviePosition = 0;
            if (MainActivity.mTwoPane) {
                mListener.refreshDetailFragment(listMovies.get(moviePosition));
            }

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

    private ArrayList<Movie> parseJsonObject(JSONObject response) {
        movie = new Movie();
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listMovies;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listMovies", listMovies);
        outState.putString("filter", filter);
        itemPosition = gridview.getFirstVisiblePosition();
        outState.putInt("itemPosition", itemPosition);
        outState.putInt("moviePosition", moviePosition);
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

    }
}