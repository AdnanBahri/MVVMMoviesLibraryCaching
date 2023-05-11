package com.example.movies.mvvm.library.caching.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {


    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> movies;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Movie> getResults() {
        return movies;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
