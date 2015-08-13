package com.frodo.android.app.simple;

import com.frodo.android.app.framework.cache.Cache;
import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.cloud.amdb.services.MoviesService;
import com.frodo.android.app.simple.entities.amdb.Movie;

import java.util.List;

import rx.Subscriber;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {
    //    private FetchMoviesTask fetchMoviesTask;
    private FetchMoviesWithRxjavaTask fetchMoviesWithRxjavaTask;
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

    /*public void loadMovies(OnFetchFinishedListener<List<Movie>> listener) {
        fetchMoviesTask = new FetchMoviesTask(moviesService, listener);
        getMainController().getBackgroundExecutor().execute(fetchMoviesTask);
    }*/

    public void loadMoviesWithRxjava(Subscriber<List<Movie>> subscriber) {
        fetchMoviesWithRxjavaTask = new FetchMoviesWithRxjavaTask(moviesService, subscriber);
        getMainController().getBackgroundExecutor().execute(fetchMoviesWithRxjavaTask);
    }

    public void setMovies(List<Movie> movies) {
        if (enableCached) {
            movieCache.put(fetchMoviesWithRxjavaTask.key(), movies);
        }
    }

    public List<Movie> getMoviesFromCache() {
        return enableCached ? movieCache.get(fetchMoviesWithRxjavaTask.key()) : null;
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
}
