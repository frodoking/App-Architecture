package com.frodo.app.android.core.toolbox;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Arrays;

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

    @NonNull
    public static Drawable getSelectableDrawableFor(int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ColorDrawable(lightenOrDarken(color, 0.20D))
            );
            stateListDrawable.addState(
                    new int[]{android.R.attr.state_focused},
                    new ColorDrawable(lightenOrDarken(color, 0.40D))
            );
            stateListDrawable.addState(
                    new int[]{},
                    new ColorDrawable(color)
            );
            return stateListDrawable;
        } else {
            ColorStateList pressedColor = ColorStateList.valueOf(lightenOrDarken(color, 0.2D));
            ColorDrawable defaultColor = new ColorDrawable(color);
            Drawable rippleColor = getRippleColor(color);
            return new RippleDrawable(
                    pressedColor,
                    defaultColor,
                    rippleColor
            );
        }
    }

    @NonNull
    private static Drawable getRippleColor(int color) {
        float[] outerRadii = new float[8];
        Arrays.fill(outerRadii, 3);
        RoundRectShape r = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(r);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    private static int lightenOrDarken(int color, double fraction) {
        if (canLighten(color, fraction)) {
            return lighten(color, fraction);
        } else {
            return darken(color, fraction);
        }
    }

    private static int lighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private static int darken(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);

        return Color.argb(alpha, red, green, blue);
    }

    private static boolean canLighten(int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return canLightenComponent(red, fraction)
                && canLightenComponent(green, fraction)
                && canLightenComponent(blue, fraction);
    }

    private static boolean canLightenComponent(int colorComponent, double fraction) {
        int red = Color.red(colorComponent);
        int green = Color.green(colorComponent);
        int blue = Color.blue(colorComponent);
        return red + (red * fraction) < 255
                && green + (green * fraction) < 255
                && blue + (blue * fraction) < 255;
    }

    private static int darkenColor(int color, double fraction) {
        return (int) Math.max(color - (color * fraction), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }
}

