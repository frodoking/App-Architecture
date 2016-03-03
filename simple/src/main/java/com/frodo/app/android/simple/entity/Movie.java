package com.frodo.app.android.simple.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by frodo on 2016/3/3.
 */
public class Movie {
    @JsonProperty("poster_path")
    public String posterPath;
    @JsonProperty("adult")
    public boolean adult;
    @JsonProperty("overview")
    public String overView;
    @JsonProperty("release_date")
    public String releaseDate;
    @JsonProperty("genre_ids")
    public int[] genreIds;
    @JsonProperty("id")
    public int id;
    @JsonProperty("original_title")
    public String originalTitle;
    @JsonProperty("original_language")
    public String originalLanguage;
    @JsonProperty("title")
    public String title;
    @JsonProperty("backdrop_path")
    public String backdropPath;
    @JsonProperty("popularity")
    public float popularity;
    @JsonProperty("vote_count")
    public int voteCount;
    @JsonProperty("video")
    public boolean video;
    @JsonProperty("vote_average")
    public float voteAverage;
}
