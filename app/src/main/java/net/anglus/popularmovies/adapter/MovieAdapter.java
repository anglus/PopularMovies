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
package net.anglus.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.util.Log;

import com.squareup.picasso.Picasso;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.activity.MovieActivity;
import net.anglus.popularmovies.rest.MovieClient;
import net.anglus.popularmovies.rest.MovieService;
import net.anglus.popularmovies.model.MovieResponse;
import net.anglus.popularmovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The MovieAdapter class is an adapter between the Main Activity and the movie data.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;
    public static int clickedMovie;

    private int numberItems;
    private int menuItem;

    /**
     * Constructor for the MovieAdapter class.
     *
     * @param numberItems - an int representing the number of items in the recycler view
     * @param menuItem - an int representing the position of the currently selected menu item in the
     *                 arry of menu items.
     */
    public MovieAdapter(int numberItems, int menuItem) {
        this.numberItems = numberItems;
        this.menuItem = menuItem;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int listItemId = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(listItemId, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    public int getClickedMovie() {
        return clickedMovie;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieItemView;
        List<Movie> movieList;

        /**
         * The constructor for the MovieViewHolder class. Holds a view of a movie item in the
         * recycler view list.
         *
         * @param itemView - a View representing a particular movie item
         */
        public MovieViewHolder(final View itemView) {
            super(itemView);

            movieItemView = (ImageView) itemView.findViewById(R.id.iv_movie);

            /**
             * Listen for clicks on the movie item and start the Movie Activity when clicked.
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    clickedMovie = movieList.get(clickedPosition).getId();
                    Log.d(TAG, "Clicked position: " + clickedPosition);
                    Log.d(TAG, "Movie ID: " + clickedMovie);

                    Context context = itemView.getContext();
                    Intent startMovieActivity = new Intent(context, MovieActivity.class);
                    context.startActivity(startMovieActivity);

                }
            });
        }

        /**
         * Binds the data in movie objects to the corresponding positions in the recycler view list.
         *
         * @param movieIndex - an int representing a particular movie item's position in the list of
         *                   movies.
         */
        void bind (final int movieIndex) {
            MovieService service = MovieClient.getRetrofit().create(MovieService.class);

            Call<MovieResponse> call;

            if (menuItem == 0) {
                call = service.getMoviesByPopularity(API_KEY);
            } else {
                call = service.getMoviesByRating(API_KEY);
            }

            if (call != null) {
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        movieList = response.body().getResults();
                        String poster = "http://image.tmdb.org/t/p/original" + movieList.get(movieIndex).getPosterPath();
                        Context context = itemView.getContext();
                        Picasso.with(context).load(poster).into(movieItemView);
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }

        }
    }
}
