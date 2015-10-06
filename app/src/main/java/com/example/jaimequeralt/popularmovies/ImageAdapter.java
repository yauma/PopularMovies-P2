package com.example.jaimequeralt.popularmovies;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by jaimequeralt on 9/6/15.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> listPostersPaths;

    public ImageAdapter(Context c, ArrayList<String> listPostersPaths) {
        mContext = c;
        this.listPostersPaths = listPostersPaths;
    }

    @Override
    public int getCount() {
        return listPostersPaths.size();
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
        String url = "http://image.tmdb.org/t/p/w342/"+listPostersPaths.get(position);
        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext)
                .load(url)
                .into(view);

        return view;
    }
}


