package com.frodo.app.android.core.network;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.frodo.app.android.core.toolbox.ManifestParser;
import com.frodo.app.framework.controller.AbstractChildSystem;
import com.frodo.app.framework.controller.IController;
import com.frodo.app.framework.controller.MainController;
import com.frodo.app.framework.exception.HttpException;
import com.frodo.app.framework.net.HttpModule;
import com.frodo.app.framework.net.NetworkInterceptor;
import com.frodo.app.framework.net.NetworkTransport;
import com.frodo.app.framework.net.Request;
import com.frodo.app.framework.net.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Network Request
 * Created by frodo on 2015/6/20.
 */
public class AndroidNetworkSystem extends AbstractChildSystem implements NetworkTransport {
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 60 * 1000; // 60s
    public static final int DEFAULT_WRITE_TIMEOUT_MILLIS = 60 * 1000; // 60s
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 60 * 1000; // 60s

    private Context context;
    private String apiUrl;
    private List<NetworkInterceptor> interceptorList = new ArrayList<>();

    private HttpModule httpModule;

    public AndroidNetworkSystem(IController controller) {
        this(controller, null);
    }

    public AndroidNetworkSystem(IController controller, HttpModule httpModule) {
        super(controller);
        this.context = (Context) controller.getMicroContext().getContext();
        if (httpModule == null) {
            this.httpModule = new ManifestParser(context).parse();
        } else {
            this.httpModule = httpModule;
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();
        return networkInfo.isAvailable();
    }

    @Override
    public boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && !accessibleProviders.isEmpty();
    }

    @Override
    public boolean isWifiEnabled() {
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return (getNetworkInfo() != null && getNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS;
    }

    @Override
    public boolean is3rd() {
        if (getNetworkInfo() != null && getNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isWifi() {
        if (getNetworkInfo() != null && getNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    @Override
    public String getAPIUrl() {
        return apiUrl;
    }

    @Override
    public void setAPIUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        resetHttpModule(getHttpModule());
    }

    @Override
    public HttpModule getHttpModule() {
        return httpModule;
    }

    @Override
    public void resetHttpModule(HttpModule httpModule) {
        this.httpModule = httpModule;
        String cacheDir = null;
        if (getController().getCacheSystem() != null) {
            cacheDir = getController().getCacheSystem().getCacheDir();
        }

        httpModule.apply((MainController) getController(),
                new HttpModule.Options(getAPIUrl(), cacheDir,
                        DEFAULT_CONNECT_TIMEOUT_MILLIS,
                        DEFAULT_READ_TIMEOUT_MILLIS,
                        DEFAULT_WRITE_TIMEOUT_MILLIS));
    }

    @Override
    public Response execute(Request request) throws HttpException {
        return this.httpModule.execute(request);
    }

    @Override
    public List<NetworkInterceptor> interceptorList() {
        return interceptorList;
    }

    @Override
    public void addInterceptor(NetworkInterceptor interceptor) {
        interceptorList.add(interceptor);
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkInfo getNetworkInfo() {
        return getConnectivityManager().getActiveNetworkInfo();
    }

}
