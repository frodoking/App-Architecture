package com.frodo.app.android.simple;

import android.text.TextUtils;

import com.frodo.app.android.core.task.AndroidFetchNetworkDataTask;
import com.frodo.app.android.core.toolbox.JsonConverter;
import com.frodo.app.android.simple.entity.ServerConfiguration;
import com.frodo.app.framework.controller.AbstractModel;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private Observable<ServerConfiguration> observable;
    private ServerConfiguration serverConfiguration;

    public ConfigurationModel(MainController controller) {
        super(controller);

        observable = Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                Request request = new Request.Builder<ResponseBody>()
                        .method("GET")
                        .relativeUrl(Path.configuration)
                        .build();
                getMainController().getBackgroundExecutor().execute(new AndroidFetchNetworkDataTask(getMainController().getNetworkTransport(), request, subscriber));
            }
        }).map(new Func1<Response, ServerConfiguration>() {
            @Override
            public ServerConfiguration call(Response response) {
                try {
                    return JsonConverter.convert(((ResponseBody) response.getBody()).string(), ServerConfiguration.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
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
    public void initBusiness() {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ServerConfiguration>() {
                    @Override
                    public void call(ServerConfiguration serverConfiguration) {
                        setTmdbConfiguration(serverConfiguration);
                    }
                });
    }
}
