package com.example.jaimequeralt.popularmovies.adaptersPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.modelPackage.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by jaimequeralt on 11/2/15.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {

    private ArrayList<Review> reviewArrayList;
    private Context context;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewArrayList) {
        super(context, R.layout.list_view_reviews, reviewArrayList);

        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_reviews, parent, false);

        TextView textViewAuthor = (TextView) rowView.findViewById(R.id.textViewAuthor);
        TextView textViewReview = (TextView) rowView.findViewById(R.id.textViewReview);

        textViewAuthor.setText(reviewArrayList.get(position).getAuthor());
        textViewReview.setText(reviewArrayList.get(position).getReview());

        return rowView;
    }
}
