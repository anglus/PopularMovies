package net.anglus.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import net.anglus.popularmovies.BuildConfig;
import net.anglus.popularmovies.R;
import net.anglus.popularmovies.model.MovieResponse;
import net.anglus.popularmovies.rest.MovieClient;
import net.anglus.popularmovies.rest.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView key = findViewById(R.id.api_key);
        //key.setText(API_KEY);
        MovieService service = MovieClient.getRetrofit().create(MovieService.class);

        //Call<MovieResponse> call = service.getMovie(129, API_KEY);
        Call<MovieResponse> call = service.getMoviesByPopularity(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Integer page = response.body().getPage();
                Integer totalPages = response.body().getTotalPages();
                Integer totalResults = response.body().getTotalResults();
                String results = response.body().getResults();
                String movies = "page: " + page +
                                "\n\ntotal pages: " + totalPages +
                                "\n\ntotal results: " + totalResults +
                                "\n\nresults: \n" + results;
                TextView mResults = findViewById(R.id.hello);
                mResults.setText(movies);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
