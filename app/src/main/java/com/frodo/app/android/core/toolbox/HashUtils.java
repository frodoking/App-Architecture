package com.frodo.app.android.core.toolbox;

import java.util.Locale;

/**
 * Created by frodo on 2015/9/1. hash key to another key
 */
public class HashUtils {
    public static String computeWeakHash(String string) {
        return String.format(Locale.US, "%08x%08x", string.hashCode(), string.length());
    }
}
