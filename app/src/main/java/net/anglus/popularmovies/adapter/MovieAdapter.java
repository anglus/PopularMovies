package net.anglus.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;
    public static int clickedMovie;

    private int numberItems;
    private int menuItem;

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
        TextView movieItemView;
        List<Movie> movieList;

        public MovieViewHolder(final View itemView) {
            super(itemView);

            movieItemView = (TextView) itemView.findViewById(R.id.tv_movie);

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
                        String movieId = movieList.get(movieIndex).getId().toString();
                        String movieItem = movieList.get(movieIndex).toString() + "\n" + movieId;
                        movieItemView.setText(movieItem);
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
