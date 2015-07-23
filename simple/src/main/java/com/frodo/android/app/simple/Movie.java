package com.frodo.android.app.simple;

import com.google.gson.Gson;

/**
 * Created by frodo on 2015/4/2.
 */
public class Movie {
    int id;
    String imageUrl;
    String name;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
