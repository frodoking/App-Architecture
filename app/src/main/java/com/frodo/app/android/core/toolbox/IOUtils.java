package com.frodo.app.android.core.toolbox;

import android.database.Cursor;

/**
 * Created by frodo on 2015/9/6.
 */
import java.io.Closeable;

public class IOUtils {

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable e) {
            }
        }
    }
}
