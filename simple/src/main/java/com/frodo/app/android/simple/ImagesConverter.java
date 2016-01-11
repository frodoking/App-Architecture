package com.frodo.app.android.simple;

import com.frodo.app.android.simple.cloud.amdb.entities.Configuration;

/**
 * Created by frodo on 2015/8/18.
 */
public class ImagesConverter {
    public static String getAbsoluteUrl(Configuration.ImagesConfiguration imagesConfiguration, String relativeUrl) {
        return imagesConfiguration.base_url + imagesConfiguration.poster_sizes.get(2) + relativeUrl;
    }
}
