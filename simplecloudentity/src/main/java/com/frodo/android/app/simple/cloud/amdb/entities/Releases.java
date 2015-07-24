package com.frodo.android.app.simple.cloud.amdb.entities;

import java.util.List;

public class Releases implements TmdbEntity {
    public int id;
    public List<CountryRelease> countries;

    public Releases() {
    }
}