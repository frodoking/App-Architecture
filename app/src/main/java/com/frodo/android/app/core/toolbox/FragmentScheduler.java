package com.frodo.android.app.core.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.frodo.android.app.ui.activity.FragmentContainerActivity2;

/**
 * Created by frodo on 2015/9/14. fragment Scheduler
 */
public class FragmentScheduler {
    /**
     * 跳转到下一个Fragment
     *
     * @param clazz
     * @param extra
     * @param isFinished
     */
    public static void redirectNextFragment(Activity activity, Class<?> clazz, Bundle extra, boolean isFinished) {
        Intent intent = new Intent(activity, FragmentContainerActivity2.class);
        if (extra == null) {
            extra = new Bundle();
            if (clazz != null) {
                extra.putString("fragment_class_name", clazz.getCanonicalName());
            }
        }
        intent.putExtra("extra", extra);
        activity.startActivity(intent);
        if (isFinished) {
            activity.finish();
        }
    }
}
