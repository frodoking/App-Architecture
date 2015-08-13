package com.frodo.android.app.simple;

import java.util.ArrayList;
import java.util.List;

import com.frodo.android.app.core.toolbox.ScreenUtils;
import com.frodo.android.app.simple.entities.amdb.Movie;
import com.frodo.android.app.simple.entities.amdb.TmdbConfiguration;
import com.frodo.android.app.ui.fragment.AbstractBaseFragment;
import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * a simple for movie
 * Created by frodo on 2015/4/2.
 */
public class MovieFragment extends AbstractBaseFragment implements MoviePresenter.MovieView {
    private List<Movie> movies = new ArrayList<>();
    private BaseAdapter movieAdapter;
    private MoviePresenter presenter;
    private GridView gridView;

    private int[] imageSize;

    @Override
    public void onCreatePresenter() {
        presenter = new MoviePresenter(this);
        presenter.attachMainControllerToModel(getMainController());
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_movie_list;
    }

    @Override
    public void initView() {
        imageSize = calcPosterSize();
        gridView = (GridView) getView().findViewById(R.id.gridview);

        movieAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return movies.size();
            }

            @Override
            public Object getItem(int position) {
                return movies.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_movie_item, null);
                    holder = new ViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                Movie movie = (Movie) getItem(position);
                final TmdbConfiguration serverConfig =
                        (TmdbConfiguration) getMainController().getConfig().serverConfig();
                final String baseImageUrl = serverConfig.imagesBaseUrl + 'w' + serverConfig.imagesPosterSizes[2];
                final String imageUrl = baseImageUrl + movie.imageUrl;
                printLog("Picasso loading image : " + imageUrl);
                Picasso.with(getActivity()).load(imageUrl).centerCrop().resize(imageSize[0], imageSize[1])
                        .into(holder.imageView);
                holder.textView.setText(movie.name);

                return convertView;
            }

            class ViewHolder {
                ImageView imageView;
                TextView textView;
            }
        };

        gridView.setAdapter(movieAdapter);
    }

    @Override
    public void registerListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // only for test redirect function
                redirectNextFragment(RedirectFragment.class, null, false);
            }
        });
    }

    @Override
    public void initBusiness() {
//        presenter.loadMovies();
        presenter.loadMoviesWithRxjava();
    }

    @Override
    public MoviePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showMovieList(final List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMsg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    private int[] calcPosterSize() {
        int itemWidth = (ScreenUtils.getScreenWidth(getActivity()) - 4 * 20) / 3;
        int itemHeight = itemWidth * 278 / 135;
        return new int[]{itemWidth, itemHeight};
    }
}
