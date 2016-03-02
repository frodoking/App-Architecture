package com.frodo.app.android.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.ui.fragment.StatedFragment;
import com.frodo.app.framework.entity.BeanNode;

import org.json.JSONArray;
import org.json.JSONException;

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
public class MovieFragment extends StatedFragment<MovieView, MovieModel> {
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
    protected void onFirstTimeLaunched() {
        final BeanNode serverConfigBeanNode = (BeanNode) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfigBeanNode);
        loadMoviesWithRxjava();
    }

    @Override
    protected void onSaveState(Bundle outState) {
        List<BeanNode> movies = getModel().getMovies();
        try {
            outState.putString("moviesJson", new ObjectMapper().writeValueAsString(movies));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        final BeanNode serverConfigNode = (BeanNode) getMainController().getConfig().serverConfig();
        getUIView().setServerConfig(serverConfigNode);
        if (savedInstanceState != null) {
            String moviesJson = savedInstanceState.getString("moviesJson");
            List<BeanNode> movies = null;
            try {
                movies = JsonConverter.convert(new JSONArray(moviesJson));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getUIView().showMovieList(movies);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    /**
     * Use RxJava for callback
     */
    public void loadMoviesWithRxjava() {
        getModel().setEnableCached(true);
        Observable.from(DEFAULT)
                .flatMap(new Func1<String, Observable<BeanNode>>() {
                    @Override
                    public Observable<BeanNode> call(String s) {
                        return Observable.create(new Observable.OnSubscribe<BeanNode>() {
                            @Override
                            public void call(Subscriber<? super BeanNode> subscriber) {
                                getModel().loadMoviesWithRxjava((Subscriber<BeanNode>) subscriber);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BeanNode>() {
                    @Override
                    public void call(BeanNode result) {
                        List<BeanNode> movies = result.findBeanNodeByName("data").getChildArray();
                        getUIView().showMovieList(movies);
                        getModel().setMovies(movies);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (getModel().isEnableCached()) {
                            List<BeanNode> movies = getModel().getMoviesFromCache();
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
