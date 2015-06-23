package com.android.app.simple;

import com.android.app.AppApplication;
import com.android.app.core.config.AndroidConfig;
import com.android.app.core.network.AndroidNetworkSystem;
import com.android.app.framework.config.Configuration;
import com.android.app.framework.controller.IController;
import com.android.app.framework.net.NetworkInteractor;
import com.android.app.framework.scene.DefaultScene;
import com.android.app.framework.scene.Scene;
import com.android.app.framework.theme.Theme;
import com.jakewharton.trakt.util.Base64;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by frodo on 2015/4/2.
 */
public class SimpleApplication extends AppApplication {

    @Override
    public Configuration loadConfiguration() {
        return new AndroidConfig(getMainController()){
            @Override
            public String getHost() {
                return "https://api.trakt.tv";
            }
        };
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
            RestAdapter.Builder builder = super.newRestAdapterBuilder();
            builder.setRequestInterceptor(new RequestInterceptor() {
                public void intercept(RequestFacade requestFacade) {
                    requestFacade.addPathParam("apikey", Constants.TRAKT_API_KEY);
                    if (userAccount != null && userPasswordSha1 != null) {
                        String source = userAccount + ":" + userPasswordSha1;
                        String authorization = "Basic " + Base64.encodeBytes(source.getBytes());
                        requestFacade.addHeader("Authorization", authorization);
                    }
                }
            });
            return builder;
        }
    }
}
