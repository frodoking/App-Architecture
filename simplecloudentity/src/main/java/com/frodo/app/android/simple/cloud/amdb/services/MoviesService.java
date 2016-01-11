package com.frodo.app.android.simple.cloud.amdb.services;

import com.frodo.app.android.simple.cloud.amdb.entities.Videos;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MoviesService {
    @GET("/movie/{id}/credits")
    com.frodo.app.android.simple.cloud.amdb.entities.Credits credits(@Path("id") int var1);

    @GET("/movie/{id}")
    com.frodo.app.android.simple.cloud.amdb.entities.Movie summary(@Path("id") int var1);

    @GET("/movie/{id}")
    com.frodo.app.android.simple.cloud.amdb.entities.Movie summary(@Path("id") int var1, @Query("language") String var2);

    @GET("/movie/{id}")
    com.frodo.app.android.simple.cloud.amdb.entities.Movie summary(@Path("id") int var1, @Query("language") String var2,
                  @Query("append_to_response") com.frodo.app.android.simple.cloud.amdb.entities.AppendToResponse var3);

    /**
     * @deprecated
     */
    @GET("/movie/{id}/trailers")
    @Deprecated
    com.frodo.app.android.simple.cloud.amdb.entities.Trailers trailers(@Path("id") int var1);

    @GET("/movie/{id}/videos")
    Videos videos(@Path("id") int var1);

    @GET("/movie/{id}/videos")
    Videos videos(@Path("id") int var1, @Query("language") String var2);

    @GET("/movie/now_playing")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage nowPlaying();

    @GET("/movie/now_playing")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage nowPlaying(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/popular")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage popular();

    @GET("/movie/popular")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage popular(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/{id}/similar_movies")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage similarMovies(@Path("id") int var1);

    @GET("/movie/{id}/similar_movies")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage similarMovies(@Path("id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("/movie/top_rated")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage topRated();

    @GET("/movie/top_rated")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage topRated(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/upcoming")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage upcoming();

    @GET("/movie/upcoming")
    com.frodo.app.android.simple.cloud.amdb.entities.MovieResultsPage upcoming(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/{id}/releases")
    com.frodo.app.android.simple.cloud.amdb.entities.Releases releases(@Path("id") int var1);

    @GET("/movie/{id}/images")
    com.frodo.app.android.simple.cloud.amdb.entities.Images images(@Path("id") int var1);

    @GET("/movie/{id}/images")
    com.frodo.app.android.simple.cloud.amdb.entities.Images images(@Path("id") int var1, @Query("language") String var2);
}
