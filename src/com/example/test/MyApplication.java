package com.example.test;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.PushService;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

public class MyApplication extends Application {
	private String AppId = "6kclx3zouexncf93ukiexbv7i81bkawawslepjcjjur3mylj";
	private String AppKey = "85718papnh3ssko3ecwgd3ml0ccgqc9sssc4feyg70bs1b9l";

	private static String userPhoneNume = "";
	private static String username = "";
	private static String addressSavedString = "";

	private static LocationClient locationClient = null;
	private static BDLocation location = null;

	private static double latitude = 0;
	private static double longtitude = 0;
	private static String city = null;
	private static String address = null;
	private static AVGeoPoint geoPoint = null;
	private static Context context;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		MyApplication.username = username;
	}

	public static String getUserPhoneNume() {
		return userPhoneNume;
	}

	public static void setUserPhoneNume(String userPhoneNume) {
		MyApplication.userPhoneNume = userPhoneNume;
	}

	public static double getLatitude() {
		return latitude;
	}

	public static void setLatitude(double latitude) {
		MyApplication.latitude = latitude;
	}

	public static double getLongtitude() {
		return longtitude;
	}

	public static void setLongtitude(double longtitude) {
		MyApplication.longtitude = longtitude;
	}

	public static AVGeoPoint getGeoPoint() {
		return geoPoint;
	}

	public static void setGeoPoint(AVGeoPoint geoPoint) {
		MyApplication.geoPoint = geoPoint;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		MyApplication.city = city;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		MyApplication.address = address;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AVOSCloud.initialize(this, AppId, AppKey);
		// 初始化地图sdk SDKInitializer.initialize(getApplicationContext());
		SDKInitializer.initialize(getApplicationContext());
		context = getApplicationContext();
//		initLocationClient();
		
//		PushService.setDefaultPushCallback(MyApplication.this, MainActivity.class);
//	    // 订阅频道，当该频道消息到来的时候，打开对应的 Activity
//		if(AVUser.getCurrentUser()!=null)
//		{
//			String s1=AVUser.getCurrentUser().getObjectId()+"rec";
//			String s2=AVUser.getCurrentUser().getObjectId()+"comment";
//			String s3=AVUser.getCurrentUser().getObjectId()+"state";
//			PushService.subscribe(this, s1, PushActivity.class);
//			PushService.subscribe(this, s2, CommentActivity.class);
//			PushService.subscribe(this, s3, StateActivity.class);
//			AVInstallation.getCurrentInstallation().saveInBackground();
//		}
	}

	private static PinLocationListener pinLocationListener = null;

	public static PinLocationListener getPinLocationListener() {
		return pinLocationListener;
	}

	public static void setPinLocationListener(
			PinLocationListener pinLocationListener) {
		MyApplication.pinLocationListener = pinLocationListener;
	}

	public static void initLocationClient() {
		locationClient = new LocationClient(context);
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location != null && pinLocationListener != null) {
					pinLocationListener.onReceiveLocation(location);
				}

				latitude = location.getLatitude();
				longtitude = location.getLongitude();
				city = location.getCity();
				address = location.getAddrStr();
				geoPoint = new AVGeoPoint(latitude, longtitude);

				Loger.i("每10秒得到：" + latitude + longtitude + city + address + geoPoint);
//				System.out.println("得到的信息：");
//				Log.i("BaiduLocationApiDem", sb.toString());
				postLocation();
			}
		});
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(600000);// 设置发起定位请求的间隔时间为5000mss
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		locationClient.setLocOption(option);
		locationClient.start();
	}

	public static void requestLocation() {
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.requestLocation();
		} else {
			Loger.d("locClient is null or not started " + locationClient);
		}
	}

	public static void postLocation() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String userId = AVUser.getCurrentUser().getObjectId();
		parameters.put("userId", userId);
		try 
		{
			parameters.put("city", city.substring(0, city.length() - 1));
		}
		catch(NullPointerException e)
		{
			parameters.put("city", null);
		}
		parameters.put("location", geoPoint);
		Loger.i("parameters" + parameters);
		
		AVCloud.callFunctionInBackground("postLocation", parameters, 
				new FunctionCallback<String>() {
			@Override
			public void done(String object, AVException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					Loger.i("位置汇报成功" + object);
				}else {
					Loger.i("位置汇报失败" + e.getMessage());
				}
			}
		});
		
	
	}

	public static String getAddressSavedString() {
		Loger.i("add: " + addressSavedString);
		return addressSavedString;
	}

	public static void setAddressSavedString(String addressSavedString) {
		MyApplication.addressSavedString = addressSavedString;
		Loger.i("add: " + addressSavedString);
	}
}
