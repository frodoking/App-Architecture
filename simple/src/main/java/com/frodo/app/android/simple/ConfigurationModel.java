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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by frodo on 2015/7/24.
 */
public class ConfigurationModel extends AbstractModel {

    private Observable<ServerConfiguration> observable;
    private ServerConfiguration serverConfiguration;

    public ConfigurationModel(MainController controller) {
        super(controller);
        observable = Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> observableEmitter) throws Exception {
                Request request = new Request.Builder<ResponseBody>()
                        .method("GET")
                        .relativeUrl(Path.configuration)
                        .build();
                getMainController().getBackgroundExecutor().execute(new AndroidFetchNetworkDataTask(getMainController().getNetworkTransport(), request, observableEmitter));
            }
        }).map(new Function<Response, ServerConfiguration>() {
            @Override
            public ServerConfiguration apply(@NonNull Response response) throws Exception {
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
                .subscribe(new Consumer<ServerConfiguration>() {
                    @Override
                    public void accept(@NonNull ServerConfiguration serverConfiguration) throws Exception {
                        setTmdbConfiguration(serverConfiguration);
                    }
                });
    }
}
