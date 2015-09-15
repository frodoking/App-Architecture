package com.frodo.android.app.simple;

import com.frodo.android.app.core.task.AndroidFetchAndMapperNetworkDataTask;
import com.frodo.android.app.framework.exception.HttpException;
import com.frodo.android.app.simple.cloud.amdb.entities.MovieResultsPage;
import com.frodo.android.app.simple.cloud.amdb.services.MoviesService;
import com.frodo.android.app.simple.entities.amdb.Movie;
import com.frodo.android.app.simple.entities.amdb.mapper.MovieMapper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by frodo on 2015/8/13.
 */
public class FetchMoviesWithRxjavaTask extends AndroidFetchAndMapperNetworkDataTask<MoviesService, MovieResultsPage, List<Movie>> {
    private String requestParams = "";

    protected FetchMoviesWithRxjavaTask(MoviesService service, Subscriber<List<Movie>> subscriber) {
        super(service, subscriber);
    }

    @Override
    public final MovieResultsPage doBackgroundCall() throws HttpException {
        requestParams = "page=1&lang=zh";
        return getService().popular(1, "zh");
    }

    @Override
    public void onSuccess(MovieResultsPage result) {
        MovieMapper movieMapper = new MovieMapper();

        List<Movie> currentMovies = new ArrayList<>();
        for (com.frodo.android.app.simple.cloud.amdb.entities.Movie movie : result.results) {
            currentMovies.add(movieMapper.transform(movie));
        }

        getSubscriber().onNext(currentMovies);
    }

    @Override
    public final String key() {
        return "movies?" + requestParams;
    }
}
