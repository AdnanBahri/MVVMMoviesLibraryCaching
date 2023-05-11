package com.example.movies.mvvm.library.caching.remote;

import com.example.movies.mvvm.library.caching.models.MoviesResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Observable<MoviesResponse> getPopular(
            @Query("api_key") String api_key,
            @Query("page") Integer page
    );

    @GET("discover/movie")
    Observable<MoviesResponse> getDiscoverMovieBy(
            @Query("api_key") String api_key,
            @Query("sort_by") String sort_by,
            @Query("page") int page
    );
}
