package com.frodo.app.android.simple;

import android.text.TextUtils;

import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.net.Request;

import rx.Subscriber;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private AndroidFetchNetworkDataTask fetchNetworkDataTask;
    private ServerConfiguration serverConfiguration;

    public ConfigurationModel(MainController controller) {
        super(controller);
        Request request = new Request("GET", Path.configuration);
        fetchNetworkDataTask = new AndroidFetchNetworkDataTask(controller.getNetworkTransport(), request, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String result) {
                serverConfiguration = JsonConverter.convert(result, ServerConfiguration.class);
                setTmdbConfiguration(serverConfiguration);
            }
        });
    }

    public boolean isValid() {
        return serverConfiguration != null
                && !TextUtils.isEmpty(serverConfiguration.images.baseUrl)
                && isValidArray(serverConfiguration.images.backdropSizes)
                && isValidArray(serverConfiguration.images.posterSizes)
                && isValidArray(serverConfiguration.images.profileSizes);
    }

    private static boolean isValidArray(Object[] array) {
        return array != null && array.length > 0;
    }

    public void setTmdbConfiguration(ServerConfiguration configuration) {
        this.serverConfiguration = configuration;
        getMainController().getConfig().setServerConfig(configuration);
    }

    @Override
    public String name() {
        return "ConfigurationModel";
    }

    @Override
    public void initBusiness() {
        getMainController().getBackgroundExecutor().execute(fetchNetworkDataTask);
    }
}
