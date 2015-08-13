package com.frodo.android.app.simple;

import com.frodo.android.app.framework.controller.AbstractModel;
import com.frodo.android.app.framework.controller.MainController;
import com.frodo.android.app.simple.cloud.amdb.services.ConfigurationService;
import com.frodo.android.app.simple.entities.amdb.TmdbConfiguration;

import android.text.TextUtils;

import rx.Subscriber;
import rx.observers.SafeSubscriber;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private FetchTmdbConfigurationTask fetchTmdbConfigurationTask;
    private FetchTmdbConfigurationWithRxjavaTask fetchTmdbConfigurationWithRxjavaTask;
    private TmdbConfiguration tmdbConfiguration;

    public ConfigurationModel(MainController controller) {
        super(controller);
        ConfigurationService configurationService =
                controller.getNetworkInteractor().create(ConfigurationService.class);
        fetchTmdbConfigurationTask = new FetchTmdbConfigurationTask(configurationService,
                new OnFetchFinishedListener<TmdbConfiguration>() {
                    @Override
                    public void onError(String errorMsg) {
                    }

                    @Override
                    public void onSuccess(TmdbConfiguration resultObject) {
                        setTmdbConfiguration(resultObject);
                    }
                });

        fetchTmdbConfigurationWithRxjavaTask = new FetchTmdbConfigurationWithRxjavaTask(configurationService, new Subscriber<TmdbConfiguration>(){
            @Override
            public void onNext(TmdbConfiguration tmdbConfiguration) {
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
                && !TextUtils.isEmpty(tmdbConfiguration.imagesBaseUrl)
                && tmdbConfiguration.imagesBackdropSizes != null
                && tmdbConfiguration.imagesPosterSizes != null
                && tmdbConfiguration.imagesProfileSizes != null;
    }

    public void loadServerConfig() {
        getMainController().getBackgroundExecutor().execute(/*fetchTmdbConfigurationTask*/fetchTmdbConfigurationWithRxjavaTask);
    }

    public void setTmdbConfiguration(TmdbConfiguration tmdbConfiguration) {
        this.tmdbConfiguration = tmdbConfiguration;
        getMainController().getConfig().setServerConfig(tmdbConfiguration);
    }
}
