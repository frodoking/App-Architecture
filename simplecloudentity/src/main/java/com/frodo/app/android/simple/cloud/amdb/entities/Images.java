package com.frodo.app.android.simple.cloud.amdb.entities;

import java.util.List;

public class Images implements TmdbEntity {
    public Integer id;
    public List<Image> backdrops;
    public List<Image> posters;

    public Images() {
    }
}