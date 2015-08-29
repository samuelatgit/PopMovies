package com.samlam.android.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by slam on 8/29/2015.
 */
public class MainActivityFragment extends Fragment {

    private final String SORTSETTING = "sortsetting";
    private final String POPULARITY = "popularity";
    private final String RATING = "vote_average";
    private View _rootView;
    private GridAdapter _adapter;
    private String _sortBy = POPULARITY;

    public static MovieModel SelectedMovie;


    public MainActivityFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem action_sort_by_popularity = menu.findItem(R.id.action_sort_by_popularity);
        MenuItem action_sort_by_rating = menu.findItem(R.id.action_sort_by_rating);

        if (_sortBy.contentEquals(POPULARITY)) {
            if (!action_sort_by_popularity.isChecked()) {
                action_sort_by_popularity.setChecked(true);
            }
        } else if (_sortBy.contentEquals(RATING)) {
            if (!action_sort_by_rating.isChecked()) {
                action_sort_by_rating.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_by_popularity:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                _sortBy = POPULARITY;
                updateMovies(_sortBy);
                return true;
            case R.id.action_sort_by_rating:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                _sortBy = RATING;
                updateMovies(_sortBy);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //getActivity().setContentView(R.layout.fragment_main);
        if (savedInstanceState != null){
            String test = savedInstanceState.getString(SORTSETTING);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        _rootView = inflater.inflate(R.layout.fragment_main, container, false);

        _adapter = new GridAdapter(inflater.getContext());

        GridView gridView = (GridView) _rootView.findViewById(R.id.gridview);

        gridView.setAdapter(_adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MainActivityFragment.SelectedMovie = _adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TITLE, MainActivityFragment.SelectedMovie.getTitle());
                startActivity(intent);
//                Toast.makeText(getView().getContext(), "" + position,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        updateMovies(POPULARITY);

        return _rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!_sortBy.contentEquals(POPULARITY)) {
            outState.putString(SORTSETTING, _sortBy);
        }
//        if (mMovies != null) {
//            outState.putParcelableArrayList(MOVIES_KEY, mMovies);
//        }
        super.onSaveInstanceState(outState);
    }

    private void updateMovies(String sort_by) {
        new FetchMoviesTask(getActivity(), new TheMovieDbOrgProvider(getResources().getString(R.string.moviedb_apikey) ), _adapter ).execute(sort_by);
    }
}

