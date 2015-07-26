package com.frodo.android.app.simple;

import java.util.ArrayList;
import java.util.List;

import com.frodo.android.app.simple.entities.amdb.Movie;
import com.frodo.android.app.simple.cloud.amdb.entities.MovieResultsPage;
import com.frodo.android.app.simple.cloud.amdb.services.MoviesService;
import com.frodo.android.app.simple.entities.amdb.mapper.MovieMapper;

/**
 * fetch movies from tmdb
 * Created by frodo on 2015/7/6.
 */
public class FetchMoviesTask extends AbstractFetchTask<MoviesService, MovieResultsPage,List<Movie>> {

    public FetchMoviesTask(MoviesService service,
                           OnFetchFinishedListener<List<Movie>> l) {
        super(service, l);
    }

    @Override
    public final MovieResultsPage doBackgroundCall() throws Exception {
        return getService().popular(1, "zh");
    }

    @Override
    public final void onSuccess(MovieResultsPage result) {
        MovieMapper movieMapper = new MovieMapper();

        List<Movie> currentMovies = new ArrayList<>();
        for (com.frodo.android.app.simple.cloud.amdb.entities.Movie movie : result.results) {
            currentMovies.add(movieMapper.transform(movie));
        }

        getListener().onSuccess(currentMovies);
    }

    @Override
    public final String key() {
        return "movies?page=1&lang=zh";
    }
}
