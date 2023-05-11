package com.example.movies.mvvm.library.caching.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movies_table")
public class Movie {

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    private String originalLanguage;

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    private String originalTitle;

    @ColumnInfo(name = "video")
    @SerializedName("video")
    private boolean video;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "genre_ids")
    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private String backdropPath;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private double popularity;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private double voteAverage;

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    private boolean adult;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
