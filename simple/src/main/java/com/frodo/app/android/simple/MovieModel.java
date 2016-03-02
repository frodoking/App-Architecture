package com.frodo.app.android.simple;

import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.framework.cache.Cache;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.entity.BeanNode;
import com.frodo.app.framework.net.Request;

import java.util.List;

import rx.Subscriber;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {
    private AndroidFetchNetworkDataTask fetchNetworkDataTask;
    private MovieCache movieCache;
    private boolean enableCached;

    private List<BeanNode> movies;

    public MovieModel(MainController controller) {
        super(controller);
        if (enableCached) {
            movieCache = new MovieCache(getMainController().getCacheSystem(), Cache.Type.DISK);
        }
    }

    public void loadMoviesWithRxjava(Subscriber<BeanNode> subscriber) {
        Request request = new Request("GET", Path.movie_popular);
        request.addQueryParam("page", "1");
        request.addQueryParam("lang", "zh");
        fetchNetworkDataTask = new AndroidFetchNetworkDataTask(getMainController().getNetworkTransport(), request,subscriber);
        getMainController().getBackgroundExecutor().execute(fetchNetworkDataTask);
    }

    public List<BeanNode> getMovies() {
        return movies;
    }

    public void setMovies(List<BeanNode> movies) {
        this.movies = movies;
        if (enableCached) {
            movieCache.put(fetchNetworkDataTask.key(), movies);
        }
    }

    public List<BeanNode> getMoviesFromCache() {
        return enableCached ? movieCache.get(fetchNetworkDataTask.key()) : null;
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

    @Override
    public void initBusiness() {
    }
}
