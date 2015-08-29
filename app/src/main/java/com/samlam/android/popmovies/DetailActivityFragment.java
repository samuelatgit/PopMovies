package com.samlam.android.popmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by slam on 8/29/2015.
 */
public class DetailActivityFragment extends Fragment {
    private View _rootView;
    private MovieModel _movie;
    private ScrollView _scrollView;
    private ImageView _imageView;
    private TextView _titleView;
    private TextView _overviewView;
    private TextView _dateView;
    private TextView _voteAverageView;
    private LinearListView _trailersView;
    private LinearListView _reviewsView;
    private CardView _reviewsCardview;
    private CardView _trailersCardview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        _movie = MainActivityFragment.SelectedMovie;

        _rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        _scrollView = (ScrollView) _rootView.findViewById(R.id.detail_layout);

        _imageView = (ImageView) _rootView.findViewById(R.id.detail_image);
        _titleView = (TextView) _rootView.findViewById(R.id.detail_title);
        _overviewView = (TextView) _rootView.findViewById(R.id.detail_overview);
        _dateView = (TextView) _rootView.findViewById(R.id.detail_date);
        _voteAverageView = (TextView) _rootView.findViewById(R.id.detail_vote_average);
        _trailersView = (LinearListView) _rootView.findViewById(R.id.detail_trailers);
//        _reviewsView = (LinearListView) _rootView.findViewById(R.id.detail_reviews);
//        _reviewsCardview = (CardView) _rootView.findViewById(R.id.detail_reviews_cardview);
//        _trailersCardview = (CardView) _rootView.findViewById(R.id.detail_trailers_cardview);
//        mTrailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<Trailer>());
//        mTrailersView.setAdapter(mTrailerAdapter);
//
//        mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(LinearListView linearListView, View view,
//                                    int position, long id) {
//                Trailer trailer = mTrailerAdapter.getItem(position);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
//                startActivity(intent);
//            }
//        });

//        mReviewAdapter = new ReviewAdapter(getActivity(), new ArrayList<Review>());
//        mReviewsView.setAdapter(mReviewAdapter);

        if (_movie != null) {

            String image_url = _movie.BaseUrl() + _movie.getImage2();

            Picasso.with(inflater.getContext()).load(image_url).into(_imageView);

            _titleView.setText(_movie.getTitle());
            _overviewView.setText(_movie.getOverview());

            String movie_date = _movie.getDate();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String date = DateUtils.formatDateTime(getActivity(),
                        formatter.parse(movie_date).getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
                _dateView.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            _voteAverageView.setText(Integer.toString(_movie.getRating()) + " of 10");
        }
        return _rootView;
    }
}
