package com.frodo.app.android.simple;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.android.ui.fragment.StatedFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        loadMovies();
    }

    @Override
    public void onSaveState(Bundle outState) {
        List<Movie> movies = getModel().getMovies();
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movies);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList("movies");

        getUIView().showMovieList(movies);
    }

    /**
     * Use RxJava for callback
     */
    public void loadMovies() {
        final ServerConfiguration serverConfiguration = (ServerConfiguration) getMainController().getConfig().serverConfig();
        getModel().setEnableCached(true);
        Disposable disposable = getModel().loadMoviesWithReactor(serverConfiguration).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(@NonNull List<Movie> movies) throws Exception {
                        getUIView().showMovieList(movies);
                        getModel().setMovies(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
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
        //  TODO: 2016/6/14 can dispose
//        disposable.dispose();
    }
}
