package com.android.app.simple;

import com.android.app.core.MainUINotifier;
import com.android.app.core.UIView;
import com.android.app.framework.command.MacroCommand;
import com.android.app.framework.controller.AbstractPresenter;

import java.util.List;

/**
 * Created by frodo on 2015/4/2.
 */
public class MoviePresenter extends AbstractPresenter {
    private MovieView movieView;

    protected MoviePresenter(MovieView view) {
        super(view);
    }

    public void requestLatestMoives() {
        MacroCommand.getDefault().executeAsync(new FetchMoviesCommand(new MainUINotifier(movieView.getActivity()) {
            @Override
            public void onUiNotify(Object... args) {
                movieView.showMovieList((List<Movie>) args[0]);
            }
        }));
    }

    @Override
    public MovieModel createModel() {
        return null;
    }

    public static interface MovieView extends UIView {
        void showMovieList(List<Movie> movies);
    }
}
