package com.ybbbi.safe.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ybbbi.safe.utils.Sharedpreferences;
import com.ybbbi.safe.utils.constants;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class GPSservice extends Service {

	private LocationManager lm;
	private mylocation listener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		listener = new mylocation();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);

	}

	public class mylocation implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			location.getSpeed();
			location.getTime();
			double longitude = location.getLongitude();// 经度
			double latitude = location.getLatitude();// 纬度
			GPSforJson(longitude, latitude);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

	private void GPSforJson(double longitude, double latitude) {
		// 116.615239,39.92973
		// api接口:http://api.jisuapi.com/geoconvert/coord2addr?lat=30.2812129803&lng=120.11523398&type=google&appkey=030112e2ef4a3fc6
		HttpUtils hu = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("lat", latitude + "");
		params.addQueryStringParameter("lng", longitude + "");
		params.addQueryStringParameter("type", "baidu");
		params.addQueryStringParameter("appkey", "030112e2ef4a3fc6");
		hu.send(HttpMethod.GET,
				"http://api.jisuapi.com/geoconvert/coord2addr?", params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> s) {
						// TODO Auto-generated method stub
						String result = s.result;
						parseJson(result);
					}

				});
	}

	private void parseJson(String result) {
		try {
			JSONObject js1 = new JSONObject(result);
			JSONObject jsonObject = js1.getJSONObject("result");
			String address = jsonObject.getString("description");
			System.out.println(address);

			// 将地址发给绑定号码
			sendmessage(address);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendmessage(String address) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(Sharedpreferences.getString(
				getApplicationContext(), constants.savenum, "5554"), null, address,
				null, null);
		System.out.println("发送成功了");
		this.stopSelf();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		lm.removeUpdates(listener);
		super.onDestroy();

	}

}
