package com.example.movies.mvvm.library.caching.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movies.mvvm.library.caching.models.Movie;
import com.example.movies.mvvm.library.caching.utils.Converters;

@androidx.room.Database(entities = {Movie.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "movies_db";
    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract MovieDao dao();
}