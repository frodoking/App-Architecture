package com.android.app.simple;

import com.android.app.framework.controller.AbstractModel;
import com.android.app.framework.controller.MainController;

import java.util.List;

/**
 * Created by frodo on 2015/4/2.
 */
public class MovieModel extends AbstractModel {

    public MovieModel(MainController controller) {
        super(controller);
    }

    public List<Movie> getMovies() {
        return null;
    }

    public void saveMovie(Movie movie) {

    }

}
