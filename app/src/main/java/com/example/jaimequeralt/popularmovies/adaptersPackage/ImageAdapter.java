package com.example.jaimequeralt.popularmovies.adaptersPackage;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.jaimequeralt.popularmovies.databasePackage.DbBitmapUtility;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by jaimequeralt on 9/6/15.
 */
public class ImageAdapter extends BaseAdapter {

    private ArrayList<Movie> listMovies;
    private Context mContext;


    public ImageAdapter(Activity activity, ArrayList<Movie> listMovies) {
        mContext = activity;
        this.listMovies = listMovies;

    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
        }
        view.setAdjustViewBounds(true);

        if(listMovies.get(position).getImageByteArray() != null){
            Bitmap bitmap = DbBitmapUtility.getImage(listMovies.get(position).getImageByteArray());
            Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
            view.setImageDrawable(drawable);
        }
        else {
            String url = "http://image.tmdb.org/t/p/w342"+listMovies.get(position).getPoster_path();
            // Trigger the download of the URL asynchronously into the image view.
            Picasso.with(mContext)
                    .load(url)
                    .into(view);
        }

        return view;
    }



}


