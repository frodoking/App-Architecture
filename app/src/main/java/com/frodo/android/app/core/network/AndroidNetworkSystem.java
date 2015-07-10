package com.frodo.android.app.core.network;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.net.NetworkInteractor;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * 网络请求系统（采用retrofit+otto）
 * Created by frodo on 2015/6/20.
 */
public class AndroidNetworkSystem extends AbstractChildSystem implements NetworkInteractor {
    private Context context;
    private RestAdapter restAdapter;

    public AndroidNetworkSystem(IController controller) {
        super(controller);
        this.context = (Context) controller.getContext();
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean flag = false;
        if ((mWifi != null) && ((mWifi.isAvailable()) || (mMobile.isAvailable()))) {
            if ((mWifi.isConnected()) || (mMobile.isConnected())) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean isGpsEnabled() {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    @Override
    public boolean isWifiEnabled() {
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((getNetworkInfo() != null && getNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
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
    public <T> T create(Class<T> service) {
        return getRestAdapter().create(service);
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkInfo getNetworkInfo() {
        return getConnectivityManager().getActiveNetworkInfo();
    }

    /**
     * 可以重写
     * RestAdapter.Builder b = super.newRestAdapterBuilder();
     * <p/>
     * if (mCacheLocation != null) {
     * OkHttpClient client = new OkHttpClient();
     * File cacheDir = new File(mCacheLocation, UUID.randomUUID().toString());
     * Cache cache = new Cache(cacheDir, 1024);
     * client.setCache(cache);
     * <p/>
     * client.setConnectTimeout(Constants.CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
     * client.setReadTimeout(Constants.READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
     * <p/>
     * b.setClient(new OkClient(client));
     * }
     * <p/>
     * return b;
     */
    public RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    private RestAdapter getRestAdapter() {
        if (this.restAdapter == null) {
            RestAdapter.Builder builder = this.newRestAdapterBuilder();
            builder.setEndpoint(getController().getConfig().getCurrentEnvironment().getUrl());
            builder.setConverter(new GsonConverter(getGsonBuilder().create()));
            if (getController().getConfig().isDebug()) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            this.restAdapter = builder.build();
        }

        return this.restAdapter;
    }

    private GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Integer.class, new JsonDeserializer() {
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
                    JsonParseException {
                try {
                    return json.getAsInt();
                } catch (NumberFormatException var5) {
                    return null;
                }
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return new SimpleDateFormat("yyy-MM-dd").parse(json.getAsString());
                } catch (ParseException var5) {
                    return null;
                }
            }
        });
        return builder;
    }
}
