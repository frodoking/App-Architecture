package com.android.app.simple;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.android.app.framework.controller.MainController;
import com.android.app.ui.fragment.AbstractBaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a simple for movie
 * Created by frodo on 2015/4/2.
 */
public class MovieFragment extends AbstractBaseFragment implements MoviePresenter.MovieView {
    private MoviePresenter presenter;
    private GridView gridView;
    List<Map<String, Object>> movies = new ArrayList<>();
    SimpleAdapter movieAdapter;

    @Override
    public void onCreatePresenter() {
        presenter = new MoviePresenter(this) {
            @Override
            public MovieModel createModel() {
                return new MovieModel() {
                    @Override
                    public MainController getMainController() {
                        return MovieFragment.this.getMainController();
                    }
                };
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_movie_list;
    }

    @Override
    public void initView() {
        gridView = (GridView) getView().findViewById(R.id.gridview);
        movieAdapter = new SimpleAdapter(getActivity(),
                movies,
                R.layout.layout_movie_item,
                new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.ItemImage, R.id.ItemText});
        gridView.setAdapter(movieAdapter);
    }

    @Override
    public void registerListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void initBusiness() {
        presenter.requestLatestMoives();
    }

    @Override
    public MoviePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showMovieList(final List<Movie> movies) {
        getView().post(new Runnable() {
            @Override
            public void run() {
                movies.clear();
                movies.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            }
        });
    }
}
