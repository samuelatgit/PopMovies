package com.samlam.android.popmovies;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slam on 8/29/2015.
 */
public class GridAdapter extends BaseAdapter {
    private final String LOGTAG = this.getClass().getName();
    private Context _context;
    private List<MovieModel> _movieList = new ArrayList<MovieModel>();
    private Point screenSize = new Point();

    public List<MovieModel> MovieList(){
        return _movieList;
    }

    public GridAdapter(Context c) {
        _context = c;
    }

    public List<MovieModel> setList(List<MovieModel> data){
        _movieList = data;
        notifyDataSetChanged(); // tell GridView to update
        return _movieList;
    }

    public int getCount() {
        return _movieList.size();
    }

    public MovieModel getItem(int position) {
        return _movieList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            //imageView.setLayoutParams(new GridView.LayoutParams(120,360));
//            imageView.setLayoutParams(new GridView.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(0, 0, 0, 0);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);

        //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg


        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(_context);
            view.setLayoutParams( new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            view.getLayoutParams().height= (int) _context.getResources().getDimension(R.dimen.grid_poster_height); //getViewHeight();
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        //String url = getItem(position);

        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(view);

        final MovieModel movie = getItem(position);

        String image_url = movie.BaseUrl() + movie.getImage();

        Log.v(LOGTAG + ".getView()", "image url " + image_url);
        Picasso.with(_context).load(image_url).into(view);

        return view;
    }

    private int getViewHeight(){
        if (screenSize.y == 0)
            ContextHelper.getDisplay(_context).getSize(screenSize);
        return screenSize.y /2;
    }

}