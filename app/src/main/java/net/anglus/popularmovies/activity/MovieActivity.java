package net.anglus.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.adapter.MovieAdapter;
import net.anglus.popularmovies.rest.MovieClient;
import net.anglus.popularmovies.rest.MovieService;
import net.anglus.popularmovies.model.Movie;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = MovieActivity.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;
    private TextView mMovieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.anglus.popularmovies.R.layout.activity_movie);

        mMovieDetails = (TextView) findViewById(R.id.tv_movie_details);

        MovieService service = MovieClient.getRetrofit().create(MovieService.class);
        Call<Movie> call = service.getMovie(MovieAdapter.clickedMovie, API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                String poster = response.body().getPosterPath();

                String title = response.body().toString();

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                Date date = response.body().getReleaseDate();
                String releaseDate = dateFormat.format(date);

                Float rating = response.body().getVoteAverage();

                String overview = response.body().getOverview();

                String details = poster + "\n\n" + title + "\n\n" + releaseDate + "\n\n"
                        + rating + "\n\n" + overview;
                mMovieDetails.setText(details);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
