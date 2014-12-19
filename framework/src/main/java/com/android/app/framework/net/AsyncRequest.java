package com.android.app.framework.net;

import android.content.Context;
import android.util.Log;

import com.android.app.framework.entity.Entity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Title: AsyncRequest.java
 * @Description: TODO(同步网络请求)
 * @author frodoking
 * @date 2014年9月27日 下午1:53:28
 */
public class AsyncRequest {
	private static final String TAG = AsyncRequest.class.getSimpleName();
	private static AsyncHttpClient mClient = new AsyncHttpClient();

	public static void doGetRequest(String url, ResponseHandlerInterface handler) {
		Log.i(TAG, "get url >> " + url);
		mClient.get(url, handler);
	}

	public static void doGetRequest(Context context, String url, Map<String, String> paramsMap,
			ResponseHandlerInterface handler) {
		Log.i(TAG, "get url >> " + url);
		RequestParams params = new RequestParams(paramsMap);
		mClient.get(context, url, getRequestHeaders(context), params, handler);
	}

	public static void doPostRequest(Context context, String url, JSONObject body, ResponseHandlerInterface handler) {
		Log.i(TAG, "post url >> " + url);
		mClient.post(context, url, getRequestHeaders(context), getEntity(body), RequestParams.APPLICATION_JSON, handler);
	}

	private static Header[] getRequestHeaders(Context context) {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Content-Type", "application/json;charset=utf-8"));

//		KukuApp app = (KukuApp) context.getApplicationContext();
//		String userId = app.getUser() == null ? "" : app.getUser().getId();
//		headers.add(new BasicHeader("CurrentUid", userId));
//		headers.add(new BasicHeader("ver", "android_" + app.getVersionName()));
		return headers.toArray(new Header[headers.size()]);
	}
	
	private static HttpEntity getEntity(JSONObject body) {
		try {
			StringEntity entity = new StringEntity(body.toString(), HTTP.UTF_8);

			entity.setContentType("text/json;charset=utf-8");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));
			return entity;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
