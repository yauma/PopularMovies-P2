package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;


public class DetailMovieActivity extends ActionBarActivity implements ReviewListFragment.OnFragmentInteractionListener {

    private final int BACK_BUTTON = 16908332;
    private String activity;
    private ActionBar mActionBar;
    private FragmentTransaction transaction;
    private ReviewListFragment reviewListFragment;
    private DetailMovieActivityFragment detailMovieActivityFragment;
    FrameLayout frameLayoutDetail, frameLayoutReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail_movie);
        activity = getIntent().getStringExtra("activity");

        frameLayoutDetail = (FrameLayout)findViewById(R.id.detail_fragment);
        frameLayoutReviews = (FrameLayout) findViewById(R.id.reviews);

        showDetails();

        detailMovieActivityFragment = new DetailMovieActivityFragment();
        transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.add(R.id.detail_fragment, detailMovieActivityFragment);


// Commit the transaction
        transaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            if (activity.equals("main")) {
                Intent intent = new Intent(DetailMovieActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void showReadMore(Movie movie) {
        reviewListFragment = ReviewListFragment.newInstance(movie);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        frameLayoutDetail.setVisibility(View.INVISIBLE);
        frameLayoutReviews.setVisibility(View.VISIBLE);
        transaction.add(R.id.reviews, reviewListFragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }

    @Override
    public void showDetails() {

        frameLayoutDetail.setVisibility(View.VISIBLE);
        frameLayoutReviews.setVisibility(View.INVISIBLE);
    }


}
