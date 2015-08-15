package com.frodo.android.app.simple;

import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.cloud.amdb.services.ConfigurationService;

import android.text.TextUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private FetchTmdbConfigurationWithRxjavaTask fetchTmdbConfigurationWithRxjavaTask;
    private Configuration tmdbConfiguration;

    public ConfigurationModel(MainController controller) {
        super(controller);
        ConfigurationService configurationService =
                controller.getNetworkInteractor().create(ConfigurationService.class);

        fetchTmdbConfigurationWithRxjavaTask = new FetchTmdbConfigurationWithRxjavaTask(configurationService, new Subscriber<Configuration>() {
            @Override
            public void onNext(Configuration tmdbConfiguration) {
                setTmdbConfiguration(tmdbConfiguration);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    public boolean isValid() {
        return tmdbConfiguration != null
                && !TextUtils.isEmpty(tmdbConfiguration.images.base_url)
                && isListEmpty(tmdbConfiguration.images.backdrop_sizes)
                && isListEmpty(tmdbConfiguration.images.poster_sizes)
                && isListEmpty(tmdbConfiguration.images.profile_sizes);
    }

    private boolean isListEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public void loadServerConfig() {
        getMainController().getBackgroundExecutor().execute(/*fetchTmdbConfigurationTask*/fetchTmdbConfigurationWithRxjavaTask);
    }

    public void setTmdbConfiguration(Configuration tmdbConfiguration) {
        this.tmdbConfiguration = tmdbConfiguration;
        getMainController().getConfig().setServerConfig(tmdbConfiguration);
    }
}
