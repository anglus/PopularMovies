package net.anglus.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.activity.MainActivity;
import net.anglus.popularmovies.rest.MovieClient;
import net.anglus.popularmovies.rest.MovieService;
import net.anglus.popularmovies.model.MovieResponse;
import net.anglus.popularmovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static net.anglus.popularmovies.activity.MainActivity.menuItem;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;

    private int numberItems;
    private int menuItem;
    //private List<Movie> movieList;
    //private TextView movieItemView;

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

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieItemView;
        List<Movie> movieList;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieItemView = (TextView) itemView.findViewById(R.id.tv_movie);
        }

        void bind (final int movieIndex) {
            MovieService service = MovieClient.getRetrofit().create(MovieService.class);

            Call<MovieResponse> call;

            /*
            switch (menuItem) {
                case 0:
                    call = service.getMoviesByPopularity(API_KEY);
                    break;
                case 1:
                    call = service.getMoviesByRating(API_KEY);
                    break;
                //case 2:
                    //call = service.getMovie(348350, API_KEY);
                    //break;
                default:
                    call = service.getMoviesByPopularity(API_KEY);
                    break;
            }
            */

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
                        //Toast.makeText(MainActivity, "Failure", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.toString());
                    }
                });
            }

        }
    }
}
