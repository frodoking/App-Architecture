package com.frodo.android.app.simple;

import java.util.List;

import com.frodo.android.app.core.UIView;
import com.frodo.android.app.framework.controller.AbstractPresenter;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.entities.amdb.Movie;

/**
 * Created by frodo on 2015/4/2.
 */
public class MoviePresenter extends AbstractPresenter {

    private MovieView movieView;
    private MovieModel movieModel;

    protected MoviePresenter(MovieView view) {
        super(view);
        this.movieView = view;
    }

    @Override
    public void attachMainControllerToModel(MainController mainController) {
        movieModel = new MovieModel(mainController);
        setModel(movieModel);
    }

    public void loadMovies() {
        movieModel = (MovieModel) getModel();
        movieModel.setEnableCached(true);
        movieModel.loadMovies(new MovieModel.OnFetchMoviesFinishedListener() {
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
    }

    public interface MovieView extends UIView {
        void showMovieList(List<Movie> movies);

        void showError(String errorMsg);
    }
}
