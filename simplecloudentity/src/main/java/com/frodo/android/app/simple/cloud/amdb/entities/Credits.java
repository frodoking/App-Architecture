package com.frodo.android.app.simple.cloud.amdb.entities;

import java.util.List;

public class Credits implements TmdbEntity {
    private static final long serialVersionUID = -7947291466017850804L;
    public Integer id;
    public List<CastMember> cast;
    public List<CrewMember> crew;

    public Credits() {
    }
}
