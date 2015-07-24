package com.frodo.android.app.simple.cloud.amdb.services;

import com.frodo.android.app.simple.cloud.amdb.entities.AppendToResponse;
import com.frodo.android.app.simple.cloud.amdb.entities.Credits;
import com.frodo.android.app.simple.cloud.amdb.entities.Images;
import com.frodo.android.app.simple.cloud.amdb.entities.Movie;
import com.frodo.android.app.simple.cloud.amdb.entities.MovieResultsPage;
import com.frodo.android.app.simple.cloud.amdb.entities.Releases;
import com.frodo.android.app.simple.cloud.amdb.entities.Trailers;
import com.frodo.android.app.simple.cloud.amdb.entities.Videos;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MoviesService {
    @GET("/movie/{id}/credits")
    Credits credits(@Path("id") int var1);

    @GET("/movie/{id}")
    Movie summary(@Path("id") int var1);

    @GET("/movie/{id}")
    Movie summary(@Path("id") int var1, @Query("language") String var2);

    @GET("/movie/{id}")
    Movie summary(@Path("id") int var1, @Query("language") String var2,
                  @Query("append_to_response") AppendToResponse var3);

    /**
     * @deprecated
     */
    @GET("/movie/{id}/trailers")
    @Deprecated
    Trailers trailers(@Path("id") int var1);

    @GET("/movie/{id}/videos")
    Videos videos(@Path("id") int var1);

    @GET("/movie/{id}/videos")
    Videos videos(@Path("id") int var1, @Query("language") String var2);

    @GET("/movie/now_playing")
    MovieResultsPage nowPlaying();

    @GET("/movie/now_playing")
    MovieResultsPage nowPlaying(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/popular")
    MovieResultsPage popular();

    @GET("/movie/popular")
    MovieResultsPage popular(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/{id}/similar_movies")
    MovieResultsPage similarMovies(@Path("id") int var1);

    @GET("/movie/{id}/similar_movies")
    MovieResultsPage similarMovies(@Path("id") int var1, @Query("page") Integer var2, @Query("language") String var3);

    @GET("/movie/top_rated")
    MovieResultsPage topRated();

    @GET("/movie/top_rated")
    MovieResultsPage topRated(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/upcoming")
    MovieResultsPage upcoming();

    @GET("/movie/upcoming")
    MovieResultsPage upcoming(@Query("page") Integer var1, @Query("language") String var2);

    @GET("/movie/{id}/releases")
    Releases releases(@Path("id") int var1);

    @GET("/movie/{id}/images")
    Images images(@Path("id") int var1);

    @GET("/movie/{id}/images")
    Images images(@Path("id") int var1, @Query("language") String var2);
}
