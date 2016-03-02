package com.frodo.app.android.simple;

import com.frodo.app.framework.entity.BeanNode;

/**
 * Created by frodo on 2015/8/18.
 */
public class ImagesConverter {
    public static String getAbsoluteUrl(BeanNode imagesConfigurationNode, BeanNode movieNode) {
        BeanNode imagesConfigurationNode2 = imagesConfigurationNode.findBeanNodeByName("images");

        return imagesConfigurationNode2.findBeanNodeByName("base_url").toString()
                + imagesConfigurationNode2.findBeanNodeByName("poster_sizes").getChildArray().get(2)
                + movieNode.findBeanNodeByName("imageUrl");
    }
}
