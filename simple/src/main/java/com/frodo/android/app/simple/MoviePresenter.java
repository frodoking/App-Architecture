package com.frodo.android.app.simple;

import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.AbstractPresenter;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.entities.amdb.Movie;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by frodo on 2015/4/2.
 */
public class MoviePresenter extends AbstractPresenter {
    private MovieView movieView;
    private MovieModel movieModel;

    private static final String[] DEFAULT = {"rxjava, movie"};

    protected MoviePresenter(MovieView view) {
        super(view);
        this.movieView = view;
    }

    @Override
    public void attachMainControllerToModel(MainController mainController) {
        movieModel = new MovieModel(mainController);
        setModel(movieModel);
    }
    /*
    public void loadMovies() {
        movieModel = (MovieModel) getModel();
        movieModel.setEnableCached(true);
        movieModel.loadMovies(new OnFetchFinishedListener<List<Movie>>() {
            @Override
            public void onError(String errorMsg) {
                if (movieModel.isEnableCached()) {
                    List<Movie> movies = movieModel.getMoviesFromCache();
                    if (movies != null) {
                        movieView.showMovieList(movies);
                        return;
                    }
                }

                movieView.showError(errorMsg);
            }

            @Override
            public void onSuccess(List<Movie> movies) {
                movieView.showMovieList(movies);
                movieModel.setMovies(movies);
            }
        });
    }*/

    /**
     * Use RxJava for callback
     */
    public void loadMoviesWithRxjava() {
        movieModel = (MovieModel) getModel();
        movieModel.setEnableCached(true);
        Observable.from(DEFAULT)
                .flatMap(new Func1<String, Observable<List<Movie>>>() {
                    @Override
                    public Observable<List<Movie>> call(String s) {
                        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
                            @Override
                            public void call(Subscriber<? super List<Movie>> subscriber) {
                                movieModel.loadMoviesWithRxjava((Subscriber<List<Movie>>) subscriber);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movies) {
                        movieView.showMovieList(movies);
                        movieModel.setMovies(movies);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (movieModel.isEnableCached()) {
                            List<Movie> movies = movieModel.getMoviesFromCache();
                            if (movies != null) {
                                movieView.showMovieList(movies);
                                return;
                            }
                        }
                        movieView.showError(throwable.getMessage());
                    }
                });
    }

    public interface MovieView extends UIView {
        void showMovieList(List<Movie> movies);

        void showError(String errorMsg);
    }
}
