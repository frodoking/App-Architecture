package com.frodo.app.android.core.toolbox;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/**
 * Created by frodo on 2016/6/8.
 */
public class DrawableProvider {
    public static void setDrawable(GradientDrawable drawable, int strokeColor, int strokeWidth, int solidColor, int radius) {
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setCornerRadius(radius);
        drawable.setColor(solidColor);
    }

    public static GradientDrawable createGradientDrawable(int solidColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(radius);
        drawable.setColor(solidColor);
        return drawable;
    }

    public static GradientDrawable createGradientDrawable(int strokeColor, int strokeWidth, int solidColor, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        setDrawable(drawable, strokeColor, strokeWidth, solidColor, radius);
        return drawable;
    }

    public static void fillViewBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}

