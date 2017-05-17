package com.frodo.app.android.simple;

import com.google.gson.reflect.TypeToken;

import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.framework.cache.Cache;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

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

    public Observable<List<Movie>> loadMoviesWithReactor(final ServerConfiguration serverConfiguration) {
        return Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> observableEmitter) throws Exception {
                Request request = new Request.Builder<ResponseBody>()
                        .method("GET")
                        .relativeUrl(Path.movie_popular)
                        .build();
                request.addQueryParam("page", "1");
                request.addQueryParam("lang", "zh");
                fetchNetworkDataTask = new AndroidFetchNetworkDataTask(getMainController().getNetworkTransport(), request, observableEmitter);
                getMainController().getBackgroundExecutor().execute(fetchNetworkDataTask);
            }
        }).map(new Function<Response, List<Movie>>() {
            @Override
            public List<Movie> apply(@NonNull Response response) throws Exception {
                String listString = null;
                try {
                    JSONObject jsonObject = new JSONObject(((ResponseBody) response.getBody()).string());
                    Object resultsObj = jsonObject.opt("results");
                    if (resultsObj instanceof JSONArray) {
                        JSONArray jsonArray = (JSONArray) resultsObj;
                        listString = jsonArray.toString();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                List<Movie> movies = JsonConverter.convert(listString, new TypeToken<List<Movie>>() {
                }.getType());

                if (movies != null && !movies.isEmpty()) {
                    for (Movie movie : movies) {
                        movie.posterPath = ImagesConverter.getAbsoluteUrl(serverConfiguration, movie);
                    }
                }

                return movies;

            }
        });
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
}
