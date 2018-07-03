package com.yangfan.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.sax.StartElementListener;
import android.util.Log;

public class NetConnectionUtil {
	private static Context context;
	public void Toast(Context context){
		this.context=context;
	}
	private static byte[] result;
	//	联网方面的我写的不太对.但是可以用.
	public static InputStream GetInputStream(String urlString) throws Exception{
		HttpGet httpGet = new HttpGet(urlString);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters,5 * 1000);
		HttpConnectionParams.setSoTimeout(httpParameters, 60 * 1000);
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
		httpGet.setHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		if (httpResponse.getStatusLine().getStatusCode() == 200){
			result = EntityUtils.toByteArray(httpResponse.getEntity());
			return new ByteArrayInputStream(result);
		}else{
			Log.i("1212121212", "错误了");
			return null;
		}
	}

}