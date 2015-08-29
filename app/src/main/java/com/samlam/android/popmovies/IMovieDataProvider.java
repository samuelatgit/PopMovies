package com.samlam.android.popmovies;

import org.json.JSONException;

import java.util.List;

/**
 * Created by slam on 8/29/2015.
 */
public interface IMovieDataProvider {
    List<MovieModel> GetPopularMovies(String sorting) throws JSONException;
    List<MovieModel> GetHighestRatedMovies() throws JSONException;
}
