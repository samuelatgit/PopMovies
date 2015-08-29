package com.samlam.android.popmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

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
            return _dataProvider.GetPopularMovies(params[0]);
        }catch(JSONException e){
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);
        _adapter.setList(movieModels);
    }
}
