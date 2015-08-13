package com.frodo.android.app.simple;

import com.frodo.android.app.simple.cloud.amdb.entities.Configuration;
import com.frodo.android.app.simple.cloud.amdb.services.ConfigurationService;
import com.frodo.android.app.simple.entities.amdb.TmdbConfiguration;
import com.frodo.android.app.simple.entities.amdb.mapper.TmdbConfigurationMapper;

/**
 * get configuration from server
 * Created by frodo on 2015/7/24.
 */
public class FetchTmdbConfigurationTask extends AbstractFetchTask<ConfigurationService, Configuration, TmdbConfiguration> {

    public FetchTmdbConfigurationTask(ConfigurationService service,
                                      OnFetchFinishedListener<TmdbConfiguration> l) {
        super(service, l);
    }

    @Override
    public final Configuration doBackgroundCall() throws Exception {
        return getService().configuration();
    }

    @Override
    public final void onSuccess(Configuration result) {
        final TmdbConfigurationMapper mapper = new TmdbConfigurationMapper();
        final TmdbConfiguration tmdbConfiguration = mapper.transform(result);
        getListener().onSuccess(tmdbConfiguration);
    }
}
