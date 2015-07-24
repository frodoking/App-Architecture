package com.frodo.android.app.simple;

import java.util.ArrayList;
import java.util.List;

import com.frodo.android.app.framework.net.NetworkCallTask;
import com.frodo.android.app.simple.cloud.amdb.entities.Movie;
import com.frodo.android.app.simple.cloud.amdb.entities.MovieResultsPage;
import com.frodo.android.app.simple.cloud.amdb.services.MoviesService;
import com.frodo.android.app.simple.entities.amdb.mapper.MovieMapper;

/**
 * Created by frodo on 2015/7/6.
 */
public class FetchMoviesTask extends NetworkCallTask<MovieResultsPage> {

    private MoviesService moviesService;
    private MovieModel.OnFetchMoviesFinishedListener listener;

    public FetchMoviesTask(MoviesService moviesService, MovieModel.OnFetchMoviesFinishedListener listener) {
        this.listener = listener;
        this.moviesService = moviesService;
    }

    @Override
    public MovieResultsPage doBackgroundCall() throws Exception {
        return moviesService.popular(1, "zh");
    }

    @Override
    public void onSuccess(MovieResultsPage result) {
        MovieMapper movieMapper = new MovieMapper();

        List< com.frodo.android.app.simple.entities.amdb.Movie> currentMovies = new ArrayList<>();
        for (Movie movie : result.results) {
            // tmp fix
            com.frodo.android.app.simple.entities.amdb.Movie tmp = movieMapper.transform(movie);
            tmp.imageUrl = Constants.TMDB_IMAGE_BASE_PATH +  tmp.imageUrl;
            currentMovies.add(tmp);
        }

        listener.onSuccess(currentMovies);
    }

    @Override
    public void onError(Exception re) {
        listener.onError(re.getMessage());
    }

    @Override
    public String key() {
        return "movies?page=1&lang=zh";
    }
}
