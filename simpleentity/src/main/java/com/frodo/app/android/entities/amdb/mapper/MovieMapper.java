package com.frodo.app.android.entities.amdb.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.frodo.app.android.entities.amdb.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieMapper implements EntityMapper<com.frodo.app.android.simple.cloud.amdb.entities.Movie, Movie> {

    private final Gson gson;

    public MovieMapper() {
        gson = new Gson();
    }

    @Override
    public Movie transform(com.frodo.app.android.simple.cloud.amdb.entities.Movie entity) {
        Movie movie = new Movie();
        movie.id = entity.id;
        movie.imageUrl = entity.poster_path;
        movie.name = entity.title;
        return movie;
    }

    @Override
    public Collection<Movie> transform(
            Collection<com.frodo.app.android.simple.cloud.amdb.entities.Movie> entityCollection) {
        List<Movie> movieList = new ArrayList<>();
        for (com.frodo.app.android.simple.cloud.amdb.entities.Movie movieEntity : entityCollection) {
            Movie movie = transform(movieEntity);
            if (movie != null) {
                movieList.add(movie);
            }
        }

        return movieList;
    }

    @Override
    public com.frodo.app.android.simple.cloud.amdb.entities.Movie transformJsonToEntity(String jsonString)
            throws JsonSyntaxException {
        Type movieEntityType = new TypeToken<com.frodo.app.android.simple.cloud.amdb.entities.Movie>() {
        }.getType();
        return this.gson.fromJson(jsonString, movieEntityType);
    }

    @Override
    public Collection<com.frodo.app.android.simple.cloud.amdb.entities.Movie> transformJsonArrayToEntity(
            String jsonArrayString)
            throws JsonSyntaxException {
        Type listOfMovieEntityType = new TypeToken<List<com.frodo.app.android.simple.cloud.amdb.entities.Movie>>() {
        }.getType();
        return this.gson.<List<com.frodo.app.android.simple.cloud.amdb.entities.Movie>>fromJson(jsonArrayString,
                listOfMovieEntityType);
    }

    @Override
    public String transformEntityToJsonString() {
        return gson.toJson(this, getClass());
    }
}
