package com.samlam.android.popmovies;

import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by slam on 8/29/2015.
 */
public class TheMovieDbOrgProvider implements IMovieDataProvider {
    final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    private String _apikey ;
    private String LOGTAG = this.getClass().getName();

    public TheMovieDbOrgProvider(String apikey){
        _apikey = apikey;
    }

    @Override
    public List<MovieModel> GetPopularMovies(String sorting) throws JSONException {
        //http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[YOUR API KEY]
        Calendar calendar = Calendar.getInstance();

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("api_key", _apikey)
                .appendQueryParameter("sort_by", sorting + ".desc")
                .appendQueryParameter("primary_release_date.lte",String.valueOf( calendar.get(Calendar.DATE)))
                .appendQueryParameter("primary_release_year",String.valueOf(calendar.get(Calendar.YEAR)))
                .build();
        String urlString = builtUri.toString();
        return Map(Get(urlString));
    }

    @Override
    public List<MovieModel> GetHighestRatedMovies() throws JSONException {
        //http://api.themoviedb.org/3/discover/movie?primary_release_year=2015&sort_by=vote_average.desc&api_key=16eed74902d6b7435483ffca3c6c95a8

        Calendar calendar = Calendar.getInstance();

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("api_key", _apikey)
                .appendQueryParameter("sort_by", "vote_average.desc")
                .appendQueryParameter("primary_release_year",String.valueOf( calendar.get(Calendar.YEAR)))
                .build();
        String urlString = builtUri.toString();
        return Map(Get(urlString));
    }

    public String Get(String urlString) {
        try {
            URL url = new URL(urlString);
            Log.v(LOGTAG + ".Get()", "server URI " + urlString);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.e(LOGTAG + ".Get()", "Error when connecting url " + urlString, e);
            return null;
        }
    }

    public static List<MovieModel> Map(String json) throws JSONException{
        List<MovieModel> ret = new ArrayList<MovieModel>();
        if (ContextHelper.IsStringNullOrEmpty(json)) return ret;

        JSONObject movieJson = new JSONObject(json);
        JSONArray jsonMovies = movieJson.getJSONArray("results");

        for(int i = 0; i < jsonMovies.length(); i++) {
            JSONObject movie = jsonMovies.getJSONObject(i);
            MovieModel movieModel = new MovieModel(movie);
            movieModel.BaseUrl("http://image.tmdb.org/t/p/w500");
            ret.add(movieModel);
        }
        return ret;
    }
}
