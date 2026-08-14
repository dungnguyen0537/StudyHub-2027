package com.studyhub.database;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> fromString(String value) {
        if (value == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) {
            return "[]";
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static Long fromDate(java.util.Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static java.util.Date toDate(Long timestamp) {
        return timestamp == null ? null : new java.util.Date(timestamp);
    }
}
