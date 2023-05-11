package com.example.movies.mvvm.library.caching.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<Integer> fromString(String value) {
        if (value == null)
            return null;
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String toString(List<Integer> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
