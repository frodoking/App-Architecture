package com.android.app.simple;

import java.util.ArrayList;
import java.util.List;

import com.android.app.framework.net.NetworkCallTask;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.services.MoviesService;

/**
 * Created by frodo on 2015/7/6.
 */
public class FetchMoviesTask extends NetworkCallTask<List<Movie>> {

    private MoviesService moviesService;
    private MovieModel.OnFetchMoviesFinishedListener listener;

    public FetchMoviesTask(MoviesService moviesService,MovieModel.OnFetchMoviesFinishedListener listener) {
        this.listener = listener;
        this.moviesService = moviesService;
    }

    @Override
    public List<Movie> doBackgroundCall() throws Exception {
        return moviesService.trending();
    }

    @Override
    public void onSuccess(List<Movie> result) {
        MovieMapper movieMapper = new MovieMapper();

        List<com.android.app.simple.Movie> currentMovies = new ArrayList<>();
        for (Movie movie : result) {
            currentMovies.add(movieMapper.map(movie));
        }

        listener.onSuccess(currentMovies);
    }

    @Override
    public void onError(Exception re) {
        listener.onError(re.getMessage());
    }
}
