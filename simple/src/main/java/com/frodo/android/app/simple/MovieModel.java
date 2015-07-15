package com.frodo.android.app.simple;

import java.util.List;

import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.MainController;
import com.uwetrottmann.tmdb.services.MoviesService;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {

    private FetchMoviesTask fetchMoviesTask;
    private MoviesService moviesService;

    public MovieModel(MainController controller) {
        super(controller);
        moviesService = getMainController().getNetworkInteractor().create(MoviesService.class);
    }

    public void loadMovies(OnFetchMoviesFinishedListener listener) {
        fetchMoviesTask = new FetchMoviesTask(moviesService, listener);
        getMainController().getBackgroundExecutor().execute(fetchMoviesTask);
    }

    public interface OnFetchMoviesFinishedListener {
        void onError(String errorMsg);

        void onSuccess(List<Movie> movies);
    }
}
