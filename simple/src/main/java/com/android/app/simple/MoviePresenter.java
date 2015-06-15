package com.android.app.simple;

import java.util.List;

import com.android.app.core.UIView;
import com.android.app.framework.controller.AbstractPresenter;
import com.android.app.framework.controller.MainController;

/**
 * Created by frodo on 2015/4/2.
 */
public class MoviePresenter extends AbstractPresenter {

    protected MoviePresenter(MovieView view) {
        super(view);
    }

    @Override
    public void attachMainControllerToModel(MainController mainController) {
        setModel(new MovieModel(mainController));
    }

    public void setMovies(List<Movie> movies) {
        MovieModel movieModel = (MovieModel) getModel();
        movieModel.saveMovie(movies.get(0));
    }

    public interface MovieView extends UIView {
        void showMovieList(List<Movie> movies);
    }
}
