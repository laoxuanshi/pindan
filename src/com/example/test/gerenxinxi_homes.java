package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.example.test.util.Loger;

public class gerenxinxi_homes extends Activity implements OnGetGeoCoderResultListener {
	
	private Button cancelButton;
	private Button saveButton;
	private Button searchButton;
	private TextView searcHomesTextView; 
	private TextView homeChooseTextView;
	private ListView searchHomesAddressListView;
	private ArrayAdapter<String> adapter;
	
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private List<String> dataString = new ArrayList<String>();
	
	GeoCoder mSearch = null;
	String cityString;
	String keyString;
	double lat;
	double lon;
	AVGeoPoint geoPoint;
	String homeChooseString;
	private Map<String, Object> itemsMap = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homesedit);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		
		cancelButton = (Button)findViewById(R.id.cancelhomes);
		saveButton = (Button)findViewById(R.id.savehomes);
		searchButton = (Button)findViewById(R.id.searchhomebutton);
		searcHomesTextView = (TextView)findViewById(R.id.searchhomeaddress);
		homeChooseTextView = (TextView)findViewById(R.id.homechoose);
		searchHomesAddressListView = (ListView)findViewById(R.id.homeaddress_list);
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataString);
		searchHomesAddressListView.setAdapter(adapter);

		searchHomesAddressListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Geo����
				cityString = data.get(position).get("city").toString();
				keyString = data.get(position).get("key").toString();
				homeChooseString = dataString.get(position);
				
				mSearch.geocode(new GeoCodeOption().city(cityString).address(keyString));
				
			}
		});
		
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//TODO �����ϴ�,�ڷ���ʱҳ����ת
				final Map<String, Object> params = new HashMap<String, Object>();
				String userIdString = AVUser.getCurrentUser().getObjectId();
				params.put("userId", userIdString);
				params.put("subscribeType", "homes");
				params.put("operation", "add");
				params.put("items", itemsMap);
				
				AVCloud.callFunctionInBackground("userSubscribe", params,
						new FunctionCallback<Object>() {
							public void done(Object object, AVException e) {
								if (e == null) {
									Loger.i("��Ӷ��ĵص�ɹ�: " + object);
									Intent intent = new Intent(gerenxinxi_homes.this,
											myxinxi.class);
									startActivity(intent);
									finish();
								} else {
									Loger.d("sms result: " + e.getCode() + ", "
											+ e.getMessage());
									Toast.makeText(gerenxinxi_homes.this, "�ύʧ��",
											Toast.LENGTH_SHORT).show();
								}
							};
						});
			}
		});
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String addressInputString = searcHomesTextView.getText()
						.toString();
				suggestSearch(addressInputString);
			}
		});
	}

	// ���߽����ѯ
	SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
	public void suggestSearch(String addressString) {
		mSuggestionSearch = SuggestionSearch.newInstance();
		Loger.l();
		OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
			public void onGetSuggestionResult(SuggestionResult res) {
				if (res == null || res.getAllSuggestions() == null) {
					data.clear();
					dataString.clear();
					Toast.makeText(getApplicationContext(), "δ�ҵ����λ��",
							Toast.LENGTH_SHORT).show();
					return;
					// δ�ҵ���ؽ��
				}
				// ��ȡ���߽���������
				Loger.l();
				Loger.i(res.getAllSuggestions() + "�����Ǽ������");
				data.clear();
				dataString.clear();
				for (SuggestionInfo mInfo : res.getAllSuggestions()) {
					// Ҫ����city+district+key
					Loger.i("key:" + mInfo.key + "district:" + mInfo.district
							+ "city:" + mInfo.city);
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("city", mInfo.city);
					map.put("key", mInfo.key);
					
					dataString.add(mInfo.city + mInfo.district + mInfo.key);
					data.add(map);
					adapter.notifyDataSetChanged();
				}
			}
		};
		mSuggestionSearch.setOnGetSuggestionResultListener(listener);
		// ʹ�ý������������ȡ�����б������onSuggestionResult()�и���
		mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
				.keyword(addressString).city(MyApplication.getCity()));
		Loger.l();
		// mSuggestionSearch.destroy();
	}
	
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(gerenxinxi_homes.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		lat = result.getLocation().latitude;
		lon = result.getLocation().longitude;
		geoPoint = new AVGeoPoint(lat, lon);
		Loger.i("��ע����ľ�γ������:" + lat + "," + lon);
		homeChooseTextView.setText(homeChooseString);
		
		itemsMap.put("location", geoPoint);
		itemsMap.put("description", homeChooseString);
		
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		
	}
}
