package com.frodo.app.android.simple;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frodo.app.android.MicroApplication;
import com.frodo.app.android.core.config.AndroidConfig;
import com.frodo.app.android.core.exception.AndroidCrashHandler;
import com.frodo.app.android.core.log.AndroidLogCollectorSystem;
import com.frodo.app.android.core.network.AndroidNetworkSystem;
import com.frodo.app.android.core.toolbox.ResourceManager;
import com.frodo.app.android.ui.FragmentScheduler;
import com.frodo.app.framework.config.Configuration;
import com.frodo.app.framework.config.Environment;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.exception.ExceptionHandler;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.net.NetworkInterceptor;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.scene.DefaultScene;
import com.frodo.app.framework.scene.Scene;
import com.frodo.app.framework.theme.Theme;

import java.io.File;

/**
 * Created by frodo on 2015/4/2. for example
 */
public class SimpleApplication extends MicroApplication {

    private ConfigurationModel configurationModel;

    @Override
    public void init() {
        super.init();
        Fresco.initialize(this);
        getMainController().getLogCollector().enableCollect(true);
        FragmentScheduler.register(FragmentScheduler.SCHEMA + "/splash", SplashFragment.class);
        FragmentScheduler.register(FragmentScheduler.SCHEMA + "/movie", MovieFragment.class);
        FragmentScheduler.register(FragmentScheduler.SCHEMA + "/redirect", MovieDetailFragment.class);
    }

    @Override
    public LogCollector loadLogCollector() {
        return new AndroidLogCollectorSystem(getMainController()) {
            @Override
            public void uploadLeakBlocking(File file, String leakInfo) {
//                final UploadFileToServerTask.FileWebService service =
//                        getMainController().getNetworkTransport().create(UploadFileToServerTask.FileWebService.class);
//                final UploadFileToServerTask task = new UploadFileToServerTask(service, file, null);
//                getMainController().getBackgroundExecutor().execute(task);
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
        return new AndroidCrashHandler(getMainController());
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

        public SimpleAndroidNetworkSystem(IController controller, final String userAccount, final String userPasswordSha1) {
            super(controller);
            setAPIUrl(controller.getConfig().getCurrentEnvironment().getUrl());
            addInterceptor(new NetworkInterceptor.RequestInterceptor() {
                @Override
                public Void intercept(Request request) {
                    Environment env = getMainController().getConfig().getCurrentEnvironment();
                    if (env.getName().contains("tmdb")) {
                        request.addQueryParam("api_key", env.getApiKey());
                    } else if (env.getName().contains("trakt")) {
                        request.addQueryParam("apikey", env.getApiKey());
                    }
                    if (userAccount != null && userPasswordSha1 != null) {
//                        String source = userAccount + ":" + userPasswordSha1;
//                        String authorization = "Basic " + Base64.encode(source.getBytes(), "");;
//                        request.getHeaders().add(new Header("Authorization", authorization));
                    }

                    return super.intercept(request);
                }
            });
        }
    }
}
