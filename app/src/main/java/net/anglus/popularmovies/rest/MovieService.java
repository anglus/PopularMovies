package net.anglus.popularmovies.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import net.anglus.popularmovies.model.Movie;
import net.anglus.popularmovies.model.MovieResponse;

public interface MovieService {
    @GET("popular")
    Call<MovieResponse> getMoviesByPopularity(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getMoviesByRating(@Query("api_key") String apiKey);

    @GET("{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);
}
