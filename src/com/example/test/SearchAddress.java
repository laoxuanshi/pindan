package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.avos.avoscloud.AVGeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

public class SearchAddress extends Activity implements OnGetGeoCoderResultListener {

	private EditText searchAddressEditText;
	private Button searchAddressButton;
	private ListView searchAddressListView;
	private ArrayAdapter<String> adapter;
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private List<String> dataString = new ArrayList<String>();
	private String chooseAddressString;
	
	GeoCoder mSearch = null;
	String cityString;
	String keyString;
	double lat;
	double lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_address);
		
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		searchAddressEditText = (EditText) findViewById(R.id.searchaddress);
		searchAddressButton = (Button) findViewById(R.id.SAbutton);
		searchAddressListView = (ListView) findViewById(R.id.address_list);

		searchAddressButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String addressInputString = searchAddressEditText.getText()
						.toString();
				suggestSearch(addressInputString);
			}
		});

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dataString);
		searchAddressListView.setAdapter(adapter);

		searchAddressListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Geo搜索
				cityString = data.get(position).get("city").toString();
				keyString = data.get(position).get("key").toString();
				chooseAddressString = dataString.get(position);
				Loger.i("cityString:" + cityString);
				//若在线建议查询返回的地址不包含城市，则自动选择为北京
				if (cityString.length() == 0) {
					cityString = "北京市";
				}
				mSearch.geocode(new GeoCodeOption().city(cityString).address(keyString));
				
//				Intent intent = new Intent(SearchAddress.this, xianxia1.class);
//				intent.putExtra("chooseAddress", chooseAddressString);
//				intent.putExtra("latdouble", lat);
//				intent.putExtra("londouble", lon);
//				startActivity(intent);
//				finish();
			}
		});
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}

	SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();

	// 在线建议查询
	public void suggestSearch(String addressString) {
		mSuggestionSearch = SuggestionSearch.newInstance();
		Loger.l();
		OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
			public void onGetSuggestionResult(SuggestionResult res) {
				if (res == null || res.getAllSuggestions() == null) {
					data.clear();
					dataString.clear();
					Toast.makeText(getApplicationContext(), "未找到相关位置",
							Toast.LENGTH_SHORT).show();
					return;
					// 未找到相关结果
				}
				// 获取在线建议检索结果
				Loger.l();
				Loger.i(res.getAllSuggestions() + "以上是检索结果");
				data.clear();
				dataString.clear();
				for (SuggestionInfo mInfo : res.getAllSuggestions()) {
					// 要的是city+district+key
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
		// 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
		
		String city;
		if(MyApplication.getCity() == null){
			city = "北京";
		}else {
			city = MyApplication.getCity();
		}
		
		mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
				.keyword(addressString).city(city));
		Loger.l();
		// mSuggestionSearch.destroy();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	//地理编码
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(SearchAddress.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		lat = result.getLocation().latitude;
		lon = result.getLocation().longitude;
		
		Loger.i("搜索地址的经纬度坐标:" + lat + "," + lon);
//		Intent intent = new Intent(SearchAddress.this, xianxia1.class);
//		intent.putExtra("chooseAddress", chooseAddressString);
//		intent.putExtra("latdouble", lat);
//		intent.putExtra("londouble", lon);
//		intent.putExtra("city", cityString);
//		startActivity(intent);
		
		Intent intent = new Intent();
		intent.putExtra("chooseAddress", chooseAddressString);
		intent.putExtra("latdouble", lat);
		intent.putExtra("londouble", lon);
		intent.putExtra("city", cityString);
		SearchAddress.this.setResult(RESULT_OK, intent);
		SearchAddress.this.finish();
		finish();
		
	}

	//反地址编码，没用到
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		
	}
}
