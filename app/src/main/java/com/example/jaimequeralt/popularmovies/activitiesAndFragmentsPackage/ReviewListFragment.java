package com.example.jaimequeralt.popularmovies.activitiesAndFragmentsPackage;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.jaimequeralt.popularmovies.R;
import com.example.jaimequeralt.popularmovies.adaptersPackage.ReviewsAdapter;
import com.example.jaimequeralt.popularmovies.modelPackage.Movie;
import com.example.jaimequeralt.popularmovies.modelPackage.Review;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "reviewsList";
    private static final String ARG_PARAM2 = "movie";

    // TODO: Rename and change types of parameters
    private ArrayList<Review> reviewsList;
    private Movie movie;
    private OnFragmentInteractionListener mListener;
    private MainActivityFragment.OnFragmentInteractionListenerMain mListenerTwoPane;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param listReviews Parameter 1.
     * @return A new instance of fragment ReviewListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewListFragment newInstance(Movie movie) {
        ReviewListFragment fragment = new ReviewListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie.getReviews());
        args.putParcelable(ARG_PARAM2,movie);
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reviewsList = (ArrayList<Review>) getArguments().getSerializable(ARG_PARAM1);
            movie = getArguments().getParcelable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review_list, container, false);

        ListView listViewReview = (ListView) rootView.findViewById(R.id.listViewReviews);
        ImageButton imageButtonCancel = (ImageButton) rootView.findViewById(R.id.imageButtonCancel);

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), reviewsList);
        listViewReview.setAdapter(reviewsAdapter);

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mTwoPane){
                    mListenerTwoPane.showDetails(movie);
                }else{
                    mListener.showDetails();

                }
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (MainActivity.mTwoPane) {
                mListenerTwoPane = (MainActivityFragment.OnFragmentInteractionListenerMain) getActivity();
            } else {
                mListener = (OnFragmentInteractionListener) activity;
            }

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListenerTwoPane = null;
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);


        void showReadMore(Movie movie);

        void showDetails();
    }

}
