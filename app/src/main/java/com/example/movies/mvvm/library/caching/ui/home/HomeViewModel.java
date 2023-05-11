package com.example.movies.mvvm.library.caching.ui.home;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.mvvm.library.caching.local.Database;
import com.example.movies.mvvm.library.caching.models.Movie;
import com.example.movies.mvvm.library.caching.models.MoviesResponse;
import com.example.movies.mvvm.library.caching.repositories.HomeRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {

    public static final String TAG = "Catch ViewModel";

    private HomeRepository repo;
    private CompositeDisposable disposable;
    private MutableLiveData<List<Movie>> moviesData;
    private MutableLiveData<Boolean> isMovieLoading;
    private MutableLiveData<String> isError;
    private Database db;

    // Pagination Variables:
    private int currentPage = 1;
    private int totalPages = 1;
    private String currentOption = "";

    public HomeViewModel(@NonNull Application application) {
        super(application);
        db = Database.getInstance(application);
        repo = new HomeRepository(db.dao());
        disposable = new CompositeDisposable();
        moviesData = new MutableLiveData<>();
        isMovieLoading = new MutableLiveData<>();
        isError = new MutableLiveData<>();
    }

    public LiveData<String> getError() {
        return isError;
    }

    public LiveData<Boolean> isLoading() {
        return isMovieLoading;
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesData;
    }

    public void fetchMovies(String option) {
        if (currentPage > totalPages)
            return;
        isMovieLoading.setValue(true);
        if (!currentOption.equals(option)) {
            resetPage();
            currentOption = option;
            if (checkInternetConnection()) {
                switch (option) {
                    case "Release Date":
                        repo.getDiscoverMoviesBy("release_date.desc", currentPage)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(apiObserver);
                        break;
                    case "Rating":
                        repo.getDiscoverMoviesBy("vote_average.desc", currentPage)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(apiObserver);
                        break;
                    case "Vote Count":
                        repo.getDiscoverMoviesBy("vote_count.desc", currentPage)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(apiObserver);
                        break;
                }
            } else {
                switch (option) {
                    case "Release Date":
                        db.dao().fetchMoviesByDate()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(dbObserver);
                        break;
                    case "Rating":
                        db.dao().fetchMoviesByRating()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(dbObserver);
                        break;
                    case "Vote Count":
                        db.dao().fetchMoviesByVoteCount()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(dbObserver);
                        break;
                }
            }
        }
    }

    private Observer<List<Movie>> dbObserver = new Observer<List<Movie>>() {
        @Override
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            disposable.add(d);
            Log.d(TAG, "onSubscribe: Fetching From Database");
        }

        @Override
        public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Movie> list) {
            if (list == null)
                Log.d(TAG, "Db Observer onNext : Database is Empty");
            Log.d(TAG, "Db Observer onNext : Database isn't Empty " + list.size());
            moviesData.setValue(list);
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            isError.setValue(e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            isMovieLoading.setValue(false);
        }
    };

    private Observer<MoviesResponse> apiObserver = new Observer<MoviesResponse>() {
        @Override
        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            disposable.add(d);
            Log.d(TAG, "onSubscribe: Fetching From Network");
        }

        @Override
        public void onNext(@io.reactivex.rxjava3.annotations.NonNull MoviesResponse response) {
            List<Movie> temp = new ArrayList<>();
            if (currentPage > 1) {
                temp.addAll(moviesData.getValue());
            }

            temp.addAll(response.getResults());
            moviesData.setValue(temp);
            currentPage = response.getPage() + 1;
            totalPages = response.getTotalPages();

            if (currentPage == 1)
                db.dao().deleteAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        db.dao().saveMovies(response.getResults()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Insert to Database OnComplete");
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                Log.d(TAG, "Insert to Database OnError: " + e.getMessage());
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
            else
                db.dao().saveMovies(response.getResults()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Insert to Database OnComplete");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "Insert to Database OnError: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
        }

        @Override
        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            isError.setValue(e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            isMovieLoading.setValue(false);
        }
    };


    // Reset Pagination Variable: When the Sorting option is Changed
    private void resetPage() {
        currentPage = 1;
        totalPages = 1;
    }

    // Check if The user is Connected to Internet
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}