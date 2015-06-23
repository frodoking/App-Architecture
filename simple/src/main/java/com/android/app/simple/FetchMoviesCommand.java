package com.android.app.simple;

import java.util.ArrayList;
import java.util.List;

import com.android.app.core.cmd.AndroidNetworkCommand;
import com.android.app.framework.controller.Notifier;
import com.jakewharton.trakt.entities.Movie;
import com.jakewharton.trakt.services.MoviesService;

/**
 * Created by frodo on 2015/4/2.
 */
public class FetchMoviesCommand extends AndroidNetworkCommand<MoviesService> {

    protected FetchMoviesCommand(MoviesService service, Notifier notifier) {
        super(service, notifier);
    }

    @Override
    public void execute() {
        super.execute();
        List<Movie> movies = getService().trending();
        MovieMapper movieMapper = new MovieMapper();

        List<com.android.app.simple.Movie> currentMovies = new ArrayList<>();
        for (Movie movie : movies) {
            currentMovies.add(movieMapper.map(movie));
        }

        getNotifier().onNotify(currentMovies);
    }
}
