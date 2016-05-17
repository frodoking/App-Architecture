package com.frodo.app.android.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.android.ui.fragment.StatedFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * a simple for movie
 * Created by frodo on 2015/4/2.
 */
public class MovieFragment extends StatedFragment<MovieView, MovieModel> {

    @Override
    public MovieView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new MovieView(this, inflater, container);
    }

    @Override
    protected void onFirstTimeLaunched() {
        final ServerConfiguration serverConfiguration = (ServerConfiguration) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfiguration);
        loadMoviesWithRxjava();
    }

    @Override
    protected void onSaveState(Bundle outState) {
        List<Movie> movies = getModel().getMovies();
        try {
            outState.putString("moviesJson", new ObjectMapper().writeValueAsString(movies));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        final ServerConfiguration serverConfiguration = (ServerConfiguration) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfiguration);
        if (savedInstanceState != null) {
            String moviesJson = savedInstanceState.getString("moviesJson");
            List<Movie> movies = JsonConverter.convert(moviesJson, new TypeReference<List<Movie>>() {
            });
            getUIView().showMovieList(movies);
        }
    }

    /**
     * Use RxJava for callback
     */
    public void loadMoviesWithRxjava() {
        getModel().setEnableCached(true);

        Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                getModel().loadMoviesWithRxjava(subscriber);
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> result) {
                        getUIView().showMovieList(result);
                        getModel().setMovies(result);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (getModel().isEnableCached()) {
                            List<Movie> movies = getModel().getMoviesFromCache();
                            if (movies != null) {
                                getUIView().showMovieList(movies);
                                return;
                            }
                        }
                        getUIView().showError(throwable.getMessage());
                    }
                }
        );
    }
}
