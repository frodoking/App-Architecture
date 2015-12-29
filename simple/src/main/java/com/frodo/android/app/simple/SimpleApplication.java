package com.frodo.android.app.simple;

import com.frodo.android.app.MicroApplication;
import com.frodo.android.app.core.config.AndroidConfig;
import com.frodo.android.app.core.log.AndroidLogCollectorSystem;
import com.frodo.android.app.core.network.AndroidNetworkSystem;
import com.frodo.android.app.core.toolbox.ResourceManager;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.config.Environment;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.exception.AbstractExceptionHandler.SimpleExceptionHandler;
import com.frodo.android.app.framework.exception.ExceptionHandler;
import com.frodo.android.app.framework.log.LogCollector;
import com.frodo.android.app.framework.net.NetworkTransport;
import com.frodo.android.app.framework.scene.DefaultScene;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.theme.Theme;
import com.frodo.android.app.simple.cloud.trakt.utils.Base64;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by frodo on 2015/4/2. for example
 */
public class SimpleApplication extends MicroApplication {

    private ConfigurationModel configurationModel;

    @Override
    public void init() {
        super.init();
        getMainController().getLogCollector().enableCollect(true);
    }

    @Override
    public LogCollector loadLogCollector() {
        return new AndroidLogCollectorSystem(getMainController()) {
            @Override
            public void uploadLeakBlocking(File file, String leakInfo) {
                final UploadFileToServerTask.FileWebService service =
                        getMainController().getNetworkTransport().create(UploadFileToServerTask.FileWebService.class);
                final UploadFileToServerTask task = new UploadFileToServerTask(service, file, null);
                getMainController().getBackgroundExecutor().execute(task);
            }
        };
    }

    @Override
    public Configuration loadConfiguration() {
        final Environment environment =
                new Environment(0, "tmdb", Constants.TMDB_ENDPOINT, Constants.TMDB_API_KEY, true);
        return new AndroidConfig(getMainController(), environment);
    }

    @Override
    public Scene loadScene() {
        return new DefaultScene();
    }

    @Override
    public Theme loadTheme() {
        return new Theme() {
            @Override
            public int themeColor() {
                return 0xff00fe;
            }
        };
    }

    @Override
    public NetworkTransport loadNetworkTransport() {
        return new SimpleAndroidNetworkSystem(getMainController(), null, null);
    }

    @Override
    public ExceptionHandler loadExceptionHandler() {
        return new SimpleExceptionHandler(getMainController()) ;
    }

    @Override
    public void loadServerConfiguration() {
        if (configurationModel == null) {
            configurationModel = new ConfigurationModel(getMainController());
        }

        if (!configurationModel.isValid()) {
            configurationModel.initBusiness();
        }
    }

    @Override
    public String applicationName() {
        return ResourceManager.getPackageInfo().packageName;
    }

    @Override
    public int versionCode() {
        return ResourceManager.getPackageInfo().versionCode;
    }

    @Override
    public String versionName() {
        return ResourceManager.getPackageInfo().versionName;
    }

    private class SimpleAndroidNetworkSystem extends AndroidNetworkSystem {
        private String userAccount;
        private String userPasswordSha1;

        public SimpleAndroidNetworkSystem(IController controller, String userAccount, String userPasswordSha1) {
            super(controller);
            this.userAccount = userAccount;
            this.userPasswordSha1 = userPasswordSha1;
        }

        @Override
        public RestAdapter.Builder newRestAdapterBuilder() {
            RestAdapter.Builder b = super.newRestAdapterBuilder();
            if (getController().getCacheSystem() != null) {
                OkHttpClient client = new OkHttpClient();

                File cacheDir = new File(getController().getCacheSystem().getCacheDir(), UUID.randomUUID().toString());
                Cache cache = new Cache(cacheDir, 1024);
                client.setCache(cache);

                client.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                client.setReadTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                client.setWriteTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                b.setClient(new OkClient(client));
            }

            return b;
        }

        @Override
        public RequestInterceptor getRequestInterceptor() {
            return new RequestInterceptor() {
                public void intercept(RequestFacade requestFacade) {
                    Environment env = getMainController().getConfig().getCurrentEnvironment();
                    if (env.getName().contains("tmdb")) {
                        requestFacade.addQueryParam("api_key", env.getApiKey());
                    } else if (env.getName().contains("trakt")) {
                        requestFacade.addQueryParam("apikey", env.getApiKey());
                    }
                    if (userAccount != null && userPasswordSha1 != null) {
                        String source = userAccount + ":" + userPasswordSha1;
                        String authorization = "Basic " + Base64.encodeBytes(source.getBytes());
                        requestFacade.addHeader("Authorization", authorization);
                    }
                }
            };
        }
    }
}
