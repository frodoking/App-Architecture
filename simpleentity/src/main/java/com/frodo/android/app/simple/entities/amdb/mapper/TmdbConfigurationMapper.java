package com.frodo.android.app.simple.entities.amdb.mapper;

import java.util.Collection;
import java.util.List;

import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.entities.amdb.TmdbConfiguration;

/**
 * Created by frodo on 2015/7/24.
 */
public class TmdbConfigurationMapper implements EntityMapper<Configuration, TmdbConfiguration> {

    @Override
    public TmdbConfiguration transform(Configuration configuration) {
        TmdbConfiguration tmdbConfiguration = new TmdbConfiguration();
        tmdbConfiguration.lastFetchTime = System.currentTimeMillis();
        tmdbConfiguration.imagesBaseUrl = configuration.images.base_url;
        tmdbConfiguration.imagesBackdropSizes = convertTmdbImageSizes(configuration.images.backdrop_sizes);
        tmdbConfiguration.imagesPosterSizes = convertTmdbImageSizes(configuration.images.poster_sizes);
        tmdbConfiguration.imagesProfileSizes = convertTmdbImageSizes(configuration.images.profile_sizes);
        return tmdbConfiguration;
    }

    @Override
    public Collection<TmdbConfiguration> transform(Collection<Configuration> entityCollection) {
        return null;
    }

    @Override
    public Configuration transformJsonToEntity(String jsonString) {
        return null;
    }

    @Override
    public Collection<Configuration> transformJsonArrayToEntity(String jsonArrayString) {
        return null;
    }

    @Override
    public String transformEntityToJsonString() {
        return null;
    }

    private static int[] convertTmdbImageSizes(List<String> stringSizes) {
        int[] intSizes = new int[stringSizes.size() - 1];
        for (int i = 0; i < intSizes.length; i++) {
            String size = stringSizes.get(i);
            if (size.charAt(0) == 'w') {
                intSizes[i] = Integer.parseInt(size.substring(1));
            }
        }
        return intSizes;
    }
}
