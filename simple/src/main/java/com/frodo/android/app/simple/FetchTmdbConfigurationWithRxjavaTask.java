package com.frodo.android.app.simple;

import com.frodo.android.app.core.task.AndroidFetchTask;
import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.cloud.amdb.services.ConfigurationService;
import com.frodo.android.app.simple.entities.amdb.TmdbConfiguration;
import com.frodo.android.app.simple.entities.amdb.mapper.TmdbConfigurationMapper;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by frodo on 2015/8/13. use rxjava Observable & Subscriber
 */
public class FetchTmdbConfigurationWithRxjavaTask extends AndroidFetchTask<ConfigurationService, Configuration, TmdbConfiguration> {
    protected FetchTmdbConfigurationWithRxjavaTask(ConfigurationService service, Subscriber<TmdbConfiguration> subscriber) {
        super(service, subscriber);
    }

    @Override
    public final Configuration doBackgroundCall() throws Exception {
        return getService().configuration();
    }

    @Override
    public final void onSuccess(Configuration result) {
        final TmdbConfigurationMapper mapper = new TmdbConfigurationMapper();
        final TmdbConfiguration tmdbConfiguration = mapper.transform(result);
        Observable<TmdbConfiguration> observable = Observable.create(new Observable.OnSubscribe<TmdbConfiguration>() {
            @Override
            public void call(Subscriber<? super TmdbConfiguration> subscriber) {
                subscriber.onNext(tmdbConfiguration);
                subscriber.onCompleted();
            }
        });
        observable.subscribe(getSubscriber());
    }
}
