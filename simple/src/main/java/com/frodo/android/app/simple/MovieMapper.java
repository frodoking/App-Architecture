package com.frodo.android.app.simple;

import com.frodo.android.app.core.mapper.EntityMapper;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieMapper extends EntityMapper<com.jakewharton.trakt.entities.Movie, Movie> {
    @Override
    public Movie map(com.jakewharton.trakt.entities.Movie entity) {
        Movie movie = new Movie();
        movie.imageUrl = entity.images.poster;
        movie.name = entity.title;
        return movie;
    }
}
