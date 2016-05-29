package com.frodo.app.android.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frodo.android.app.simple.R;
import com.frodo.app.android.core.UIView;
import com.frodo.app.android.simple.entity.Movie;
import com.frodo.app.android.ui.fragment.AbstractBaseFragment;

/**
 * Created by frodo on 2015/7/10.
 */
public class MovieDetailFragment extends AbstractBaseFragment {

    private Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        movie = (Movie) bundle.getSerializable("movie");
    }

    @Override
    public UIView createUIView(Context context, LayoutInflater inflater, ViewGroup container) {
        return new UIView(null, inflater, container, R.layout.layout_movie_detail) {
            @Override
            public void initView() {
                TextView tv = (TextView) getRootView().findViewById(R.id.imei);
                tv.setText(movie.title);
            }

            @Override
            public void registerListener() {
            }
        };
    }
}
