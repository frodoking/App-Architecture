package com.frodo.android.app.simple;

import java.util.List;

import com.frodo.android.app.framework.cache.Cache;
import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.cloud.amdb.services.MoviesService;
import com.frodo.android.app.simple.entities.amdb.Movie;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {

    private FetchMoviesTask fetchMoviesTask;
    private MoviesService moviesService;
    private MovieCache movieCache;
    private boolean enableCached;

    public MovieModel(MainController controller) {
        super(controller);
        moviesService = getMainController().getNetworkInteractor().create(MoviesService.class);
        if (enableCached) {
            movieCache = new MovieCache(getMainController().getCacheSystem(), Cache.Type.DISK);
        }
    }

    public void loadMovies(OnFetchMoviesFinishedListener listener) {
        fetchMoviesTask = new FetchMoviesTask(moviesService, listener);
        getMainController().getBackgroundExecutor().execute(fetchMoviesTask);
    }

    public void setMovies(List<Movie> movies) {
        if (enableCached) {
            movieCache.put(fetchMoviesTask.key(), movies);
        }
    }

    public List<Movie> getMoviesFromCache() {
        return enableCached ? movieCache.get(fetchMoviesTask.key()) : null;
    }

    public boolean isEnableCached() {
        return enableCached;
    }

    public void setEnableCached(boolean enableCached) {
        this.enableCached = enableCached;

        if (movieCache == null) {
            movieCache = new MovieCache(getMainController().getCacheSystem(), Cache.Type.DISK);
        }
    }

    public interface OnFetchMoviesFinishedListener {
        void onError(String errorMsg);

        void onSuccess(List<Movie> movies);
    }
}
