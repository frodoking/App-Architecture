package com.frodo.app.android.simple;

import android.app.Application;

import com.frodo.app.android.ApplicationDelegation;
import com.frodo.app.android.MicroContextLoader;
import com.frodo.app.android.core.config.AndroidConfig;
import com.frodo.app.android.core.exception.AndroidCrashHandler;
import com.frodo.app.android.core.log.AndroidLogCollectorSystem;
import com.frodo.app.android.core.network.AndroidNetworkSystem;
import com.frodo.app.android.core.toolbox.ResourceManager;
import com.frodo.app.android.ui.FragmentScheduler;
import com.frodo.app.framework.config.Configuration;
import com.frodo.app.framework.config.Environment;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.ExceptionHandler;
import com.frodo.app.framework.log.LogCollector;
import com.frodo.app.framework.log.Logger;
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
public class SimpleApplication extends Application implements ApplicationDelegation {
	private MicroContextLoader microContextLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		beforeLoad();
		microContextLoader = loadMicroContextLoader();
		afterLoad();
		Logger.fLog().tag("ReBuild [Application]"+hashCode()).i("MainController@"+microContextLoader.getMainController().hashCode());
	}

	@Override
	public void beforeLoad() {
		FragmentScheduler.register(FragmentScheduler.SCHEMA + "/splash", SplashFragment.class);
		FragmentScheduler.register(FragmentScheduler.SCHEMA + "/movie", MovieFragment.class);
		FragmentScheduler.register(FragmentScheduler.SCHEMA + "/redirect", MovieDetailFragment.class);
	}

	@Override
	public MicroContextLoader loadMicroContextLoader() {
		return new MicroContextLoader(this) {

			@Override
			public Configuration loadConfiguration() {
				final Environment environment = new Environment(0, "tmdb", Constants.TMDB_ENDPOINT, Constants.TMDB_API_KEY, true);
				return new AndroidConfig(getMainController(), environment);
			}

			@Override
			public NetworkTransport loadNetworkTransport() {
				return new SimpleAndroidNetworkSystem(getMainController(), null, null);
			}

			@Override
			public void loadServerConfiguration() {
				ConfigurationModel configurationModel = getMainController().getModelFactory().getOrCreateIfAbsent(ConfigurationModel.class.getSimpleName(),
						ConfigurationModel.class, getMainController());

				if (!configurationModel.isValid()) {
					configurationModel.initBusiness();
				}
			}
		};
	}

	@Override
	public void afterLoad() {
		getMainController().getLogCollector().enableCollect(true);
	}

	@Override
	public MainController getMainController() {
		return microContextLoader.getMainController();
	}

	private class SimpleAndroidNetworkSystem extends AndroidNetworkSystem {

		public SimpleAndroidNetworkSystem(IController controller, final String userAccount, final String userPasswordSha1) {
			super(controller);
			setAPIUrl(controller.getConfig().getCurrentEnvironment().getUrl());
			addInterceptor(new NetworkInterceptor.RequestInterceptor() {
				@Override
				public Void intercept(Request request) {
					Environment env = getController().getConfig().getCurrentEnvironment();
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
