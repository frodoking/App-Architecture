package com.frodo.android.app.simple;

import io.realm.RealmObject;

/**
 * Created by frodo on 2015/4/2.
 */
public class Movie extends RealmObject {
    int id;
    String imageUrl;
    String name;
}
