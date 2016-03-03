package com.frodo.app.android.simple;

import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;

/**
 * Created by frodo on 2015/8/18.
 */
public class ImagesConverter {
    public static String getAbsoluteUrl(ServerConfiguration configuration, Movie movie) {
        return configuration.images.baseUrl + configuration.images.posterSizes[2] + movie.posterPath;
    }
}
