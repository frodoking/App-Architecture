package com.frodo.app.android.ui.activity;

import android.content.Intent;
import android.net.Uri;

import com.frodo.app.android.ui.FragmentScheduler;

/**
 * Created by frodo on 2016/5/26.
 */
public class RedirectActivity extends FragmentContainerActivity {
    @Override
    public void initView() {
        Intent intent = getIntent();
        Uri uri = intent.getData();
        String uriString = uri.toString();
        if (uriString.startsWith(FragmentScheduler.SCHEME)) {
            FragmentScheduler.doReplace(this, uri.toString(), intent.getExtras());
        } else {
            throw new IllegalArgumentException("you must to define your schema like 'app://redirect/xxxx'");
        }
    }

    @Override
    public void registerListener() {
    }

    @Override
    public void initBusiness() {
    }
}
