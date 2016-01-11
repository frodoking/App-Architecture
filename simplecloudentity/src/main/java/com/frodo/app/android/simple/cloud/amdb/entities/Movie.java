package com.frodo.app.android.simple.cloud.amdb.entities;

import java.util.Date;
import java.util.List;

public class Movie implements TmdbEntity {
    private static final long serialVersionUID = 4604935751051141456L;
    public Integer id;
    public Boolean adult;
    public String backdrop_path;
    public Integer budget;
    public List<Genre> genres;
    public String homepage;
    public String imdb_id;
    public String original_title;
    public String overview;
    public Double popularity;
    public String poster_path;
    public List<ProductionCompany> production_companies;
    public List<ProductionCountry> production_countries;
    public Date release_date;
    public Integer revenue;
    public Integer runtime;
    public List<SpokenLanguage> spoken_languages;
    public String tagline;
    public String title;
    public Double vote_average;
    public Integer vote_count;
    public com.frodo.app.android.simple.cloud.amdb.entities.Trailers trailers;
    public com.frodo.app.android.simple.cloud.amdb.entities.Videos videos;
    public com.frodo.app.android.simple.cloud.amdb.entities.Releases releases;
    public com.frodo.app.android.simple.cloud.amdb.entities.Credits credits;
    public Images images;
    public com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage similar_movies;

    public Movie() {
    }
}
