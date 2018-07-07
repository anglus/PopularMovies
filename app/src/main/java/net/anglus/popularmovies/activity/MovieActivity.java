package net.anglus.popularmovies.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.squareup.picasso.Picasso;

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
    //private TextView mMovieDetails;
    private ImageView mMovieBackdrop;
    private TextView mMovieTitle;
    private ImageView mMoviePoster;
    private TextView mMovieDate;
    private TextView mMovieScore;
    private TextView mMoviePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(net.anglus.popularmovies.R.layout.activity_movie);

        //mMovieDetails = (TextView) findViewById(R.id.tv_movie_details);
        mMovieBackdrop = (ImageView) findViewById(R.id.iv_movie_backdrop);
        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        mMovieDate = (TextView) findViewById(R.id.tv_movie_release_date);
        mMovieScore = (TextView) findViewById(R.id.tv_movie_user_rating);
        mMoviePlot = (TextView) findViewById(R.id.tv_movie_plot_synopsis);

        MovieService service = MovieClient.getRetrofit().create(MovieService.class);
        Call<Movie> call = service.getMovie(MovieAdapter.clickedMovie, API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                String activityTitle = "Movie Detail";
                MovieActivity.this.setTitle(activityTitle);

                String backdrop = "http://image.tmdb.org/t/p/original" + response.body().getBackdropPath();
                Context context = getApplicationContext();
                Picasso.with(context).load(backdrop).into(mMovieBackdrop);

                String movieTitle = response.body().getTitle();
                String originalTitle = response.body().getOriginalTitle();
                String fullTitle = movieTitle.equals(originalTitle) ? movieTitle : movieTitle + "\n(" + originalTitle + ")";

                String poster = "http://image.tmdb.org/t/p/w185" + response.body().getPosterPath();
                Picasso.with(context).load(poster).into(mMoviePoster);

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
                Date date = response.body().getReleaseDate();
                String releaseDate = dateFormat.format(date);

                Float rating = response.body().getVoteAverage();

                String overview = response.body().getOverview();

                mMovieTitle.setText(fullTitle);
                mMovieDate.setText(releaseDate);
                mMovieScore.setText(String.valueOf(rating));
                mMoviePlot.setText(overview);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
