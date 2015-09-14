package com.frodo.android.app.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.entities.amdb.Movie;
import com.frodo.android.app.ui.fragment.AbstractBaseFragment;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * a simple for movie
 * Created by frodo on 2015/4/2.
 */
public class MovieFragment extends AbstractBaseFragment<MovieView, MovieModel> {
    private static final String[] DEFAULT = {"rxjava, movie"};

    @Override
    public MovieView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new MovieView(this, inflater, container, R.layout.layout_movie_list);
    }

    @Override
    public MovieModel createModel() {
        return new MovieModel(getMainController());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Configuration serverConfig = (Configuration) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfig);
        loadMoviesWithRxjava();
    }

    /**
     * Use RxJava for callback
     */
    public void loadMoviesWithRxjava() {
        getModel().setEnableCached(true);
        Observable.from(DEFAULT)
                .flatMap(new Func1<String, Observable<List<Movie>>>() {
                    @Override
                    public Observable<List<Movie>> call(String s) {
                        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
                            @Override
                            public void call(Subscriber<? super List<Movie>> subscriber) {
                                getModel().loadMoviesWithRxjava((Subscriber<List<Movie>>) subscriber);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movies) {
                        getUIView().showMovieList(movies);
                        getModel().setMovies(movies);
                    }
                }, new Action1<Throwable>() {
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
                });
    }
}
