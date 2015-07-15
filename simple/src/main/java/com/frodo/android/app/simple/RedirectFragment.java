package com.frodo.android.app.simple;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.frodo.android.app.framework.controller.IPresenter;
import com.frodo.android.app.ui.fragment.AbstractBaseFragment;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by frodo on 2015/7/10.
 */
public class RedirectFragment extends AbstractBaseFragment {
    @Override
    public void onCreatePresenter() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_redirect;
    }

    @Override
    public void initView() {
    }

    @Override
    public void registerListener() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();       //取出IMEI
        String tel = tm.getLine1Number();     //取出MSISDN，很可能为空
        String iccid = tm.getSimSerialNumber();  //取出ICCID
        String imsi = tm.getSubscriberId();     //取出IMSI
        String networkOperatorName = tm.getNetworkOperatorName();     //返回注册的网络运营商的名字

        TextView tv = (TextView) getView().findViewById(R.id.imei);

        StringBuffer sb = new StringBuffer();
        sb.append("deviceid:").append(imei).append("\n");
        sb.append("tel:").append(tel).append("\n");
        sb.append("iccid:").append(iccid).append("\n");
        sb.append("imsi:").append(imsi).append("\n");
        sb.append("networkOperatorName:").append(networkOperatorName).append("\n");
        tv.setText(/*getSIMInfo()*/sb.toString());
    }

    @Override
    public void initBusiness() {

    }

    @Override
    public IPresenter getPresenter() {
        return null;
    }

    private String getSIMInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field[] fs = c.getFields();
            for (Field f : fs) {
                sb.append("field:").append(f.getName()).append("\n");
            }
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            Method m = TelephonyManager.class.getDeclaredMethod(
                    "getSubscriberIdGemini", int.class);
            sb.append("imsi:").append("\n");
            String imsi_1 = (String) m.invoke(tm, simId_1);
            String imsi_2 = (String) m.invoke(tm, simId_2);
            sb.append(imsi_1).append("\n").append(imsi_2).append("\n");

            Method m1 = TelephonyManager.class.getDeclaredMethod(
                    "getDeviceIdGemini", int.class);
            String imei_1 = (String) m1.invoke(tm, simId_1);
            String imei_2 = (String) m1.invoke(tm, simId_2);
            sb.append("imei:").append("\n");
            sb.append(imei_1).append("\n").append(imei_2).append("\n");

            Method mx = TelephonyManager.class.getDeclaredMethod(
                    "getPhoneTypeGemini", int.class);
            int phoneType_1 = (Integer) mx.invoke(tm, simId_1);
            int phoneType_2 = (Integer) mx.invoke(tm, simId_2);
            sb.append("phoneType:").append("\n");
            sb.append(phoneType_1).append("\n").append(phoneType_2).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        getMainController().getLogCollector().logInfo("simi", sb.toString());
        return sb.toString();
    }

    private void getSIMInfo2() {
        try {
            TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            Method mx = TelephonyManager.class.getMethod("getDefault",
                    int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

            String imsi_1 = tm1.getSubscriberId();
            String imsi_2 = tm2.getSubscriberId();

            String imei_1 = tm1.getDeviceId();
            String imei_2 = tm2.getDeviceId();

            int phoneType_1 = tm1.getPhoneType();
            int phoneType_2 = tm2.getPhoneType();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
