package com.android.app.core.network;

import java.util.List;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.net.NetworkInteractor;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by frodo on 2015/6/20.
 */
public class AndroidNetworkSystem extends AbstractChildSystem implements NetworkInteractor{
    private Context context;

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
        if((mWifi != null)  && ((mWifi.isAvailable()) || (mMobile.isAvailable())))
        {
            if((mWifi.isConnected()) || (mMobile.isConnected()))
            {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean isGpsEnabled() {
        LocationManager lm = ((LocationManager) context .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    @Override
    public boolean isWifiEnabled() {
        TelephonyManager mgrTel = (TelephonyManager) context .getSystemService(Context.TELEPHONY_SERVICE);
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
        if (getNetworkInfo() != null  && getNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    private ConnectivityManager getConnectivityManager(){
        return (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkInfo getNetworkInfo(){
        return getConnectivityManager().getActiveNetworkInfo();
    }
}
