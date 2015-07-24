package com.frodo.android.app.simple.cloud.amdb.entities;

import java.util.List;

public class Trailers implements TmdbEntity {
    private static final long serialVersionUID = 665059823359173539L;
    public Integer id;
    public List<Trailer> quicktime;
    public List<Trailer> youtube;

    public Trailers() {
    }
}
