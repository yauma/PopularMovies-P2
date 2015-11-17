package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnFragmentInteractionListenerMain {


    private Bundle params;
    public static boolean mTwoPane;
    private ReviewListFragment reviewListFragment;
    private DetailMovieActivityFragment detailMovieActivityFragment;
    private boolean firstTimeload = true;
    private MainActivityFragment mainActivityFragment;
    public static boolean INTERNET_CONNECTION;


    public Bundle getParams() {
        return params;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        INTERNET_CONNECTION = isNetworkAvailable();
        if (INTERNET_CONNECTION == false){
            Toast.makeText(this,"No internet connection avalaible",Toast.LENGTH_LONG).show();
        }
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                setGridFragment();
            }
        } else {
            mTwoPane = false;
        }


    }

    private void setGridFragment() {
        mainActivityFragment = MainActivityFragment.getInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.list_container, mainActivityFragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_gridview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void refreshDetailFragment(Movie movie) {
        detailMovieActivityFragment = DetailMovieActivityFragment.newInstance(movie);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.detail_container, detailMovieActivityFragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }

    @Override
    public void showReviews(Movie movie) {
        reviewListFragment = ReviewListFragment.newInstance(movie);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.detail_container, reviewListFragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }

    @Override
    public void showDetails(Movie movie) {
        detailMovieActivityFragment = DetailMovieActivityFragment.newInstance(movie);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_container, detailMovieActivityFragment);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();

    }

    //Based on a stackoverflow snippet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
