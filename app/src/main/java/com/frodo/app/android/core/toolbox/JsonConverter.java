package com.frodo.app.android.core.toolbox;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by frodo on 2016/3/2. Gson
 */
public class JsonConverter {

    public static String toJson(Object objectValue) {
       return new Gson().toJson(objectValue);
    }

    public static <T> T convert(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }
}
