package com.samlam.android.popmovies;

import android.content.Context;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slam on 8/29/2015.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<MovieModel>> {
    private final String LOG_TAG = FetchMoviesTask.class.getName();
    private Context _context;
    private IMovieDataProvider _dataProvider;
    private GridAdapter _adapter;

    public FetchMoviesTask(Context ctx, IMovieDataProvider dataProvider, GridAdapter adapter){
        _context = ctx;
        _dataProvider = dataProvider;
        _adapter = adapter;
    }

    @Override
    protected List<MovieModel> doInBackground(String... params) {
        try{
            if (ContextHelper.IsNetworkConnected(_context))
                return _dataProvider.GetPopularMovies(params[0]);
            else{
                //TODO: return cache data here
                return new ArrayList<MovieModel>();
            }
        }catch(JSONException e){
            Log.e(LOG_TAG + ".doInBackground()", "Error when fetching movies", e);
            return new ArrayList<MovieModel>();
        }
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);
        _adapter.setList(movieModels);
        if (movieModels.isEmpty()) {
            Toast.makeText(_context, "No movie data returned, please check your Internet connection",
                    Toast.LENGTH_LONG).show();
        }
    }
}
