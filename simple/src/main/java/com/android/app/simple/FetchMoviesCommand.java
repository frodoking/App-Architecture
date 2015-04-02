package com.android.app.simple;

import com.android.app.framework.command.AbstractCommand;
import com.android.app.framework.controller.Notifier;
import com.jakewharton.trakt.entities.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<Map<String, String>> currentMovies = new ArrayList<>();
        for (Movie movie : movies) {
            com.android.app.simple.Movie currentMovie = movieMapper.map(movie);

            Map<String, String> map = new HashMap<>();
            map.put("ItemImage", currentMovie.imageUrl);
            map.put("ItemText", currentMovie.name);
            currentMovies.add(map);
        }

        getNotifier().onNotify(currentMovies);
    }
}
