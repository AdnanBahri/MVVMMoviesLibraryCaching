package com.example.movies.mvvm.library.caching.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movies.mvvm.library.caching.models.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies_table")
    Observable<List<Movie>> getMovies();

    @Query("SELECT * FROM movies_table WHERE id=:movie_id")
    Single<Movie> getMovie(int movie_id);

    @Query("SELECT * FROM movies_table ORDER BY original_title DESC")
    Observable<List<Movie>> fetchAlphabeticallyMovies();

    @Query("SELECT * FROM movies_table ORDER BY release_date DESC")
    Observable<List<Movie>> fetchMoviesByDate();

    @Query("SELECT * FROM movies_table ORDER BY vote_average DESC")
    Observable<List<Movie>> fetchMoviesByRating();

    @Query("SELECT * FROM movies_table ORDER BY vote_count DESC")
    Observable<List<Movie>> fetchMoviesByVoteCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable saveMovies(List<Movie> movie);

    @Delete
    Completable delete(Movie movie);

    @Query("DELETE FROM movies_table WHERE id=:movie_id")
    Completable deleteMovieById(int movie_id);

    @Query("DELETE FROM movies_table")
    Completable deleteAll();

    @Query("SELECT COUNT(*) FROM movies_table WHERE id = :movieId")
    Single<Integer> isMovieInFavorites(int movieId);
}
