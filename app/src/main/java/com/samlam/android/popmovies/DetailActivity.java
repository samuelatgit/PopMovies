package com.samlam.android.popmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by slam on 8/29/2015.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    //.add(R.id.container, new PlaceholderFragment())
                    .add(R.id.detail_activity, new DetailActivityFragment())
                    .commit();
        }

    }
}
