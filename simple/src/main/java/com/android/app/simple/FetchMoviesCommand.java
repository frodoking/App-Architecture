package com.android.app.simple;

import com.android.app.framework.command.AbstractCommand;
import com.android.app.framework.controller.Notifier;
import com.jakewharton.trakt.entities.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frodo on 2015/4/2.
 */
public class FetchMoviesCommand extends AbstractCommand {
    protected FetchMoviesCommand(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void execute() {
        List<Movie> movies = PhilmTrakt.getDefault().moviesService().trending();
        MovieMapper movieMapper = new MovieMapper();

        List<com.android.app.simple.Movie> currentMovies = new ArrayList<>();
        for (Movie movie : movies) {
            currentMovies.add(movieMapper.map(movie));
        }

        getNotifier().onNotify(currentMovies);
    }
}
