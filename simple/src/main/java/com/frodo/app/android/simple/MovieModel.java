package com.frodo.app.android.simple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.framework.cache.Cache;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.net.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Subscriber;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {
    private AndroidFetchNetworkDataTask fetchNetworkDataTask;
    private MovieCache movieCache;
    private boolean enableCached;

    private List<Movie> movies;

    public MovieModel(MainController controller) {
        super(controller);
        if (enableCached) {
            movieCache = new MovieCache(getMainController().getCacheSystem(), Cache.Type.DISK);
        }
    }

    public void loadMoviesWithRxjava(final Subscriber<? super List<Movie>> subscriber) {
        Request request = new Request("GET", Path.movie_popular);
        request.addQueryParam("page", "1");
        request.addQueryParam("lang", "zh");
        fetchNetworkDataTask = new AndroidFetchNetworkDataTask(getMainController().getNetworkTransport(), request, new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                subscriber.onStart();
            }

            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(String s) {
                String listString = null;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Object resultsObj = jsonObject.opt("results");
                    if (resultsObj instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) resultsObj;
                        listString = jsonArray.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                    return;
                }

                if (listString != null) {
                    List<Movie> movies = JsonConverter.convert(listString, new TypeReference<List<Movie>>() {
                    });
                    subscriber.onNext(movies);
                } else {
                    subscriber.onNext(null);
                }
            }
        });
        getMainController().getBackgroundExecutor().execute(fetchNetworkDataTask);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        if (enableCached) {
            movieCache.put(fetchNetworkDataTask.key(), movies);
        }
    }

    public List<Movie> getMoviesFromCache() {
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
