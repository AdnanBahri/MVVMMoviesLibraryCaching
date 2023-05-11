package com.example.movies.mvvm.library.caching.repositories;

import com.example.movies.mvvm.library.caching.local.MovieDao;
import com.example.movies.mvvm.library.caching.models.Movie;
import com.example.movies.mvvm.library.caching.models.MoviesResponse;
import com.example.movies.mvvm.library.caching.remote.ApiClient;
import com.example.movies.mvvm.library.caching.remote.ApiService;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class HomeRepository {

    private ApiService api;
    private MovieDao dao;

    public HomeRepository(MovieDao dao){
        this.dao = dao;
        api= ApiClient.getInstance().create(ApiService.class);
    }

    public Observable<MoviesResponse> fetchFromNetwork(String api_key,int page){
        return api.getPopular(api_key,page);
    }

    public Observable<List<Movie>> fetchFromDatabase(){
        return dao.getMovies();
    }

    public Observable<MoviesResponse> getDiscoverMoviesBy(String sort_by,int page){
        return api.getDiscoverMovieBy("eea817b734ed288ab6730d4787451043",sort_by,page);
    }
}
