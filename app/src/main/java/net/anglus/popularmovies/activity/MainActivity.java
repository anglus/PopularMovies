/**
 * Copyright 2018 Matthew Morris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.anglus.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.anglus.popularmovies.R;
import net.anglus.popularmovies.adapter.MovieAdapter;

/**
 * The MainActivity class displays a list of movie posters in a grid layout in a recycler view. The
 * list is either of the twenty most popular movies or the twenty highest rated movies, according to
 * The Movie Database (tmdb.com).
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NUM_LIST_ITEMS = 20;

    private MovieAdapter mAdapter;
    private RecyclerView mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * The onCreateOptionsMenu() method creates an Action Bar spinner menu with the options defined
     * in spinner_array. It then calls the onItemSelected() method to populate the Main Activity's
     * recycler view according to which menu item is selected.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);

        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return true;
    }

    /**
     * The onItemSelected() method populates the recycler view in the Main Activity, calling the
     * MovieAdapter class to retrieve the data.
     *
     * @param parent
     * @param view
     * @param position - an int representing the selected menu item's position in the spinner_array
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMovieList = (RecyclerView) findViewById(R.id.rv_main);
        mMovieList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mMovieList.setHasFixedSize(true);
        mAdapter = new MovieAdapter(NUM_LIST_ITEMS, position);
        mMovieList.setAdapter(mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
