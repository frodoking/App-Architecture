package com.frodo.android.app.simple;

import com.frodo.android.app.AppApplication;
import com.frodo.android.app.core.config.AndroidConfig;
import com.frodo.android.app.core.network.AndroidNetworkSystem;
import com.frodo.android.app.core.toolbox.ResourceManager;
import com.frodo.android.app.framework.config.Configuration;
import com.frodo.android.app.framework.config.Environment;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.net.NetworkInteractor;
import com.frodo.android.app.framework.scene.DefaultScene;
import com.frodo.android.app.framework.scene.Scene;
import com.frodo.android.app.framework.theme.Theme;
import com.jakewharton.trakt.util.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by frodo on 2015/4/2.
 */
public class SimpleApplication extends AppApplication {

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
    public NetworkInteractor loadNetworkInteractor() {
        return new SimpleAndroidNetworkSystem(getMainController(), null, null);
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

            //            if (mCacheLocation != null) {
            //                OkHttpClient client = new OkHttpClient();
            //
            //                try {
            //                    File cacheDir = new File(mCacheLocation, UUID.randomUUID().toString());
            //                    Cache cache = new Cache(cacheDir, 1024);
            //                    client.setCache(cache);
            //                } catch (IOException e) {
            //                    Log.e(TAG, "Could not use OkHttp Cache", e);
            //                }
            //
            //                client.setConnectTimeout(Constants.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            //                client.setReadTimeout(Constants.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            //
            //                b.setClient(new OkClient(client));
            //            }

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
