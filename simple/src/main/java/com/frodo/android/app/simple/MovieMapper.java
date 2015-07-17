package com.frodo.android.app.simple;

import com.frodo.android.app.core.mapper.EntityMapper;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieMapper extends EntityMapper<com.uwetrottmann.tmdb.entities.Movie, Movie> {

    @Override
    public Movie map(com.uwetrottmann.tmdb.entities.Movie entity) {
        Movie movie = new Movie();
        movie.id = entity.id;
        movie.imageUrl = Constants.TMDB_IMAGE_BASE_PATH + entity.poster_path;
        movie.name = entity.title;
        return movie;
    }
}
