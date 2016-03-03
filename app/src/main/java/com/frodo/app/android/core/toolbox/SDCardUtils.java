package com.frodo.app.android.core.toolbox;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Provide some function to operator sdcard
 * Created by frodo on 2015/7/15.
 */
public class SDCardUtils {
    private SDCardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * is mounted
     *
     * @return boolean
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * get sdcard absolute path
     *
     * @return String
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * get sdcard all size
     *
     * @return long
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * get unused size by bytes
     *
     * @param filePath String
     */
    public static long getFreeBytes(String filePath) {
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * get root dir
     *
     * @return String
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
