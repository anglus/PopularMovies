package net.anglus.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.adapter.MovieAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final int NUM_LIST_ITEMS = 20;

    private MovieAdapter mAdapter;
    private RecyclerView mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = (RecyclerView) findViewById(R.id.rv_main);
        mMovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mMovieList.setHasFixedSize(true);
        mAdapter = new MovieAdapter(NUM_LIST_ITEMS);
        mMovieList.setAdapter(mAdapter);
    }
}
