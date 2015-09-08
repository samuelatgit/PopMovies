package com.samlam.android.popmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by slam on 8/29/2015.
 */
public class MainActivityFragment extends Fragment {

    private final String MOVIE_LIST = "movieList";
    private final String SORT_SETTING = "sortSetting";
    private final String POPULARITY = "popularity";
    private final String RATING = "vote_average";
    private View _rootView;
    private GridAdapter _adapter;
    private String _sortBy = POPULARITY;

    public static MovieModel SelectedMovie;


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //getActivity().setContentView(R.layout.fragment_main);
        _adapter = (_adapter == null)? new GridAdapter(getActivity()): _adapter;
        if (savedInstanceState != null){
            String listJson = savedInstanceState.getString(MOVIE_LIST);
            _adapter.setListFromJson(listJson);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String sort = getPreference(R.string.pref_sort_by,POPULARITY);
        if (_sortBy != sort || _adapter.getCount() == 0) {
            _sortBy = sort;
            updateMovies(_sortBy);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        _rootView = inflater.inflate(R.layout.fragment_main, container, false);

        _adapter = (_adapter == null)? new GridAdapter(inflater.getContext()): _adapter;

        GridView gridView = (GridView) _rootView.findViewById(R.id.gridview);

        gridView.setAdapter(_adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                MainActivityFragment.SelectedMovie = _adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra(Intent.EXTRA_TITLE, MainActivityFragment.SelectedMovie.getTitle());
                startActivity(intent);
            }
        });

        return _rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        _sortBy = getPreference(R.string.pref_sort_by, POPULARITY);
        if (!_sortBy.contentEquals(POPULARITY)) {
            outState.putString(SORT_SETTING, _sortBy);
        }
        if (_adapter != null){
            outState.putString(MOVIE_LIST, _adapter.MovieListInJson());
        }
        super.onSaveInstanceState(outState);
    }

    private void updateMovies(String sort_by) {
        if (_adapter != null || _adapter.getCount()==0) {
            new FetchMoviesTask(getActivity(), new TheMovieDbOrgProvider(getResources().getString(R.string.moviedb_apikey)), _adapter).execute(sort_by);
        }
    }

    private String getPreference(int resourceId, String defaultValue){
        Context ctx = getActivity();
        return ContextHelper.GetDefaultSharedPreference(ctx).getString(ctx.getResources().getString(resourceId),defaultValue);
    }
}

