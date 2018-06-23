package net.anglus.popularmovies.activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.adapter.MovieAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;
    private static final int NUM_LIST_ITEMS = 20;
    //private static int menuItem = 0;

    private MovieAdapter mAdapter;
    private RecyclerView mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner spinner = (Spinner) findViewById(R.id.menu_spinner);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //spinner.setOnItemSelectedListener(this);

        //mMovieList = (RecyclerView) findViewById(R.id.rv_main);
        //mMovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        //mMovieList.setHasFixedSize(true);
        //mAdapter = new MovieAdapter(NUM_LIST_ITEMS, menuItem);
        //mMovieList.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);

        //Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //String arr[] = {"Popular", "Top Rated", "Solo"};
        //String menuItemText = arr[position];
        String[] arr = getResources().getStringArray(R.array.spinner_array);
        String menuItemText = arr[position];
        /*
        switch (position) {
            case 0:
                Toast.makeText(this, "Popular", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, "Top Rated", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Solo", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        */
        Toast.makeText(this, menuItemText, Toast.LENGTH_SHORT).show();
        int menuItem = position;
        Log.d(TAG, "menuItem is: " + String.valueOf(menuItem));
        //mAdapter = new MovieAdapter(NUM_LIST_ITEMS, menuItem);
        //mMovieList.setAdapter(mAdapter);
        mMovieList = (RecyclerView) findViewById(R.id.rv_main);
        mMovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mMovieList.setHasFixedSize(true);
        mAdapter = new MovieAdapter(NUM_LIST_ITEMS, menuItem);
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {

    //}
}
