package com.frodo.app.android.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.frodo.app.android.ui.FragmentScheduler;

import java.util.List;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getIntent() != null
                && getIntent().getExtras() != null
                && getIntent().getExtras().getBoolean("needActivityResult", false)) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                if (f != null) {
                    f.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }
}
