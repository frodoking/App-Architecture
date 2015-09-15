package com.frodo.android.app.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frodo.android.app.core.AndroidUIViewController;
import com.frodo.android.app.core.UIView;
import com.frodo.android.app.core.toolbox.FragmentScheduler;
import com.frodo.android.app.core.toolbox.ScreenUtils;
import com.frodo.android.app.framework.log.Logger;
import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.entities.amdb.Movie;
import com.frodo.android.app.ui.activity.FragmentContainerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frodo on 2015/9/14.
 */
public class MovieView extends UIView {
    private List<Movie> movies = new ArrayList<>();
    private BaseAdapter movieAdapter;
    private GridView gridView;
    private int[] imageSize;
    private Configuration serverConfig;

    public MovieView(AndroidUIViewController presenter, LayoutInflater inflater, ViewGroup container, int layoutResId) {
        super(presenter, inflater, container, layoutResId);
    }

    public void setServerConfig(Configuration serverConfig) {
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
                    convertView = LayoutInflater.from(getPresenter().getAndroidContext()).inflate(R.layout.layout_movie_item, null);
                    holder = new ViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                Movie movie = (Movie) getItem(position);
                final String imageUrl = ImagesConverter.getAbsoluteUrl(serverConfig.images, movie.imageUrl);
                Logger.tag("MovieView").printLog("Picasso loading image : " + imageUrl);
                Picasso.with(getPresenter().getAndroidContext()).load(imageUrl).centerCrop().resize(imageSize[0], imageSize[1])
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
