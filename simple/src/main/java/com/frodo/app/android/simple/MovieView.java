package com.frodo.app.android.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.AndroidUIViewController;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.core.toolbox.FragmentScheduler;
import com.frodo.app.android.core.toolbox.ScreenUtils;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.android.ui.activity.FragmentContainerActivity;
import com.frodo.app.framework.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frodo on 2015/9/14.
 */
public class MovieView extends UIView {
    private BaseAdapter movieAdapter;
    private GridView gridView;
    private int[] imageSize;
    private ServerConfiguration serverConfig;
    private List<Movie> movies = new ArrayList<>();

    public MovieView(AndroidUIViewController presenter, LayoutInflater inflater, ViewGroup container, int layoutResId) {
        super(presenter, inflater, container, layoutResId);
    }

    public void setServerConfig(ServerConfiguration serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public void initView() {
        imageSize = calcPosterSize();
        gridView = (GridView) getRootView().findViewById(R.id.gridview);

        movieAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return movies.size();
            }

            @Override
            public Movie getItem(int position) {
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
                    convertView = LayoutInflater.from(getPresenter().getAndroidContext()).inflate(R.layout.layout_movie_item, null);
                    holder = new ViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                Movie movie = getItem(position);
                final String imageUrl = ImagesConverter.getAbsoluteUrl(serverConfig, movie);
                Logger.fLog().tag("MovieView").i("Glide loading image : " + imageUrl);
                if (imageUrl != null) {
                    Glide.with(getPresenter().getAndroidContext())
                            .load(imageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .centerCrop()
                            .override(imageSize[0], imageSize[1])
                            .into(holder.imageView);
                }

                holder.textView.setText(movie.title);

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
                FragmentScheduler.nextFragment(((FragmentContainerActivity) getPresenter().getAndroidContext()), RedirectFragment.class, null, false);
            }
        });
    }

    private int[] calcPosterSize() {
        int itemWidth = (ScreenUtils.getScreenWidth(getPresenter().getAndroidContext()) - 4 * 20) / 3;
        int itemHeight = itemWidth * 278 / 135;
        return new int[]{itemWidth, itemHeight};
    }

    public void showMovieList(List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        movieAdapter.notifyDataSetChanged();
    }

    public void showError(String errorMsg) {
        if (isOnShown()) {
            Toast.makeText(getPresenter().getAndroidContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
