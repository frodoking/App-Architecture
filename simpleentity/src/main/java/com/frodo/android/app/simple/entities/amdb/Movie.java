package com.frodo.android.app.simple.entities.amdb;

import com.google.gson.Gson;

/**
 * Created by frodo on 2015/4/2.
 */
public class Movie {
    public int id;
    public String imageUrl;
    public String name;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
