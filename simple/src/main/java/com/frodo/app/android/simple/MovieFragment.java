package com.frodo.app.android.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.android.ui.fragment.StatedFragment;

import java.util.List;

import rx.Subscription;
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
    public void onFirstTimeLaunched() {
        final ServerConfiguration serverConfiguration = (ServerConfiguration) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfiguration);
        loadMovies();
    }

    @Override
    public void onSaveState(Bundle outState) {
//        List<Movie> movies = getModel().getMovies();
//        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movies);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
//        List<Movie> movies = savedInstanceState.getParcelableArrayList("movies");

        final ServerConfiguration serverConfiguration = (ServerConfiguration) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfiguration);

        List<Movie> movies = getModel().getMovies();
        getUIView().showMovieList(movies);
    }

    /**
     * Use RxJava for callback
     */
    public void loadMovies() {
        getModel().setEnableCached(true);
        Subscription subscription = getModel().loadMoviesWithReactor().subscribeOn(Schedulers.io())
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
//  TODO: 2016/6/14 can unsubscribe
//        subscription.unsubscribe();
    }
}
