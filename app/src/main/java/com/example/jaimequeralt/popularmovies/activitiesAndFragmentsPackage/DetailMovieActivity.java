package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jaimequeralt.popularmovies.R;


public class DetailMovieActivity extends ActionBarActivity {

    private final int BACK_BUTTON = 16908332;
    private String activity;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail_movie);
        activity = getIntent().getStringExtra("activity");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_movie, menu);
        return true;
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
            } else {
                Intent intent = new Intent(DetailMovieActivity.this, MyFavoritesActivity.class);
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }

}
