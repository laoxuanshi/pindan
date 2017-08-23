package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.fragment.PinFragment;
import com.example.test.fragment.SearchFragment;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchResult extends ListActivity
{
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private List<String> list3 = new ArrayList<String>();
	private List<String> list4 = new ArrayList<String>();
	private List<String> list5 = new ArrayList<String>();
	private List<String> list6 = new ArrayList<String>();
	static List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();
	private OrderAdapter listAdapter = null; // 进行数据的转换操作
	private Spinner mySpinner1, mySpinner2, mySpinner3, mySpinner4, mySpinner5, mySpinner6;    
    private ArrayAdapter<String> adapter1, adapter2, adapter3, adapter4, adapter5, adapter6; 
    
	TextView showsearch; 
	public static String city,category2,status, whetherOnline=null;
	public static String category1=SearchFragment.cato1;
	
	private PullToRefreshListView listView;	// 定义ListView组件
	String nickNameString;
	String spotString;
	String descriptString;
	String stateString;
	String imagoject;
	String orderTypeString;
	String endTimeString;
	String genderString;
	String imageIdString;
	String portraitIdString;
	String orderIdString;
	String orderCreatorString;
//	static int page = 1;
	static String sid;
//	static long currentTime;
	private ImageDownLoader mImageDownLoader;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
//		currentTime = System.currentTimeMillis()/1000;
//		Loger.i(currentTime+"时间戳");
//		page = 1;
		setContentView(R.layout.searchresult_listview);

		mImageDownLoader = new ImageDownLoader(this);
		sid = this.getIntent().getCharSequenceExtra("sid").toString();
		
		showsearch =(TextView)findViewById(R.id.searchtextshow);
		showsearch.setText("搜索—"+SearchFragment.showsearch) ;
		
		//城市的选择
		list1.add("所有省份");    
        list1.add("北京");
        list1.add("上海");
        list1.add("天津");
        list1.add("重庆");
        list1.add("河北");
        list1.add("山西");
        list1.add("内蒙古");
        list1.add("辽宁");
        list1.add("吉林");
        list1.add("黑龙江");
        list1.add("江苏");
        list1.add("浙江");
        list1.add("安徽");
        list1.add("福建");
        list1.add("江西");
        list1.add("山东");
        list1.add("河南");
        list1.add("湖北");
        list1.add("湖南");
        list1.add("广东");
        list1.add("广西");
        list1.add("海南");
        list1.add("四川");
        list1.add("贵州");
        list1.add("云南");
        list1.add("西藏");
        list1.add("陕西");
        list1.add("甘肃");
        list1.add("青海");
        list1.add("宁夏");
        list1.add("新疆");
        list1.add("港澳台");
        //0-32

        mySpinner1 = (Spinner)findViewById(R.id.s_city); 
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1); 
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner1.setAdapter(adapter1);  
        
        mySpinner6 = (Spinner)findViewById(R.id.s_city1); 
        adapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list6); 
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner6.setAdapter(adapter6);  
        
        mySpinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				switch (arg2) 
				{
				case 0:
					list6.clear();
					list6.add("所有城市");
					mySpinner6.setSelection(0);
					city=null;
					Loger.i("执行一次");
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 1:
					list6.clear();
					list6.add("北京");
					mySpinner6.setSelection(0);
					city="北京";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 2:
					list6.clear();
					list6.add("上海");
					mySpinner6.setSelection(0);
					city="上海";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 3:
					list6.clear();
					list6.add("天津");
					mySpinner6.setSelection(0);
					city="天津";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 4:
					list6.clear();
					list6.add("重庆");
					mySpinner6.setSelection(0);
					city="重庆";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 5:
					list6.clear();
					list6.add("石家庄");
					list6.add("唐山");
					list6.add("秦皇岛");
					list6.add("邯郸");
					list6.add("邢台");
					list6.add("保定");
					list6.add("张家口");
					list6.add("承德");
					list6.add("沧州");
					list6.add("廊坊");
					list6.add("衡水");
					adapter6.notifyDataSetChanged();
					
					city="石家庄";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="石家庄";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="唐山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="秦皇岛";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="邯郸";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="邢台";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="保定";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="张家口";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="承德";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="沧州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="廊坊";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="衡水";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 6:
					list6.clear();
					list6.add("太原");
					list6.add("大同");
					list6.add("阳泉");
					list6.add("长治");
					list6.add("晋城");
					list6.add("朔州");
					list6.add("晋中");
					list6.add("运城");
					list6.add("忻州");
					list6.add("临汾");
					list6.add("吕梁");
					adapter6.notifyDataSetChanged();
					
					city="太原";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="太原";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="大同";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="阳泉";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="长治";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="晋城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="朔州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="晋中";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="运城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="忻州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="临汾";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="吕梁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 7:
					list6.clear();
					list6.add("呼和浩特");
					list6.add("包头");
					list6.add("乌海");
					list6.add("赤峰");
					list6.add("通辽");
					list6.add("鄂尔多斯");
					list6.add("呼伦贝尔");
					list6.add("巴彦淖尔");
					list6.add("乌兰察布");
					list6.add("兴安");
					list6.add("锡林郭勒");
					list6.add("阿拉善");
					adapter6.notifyDataSetChanged();
					
					city="呼和浩特";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="呼和浩特";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="包头";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="乌海";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="赤峰";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="通辽";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="鄂尔多斯";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="呼伦贝尔";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="巴彦淖尔";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="乌兰察布";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="兴安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="锡林郭勒";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="阿拉善";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 8:
					list6.clear();
					list6.add("沈阳");
					list6.add("大连");
					list6.add("鞍山");
					list6.add("抚顺");
					list6.add("本溪");
					list6.add("丹东");
					list6.add("锦州");
					list6.add("营口");
					list6.add("阜新");
					list6.add("辽阳");
					list6.add("盘锦");
					list6.add("铁岭");
					list6.add("朝阳");
					list6.add("葫芦岛");
					adapter6.notifyDataSetChanged();
					
					city="沈阳";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="沈阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="大连";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="鞍山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="抚顺";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="本溪";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="丹东";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="锦州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="营口";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="阜新";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="辽阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="盘锦";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="铁岭";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="朝阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="葫芦岛";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 9:
					list6.clear();
					list6.add("长春");
					list6.add("吉林");
					list6.add("四平");
					list6.add("辽源");
					list6.add("通化");
					list6.add("白山");
					list6.add("松原");
					list6.add("白城");
					list6.add("延边");
					adapter6.notifyDataSetChanged();
					
					city="长春";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="长春";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="吉林";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="四平";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="辽源";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="通化";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="白山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="松原";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="白城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="延边";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 10:
					list6.clear();
					list6.add("哈尔滨");
					list6.add("齐齐哈尔");
					list6.add("鸡西");
					list6.add("鹤岗");
					list6.add("双鸭山");
					list6.add("大庆");
					list6.add("伊春");
					list6.add("佳木斯");
					list6.add("七台河");
					list6.add("牡丹江");
					list6.add("黑河");
					list6.add("绥化");
					list6.add("大兴安岭");
					adapter6.notifyDataSetChanged();
					
					city="哈尔滨";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="哈尔滨";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="齐齐哈尔";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="鸡西";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="鹤岗";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="双鸭山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="大庆";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="伊春";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="佳木斯";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="七台河";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="牡丹江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="黑河";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="绥化";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="大兴安岭";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 11:
					list6.clear();
					list6.add("南京");
					list6.add("无锡");
					list6.add("徐州");
					list6.add("常州");
					list6.add("苏州");
					list6.add("南通");
					list6.add("连云港");
					list6.add("淮安");
					list6.add("盐城");
					list6.add("扬州");
					list6.add("镇江");
					list6.add("泰州");
					list6.add("宿迁");
					adapter6.notifyDataSetChanged();
					
					city="南京";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="南京";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="无锡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="徐州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="常州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="苏州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="南通";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="连云港";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="淮安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="盐城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="扬州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="镇江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="泰州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="宿迁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 12:
					list6.clear();
					list6.add("杭州");
					list6.add("宁波");
					list6.add("温州");
					list6.add("嘉兴");
					list6.add("湖州");
					list6.add("绍兴");
					list6.add("金华");
					list6.add("衢州");
					list6.add("舟山");
					list6.add("台州");
					list6.add("丽水");
					adapter6.notifyDataSetChanged();
					
					city="杭州";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="杭州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="宁波";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="温州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="嘉兴";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="湖州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="绍兴";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="金华";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="衢州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="舟山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="台州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="丽水";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 13:
					list6.clear();
					list6.add("合肥");
					list6.add("芜湖");
					list6.add("蚌埠");
					list6.add("淮南");
					list6.add("马鞍山");
					list6.add("淮北");
					list6.add("铜陵");
					list6.add("安庆");
					list6.add("黄山");
					list6.add("滁州");
					list6.add("阜阳");
					list6.add("宿州");
					list6.add("巢湖");
					list6.add("六安");
					list6.add("亳州");
					list6.add("池州");
					list6.add("宣城");
					adapter6.notifyDataSetChanged();
					
					city="合肥";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="合肥";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="芜湖";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="蚌埠";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="淮南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="马鞍山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="淮北";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="铜陵";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="安庆";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="黄山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="滁州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="阜阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="宿州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="巢湖";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="六安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="亳州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="池州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="宣城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 14:
					list6.clear();
					list6.add("福州");
					list6.add("厦门");
					list6.add("莆田");
					list6.add("三明");
					list6.add("泉州");
					list6.add("漳州");
					list6.add("南平");
					list6.add("龙岩");
					list6.add("宁德");
					adapter6.notifyDataSetChanged();
					
					city="福州";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="福州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="厦门";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="莆田";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="三明";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="泉州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="漳州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="南平";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="龙岩";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="宁德";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 15:
					list6.clear();
					list6.add("南昌");
					list6.add("景德镇");
					list6.add("萍乡");
					list6.add("九江");
					list6.add("新余");
					list6.add("鹰潭");
					list6.add("赣州");
					list6.add("吉安");
					list6.add("宜春");
					list6.add("抚州");
					list6.add("上饶");
					adapter6.notifyDataSetChanged();
					
					city="南昌";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="南昌";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="景德镇";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="萍乡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="九江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="新余";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="鹰潭";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="赣州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="吉安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="宜春";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="抚州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="上饶";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 16:
					list6.clear();
					list6.add("济南");
					list6.add("青岛");
					list6.add("淄博");
					list6.add("枣庄");
					list6.add("东营");
					list6.add("烟台");
					list6.add("潍坊");
					list6.add("威海");
					list6.add("济宁");
					list6.add("泰安");
					list6.add("日照");
					list6.add("莱芜");
					list6.add("临沂");
					list6.add("德州");
					list6.add("聊城");
					list6.add("滨州");
					list6.add("菏泽");
					adapter6.notifyDataSetChanged();
					
					city="济南";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="济南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="青岛";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="淄博";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="枣庄";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="东营";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="烟台";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="潍坊";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="威海";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="济宁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="泰安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="日照";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="莱芜";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="临沂";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="德州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="聊城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="滨州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="菏泽";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 17:
					list6.clear();
					list6.add("郑州");
					list6.add("开封");
					list6.add("洛阳");
					list6.add("平顶山");
					list6.add("焦作");
					list6.add("鹤壁");
					list6.add("新乡");
					list6.add("安阳");
					list6.add("濮阳");
					list6.add("许昌");
					list6.add("漯河");
					list6.add("三门峡");
					list6.add("南阳");
					list6.add("商丘");
					list6.add("信阳");
					list6.add("周口");
					list6.add("驻马店");
					adapter6.notifyDataSetChanged();
					
					city="郑州";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="郑州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="开封";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="洛阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="平顶山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="焦作";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="鹤壁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="新乡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="安阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="濮阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="许昌";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="漯河";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="三门峡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="南阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="商丘";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="信阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="周口";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="驻马店";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 18:
					list6.clear();
					list6.add("武汉");
					list6.add("黄石");
					list6.add("襄樊");
					list6.add("十堰");
					list6.add("荆州");
					list6.add("宜昌");
					list6.add("荆门");
					list6.add("鄂州");
					list6.add("孝感");
					list6.add("黄冈");
					list6.add("咸宁");
					list6.add("随州");
					list6.add("恩施");
					adapter6.notifyDataSetChanged();
					
					city="武汉";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="武汉";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="黄石";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="襄樊";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="十堰";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="荆州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="宜昌";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="荆门";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="鄂州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="孝感";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="黄冈";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="咸宁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="随州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="恩施";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 19:
					list6.clear();
					list6.add("长沙");
					list6.add("株洲");
					list6.add("湘潭");
					list6.add("衡阳");
					list6.add("邵阳");
					list6.add("岳阳");
					list6.add("常德");
					list6.add("张家界");
					list6.add("益阳");
					list6.add("郴州");
					list6.add("永州");
					list6.add("怀化");
					list6.add("娄底");
					list6.add("湘西");
					adapter6.notifyDataSetChanged();
					
					city="长沙";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="长沙";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="株洲";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="湘潭";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="衡阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="邵阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="岳阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="常德";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="张家界";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="益阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="郴州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="永州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="怀化";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="娄底";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="湘西";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 20:
					list6.clear();
					list6.add("广州");
					list6.add("深圳");
					list6.add("珠海");
					list6.add("汕头");
					list6.add("韶关");
					list6.add("佛山");
					list6.add("江门");
					list6.add("湛江");
					list6.add("茂名");
					list6.add("肇庆");
					list6.add("惠州");
					list6.add("梅州");
					list6.add("汕尾");
					list6.add("河源");
					list6.add("阳江");
					list6.add("清远");
					list6.add("东莞");
					list6.add("中山");
					list6.add("潮州");
					list6.add("揭阳");
					list6.add("云浮");
					adapter6.notifyDataSetChanged();
					
					city="广州";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="广州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="深圳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="珠海";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="汕头";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="韶关";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="佛山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="江门";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="湛江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="茂名";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="肇庆";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="惠州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="梅州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="汕尾";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="河源";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="阳江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="清远";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 16:
								city="东莞";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 17:
								city="中山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 18:
								city="潮州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 19:
								city="揭阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="云浮";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 21:
					list6.clear();
					list6.add("南宁");
					list6.add("柳州");
					list6.add("桂林");
					list6.add("梧州");
					list6.add("北海");
					list6.add("防城港");
					list6.add("钦州");
					list6.add("贵港");
					list6.add("玉林");
					list6.add("百色");
					list6.add("贺州");
					list6.add("河池");
					list6.add("来宾");
					list6.add("崇左");
					adapter6.notifyDataSetChanged();
					
					city="南宁";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="南宁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="柳州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="桂林";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="梧州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="北海";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="防城港";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="钦州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="贵港";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="玉林";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="百色";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="贺州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="河池";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="来宾";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="崇左";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 22:
					list6.clear();
					list6.add("海口");
					list6.add("三亚");
					adapter6.notifyDataSetChanged();
					
					city="海口";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="海口";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="三亚";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 23:
					list6.clear();
					list6.add("成都");
					list6.add("自贡");
					list6.add("攀枝花");
					list6.add("泸州");
					list6.add("德阳");
					list6.add("绵阳");
					list6.add("广元");
					list6.add("遂宁");
					list6.add("内江");
					list6.add("乐山");
					list6.add("南充");
					list6.add("宜宾");
					list6.add("广安");
					list6.add("达州");
					list6.add("眉山");
					list6.add("雅安");
					list6.add("巴中");
					list6.add("资阳");
					list6.add("阿坝");
					list6.add("甘孜");
					list6.add("凉山");
					adapter6.notifyDataSetChanged();
					
					city="成都";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="成都";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="自贡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="攀枝花";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="泸州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="德阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="绵阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="广元";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="遂宁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="内江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="乐山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="南充";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="宜宾";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="广安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="达州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="眉山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="雅安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 16:
								city="巴中";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 17:
								city="资阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 18:
								city="阿坝";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 19:
								city="甘孜";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="凉山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 24:
					list6.clear();
					list6.add("贵阳");
					list6.add("六盘水");
					list6.add("遵义");
					list6.add("安顺");
					list6.add("铜仁");
					list6.add("毕节");
					list6.add("黔西南");
					list6.add("黔东南");
					list6.add("黔南");
					adapter6.notifyDataSetChanged();
					
					city="贵阳";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="贵阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="六盘水";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="遵义";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="安顺";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="铜仁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="毕节";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="黔西南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="黔东南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="黔南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 25:
					list6.clear();
					list6.add("昆明");
					list6.add("曲靖");
					list6.add("玉溪");
					list6.add("保山");
					list6.add("昭通");
					list6.add("丽江");
					list6.add("普洱");
					list6.add("临沧");
					list6.add("文山");
					list6.add("红河");
					list6.add("西双版纳");
					list6.add("楚雄");
					list6.add("大理");
					list6.add("德宏");
					list6.add("怒江");
					list6.add("迪庆");
					adapter6.notifyDataSetChanged();
					
					city="昆明";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="昆明";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="曲靖";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="玉溪";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="保山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="昭通";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="丽江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="普洱";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="临沧";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="文山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="红河";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="西双版纳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="楚雄";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="大理";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="德宏";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="怒江";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="迪庆";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 26:
					list6.clear();
					list6.add("拉萨");
					list6.add("昌都");
					list6.add("山南");
					list6.add("日咯则");
					list6.add("那曲");
					list6.add("阿里");
					list6.add("林芝");
					adapter6.notifyDataSetChanged();
					
					city="拉萨";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="拉萨";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="昌都";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="山南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="日咯则";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="那曲";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="阿里";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="林芝";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 27:
					list6.clear();
					list6.add("西安");
					list6.add("铜川");
					list6.add("宝鸡");
					list6.add("咸阳");
					list6.add("渭南");
					list6.add("延安");
					list6.add("汉中");
					list6.add("榆林");
					list6.add("安康");
					list6.add("商洛");
					adapter6.notifyDataSetChanged();
					
					city="西安";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="西安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="铜川";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="宝鸡";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="咸阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="渭南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="延安";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="汉中";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="榆林";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="安康";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="商洛";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 28:
					list6.clear();
					list6.add("兰州");
					list6.add("嘉峪关");
					list6.add("金昌");
					list6.add("白银");
					list6.add("天水");
					list6.add("武威");
					list6.add("张掖");
					list6.add("平凉");
					list6.add("酒泉");
					list6.add("庆阳");
					list6.add("定西");
					list6.add("陇南");
					list6.add("临夏");
					list6.add("甘南");
					adapter6.notifyDataSetChanged();
					
					city="兰州";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="兰州";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="嘉峪关";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="金昌";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="白银";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="天水";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="武威";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="张掖";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="平凉";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="酒泉";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="庆阳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="定西";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="陇南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="临夏";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="甘南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 29:
					list6.clear();
					list6.add("西宁");
					list6.add("海东");
					list6.add("海北");
					list6.add("黄南");
					list6.add("海南");
					list6.add("果洛");
					list6.add("玉树");
					list6.add("海西");
					adapter6.notifyDataSetChanged();
					
					city="西宁";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="西宁";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="海东";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="海北";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="黄南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="海南";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="果洛";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="玉树";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="海西";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 30:
					list6.clear();
					list6.add("银川");
					list6.add("石嘴山");
					list6.add("吴忠");
					list6.add("固原");
					list6.add("中卫");
					adapter6.notifyDataSetChanged();
					
					city="银川";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="银川";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="石嘴山";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="吴忠";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="固原";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="中卫";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				case 31:
					list6.clear();
					list6.add("乌鲁木齐");
					list6.add("克拉玛依");
					list6.add("吐鲁番");
					list6.add("哈密");
					list6.add("和田");
					list6.add("阿克苏");
					list6.add("喀什");
					list6.add("克孜勒苏柯尔克孜");
					list6.add("巴音郭勒蒙古");
					list6.add("昌吉");
					list6.add("博尔塔拉蒙古");
					list6.add("伊犁哈萨克");
					list6.add("塔城");
					list6.add("阿勒泰");
					adapter6.notifyDataSetChanged();
					
					city="乌鲁木齐";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="乌鲁木齐";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="克拉玛依";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="吐鲁番";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="哈密";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="和田";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="阿克苏";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="喀什";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="克孜勒苏柯尔克孜";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="巴音郭勒蒙古";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="昌吉";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="博尔塔拉蒙古";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="伊犁哈萨克";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="塔城";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="阿勒泰";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				
				default:
					list6.clear();
					list6.add("香港");
					list6.add("澳门");
					list6.add("台湾");
					adapter6.notifyDataSetChanged();
					
					city="香港";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="香港";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="澳门";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="台湾";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							}
							adapter6.notifyDataSetChanged();
							arg0.setVisibility(View.VISIBLE);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
							arg0.setVisibility(View.VISIBLE);
						}
						
					});
					break;
				}
				// 通知Spinner2数据已更改
				adapter1.notifyDataSetChanged();
				//mySpinner1.setSelection(arg2);
				arg0.setVisibility(View.VISIBLE);
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				arg0.setVisibility(View.VISIBLE);
			}
		});
        //大类的选择
        list2.add("所有大类");
        list2.add("服饰");
		list2.add("鞋靴");
		list2.add("家电");
		list2.add("数码");
		list2.add("图书");
		list2.add("食品");
		list2.add("汽车用品");
		list2.add("运动户外");
		list2.add("电脑办公");
		list2.add("家居家装");
		list2.add("个护化妆");
		list2.add("其他商品");
        mySpinner2 = (Spinner)findViewById(R.id.s_sortbig); 
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list2); 
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner2.setAdapter(adapter2); 
        mySpinner2.setSelection(SearchFragment.bigcatindex);
        
        //与大类相关的小类的选择   
        mySpinner3 = (Spinner) findViewById(R.id.s_sortsmall);
		adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list3);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner3.setAdapter(adapter3);

		mySpinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) 
					{
						switch (arg2) {
						case 0:
							list3.clear();
							list3.add("所有小类");
							category1=null;	category2=null;	
							Loger.i("执行两次");
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							break;
						case 1:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("男装");
							list3.add("女装");
							list3.add("服饰配件");
							list3.add("其他");
							category1="0";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() 
							{
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;        
					    				case 1:
					    					category2="0";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 2:
					    					category2="1";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 3:
					    					category2="2";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				default:
					    					category2="3";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 2:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("男鞋");
							list3.add("女鞋");
							list3.add("其他");
							category1="1";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;        
					    				case 1:
					    					category2="10";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 2:
					    					category2="11";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				default:
					    					category2="12";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 3:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("大家电");
							list3.add("生活电器");
							list3.add("厨房电器");
							list3.add("其他");
							category1="2";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;        
					    				case 1:
					    					category2="20";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 2:
					    					category2="21";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 3:
					    					category2="22";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				default:
					    					category2="23";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 4:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("手机");
							list3.add("手机配件");
							list3.add("摄影");
							list3.add("数码配件");
							list3.add("其他");
							category1="3";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;        
					    				case 1:
					    					category2="30";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 2:
					    					category2="31";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 3:
					    					category2="32";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 4:
					    					category2="33";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				default:
					    					category2="34";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 5:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("电子书");
							list3.add("人文");
							list3.add("科学");
							list3.add("教育");
							list3.add("少儿");
							list3.add("其他");
							category1="4";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;        
					    				case 1:
					    					category2="40";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 2:
					    					category2="41";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 3:
					    					category2="42";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 4:
					    					category2="43";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				case 5:
					    					category2="44";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				default:
					    					category2="45";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 6:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("进口食品");
							list3.add("休闲食品");
							list3.add("地方特产");
							list3.add("其他");
							category1="5";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="50";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="51";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="52";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="53";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 7:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("维修保养");
							list3.add("汽车装饰");
							list3.add("美容清洗");
							list3.add("其他");
							category1="6";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="60";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="61";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="62";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="63";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 8:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("运动服鞋");
							list3.add("户外装备");
							list3.add("体育用品");
							list3.add("其他");
							category1="7";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="70";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="71";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="72";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="73";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 9:
							SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("电脑整机");
							list3.add("电脑设备");
							list3.add("办公用品");
							list3.add("其他");
							category1="8";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="80";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="81";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="82";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="83";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
						case 10:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("厨具");
							list3.add("家居");
							list3.add("灯具");
							list3.add("家装软饰");
							list3.add("其他");
							category1="9";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="90";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="91";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="92";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 4:
					    					category2="93";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="94";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;
							
						case 11:
							//SearchFragment.bigcatindex=-1;    //暂时防止不停刷新
							list3.clear();
							list3.add("所有小类");
							list3.add("面部护肤");
							list3.add("身体护肤");
							list3.add("洗头护发");
							list3.add("口腔护理");
							list3.add("其他");
							category1="10";	category2=null;	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							mySpinner3.setSelection(0);
							mySpinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					    		public void onItemSelected(AdapterView<?> arg0, View arg1,
					    					int arg2, long arg3) 
					    			{
					    				switch (arg2) 
					    				{
					    				case 0:
					    					category2=null;	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;        
					    				case 1:
					    					category2="100";	
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 2:
					    					category2="101";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 3:
					    					category2="102";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				case 4:
					    					category2="103";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				default:
					    					category2="104";
					    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
											break;
					    				}
					    				// 通知Spinner2数据已更改
					    				adapter3.notifyDataSetChanged();
					    				//mySpinner5.setSelection(arg2);
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    			public void onNothingSelected(AdapterView<?> arg0) {
					    				arg0.setVisibility(View.VISIBLE);
					    			}
					    		});
							break;

						default:
							list3.clear();
							list3.add("其他");
							category1="11";	category2="110";	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							break;
						}
						// 通知Spinner2数据已更改
						adapter3.notifyDataSetChanged();
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});

		
		//拼单状态选择
		list4.add("所有状态");    
        list4.add("等待拼单");
        list4.add("人数已满");
        list4.add("拼单成功");    
        
        mySpinner4 = (Spinner)findViewById(R.id.pinstatus); 
        adapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list4); 
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner4.setAdapter(adapter4);  
        mySpinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
    					int arg2, long arg3) 
    			{
    				switch (arg2) 
    				{
    				case 0:
    					status=null;	
    					Loger.i("执行三次");
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;        
    				case 1:
    					status="0";	
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;
    				case 2:
    					status="1";	
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;
    				default:
    					status="2";	
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;
    				}
    				// 通知Spinner2数据已更改
    				adapter4.notifyDataSetChanged();
    				//mySpinner5.setSelection(arg2);
    				arg0.setVisibility(View.VISIBLE);
    			}
    			public void onNothingSelected(AdapterView<?> arg0) {
    				arg0.setVisibility(View.VISIBLE);
    			}
    		});
        
        //拼单类别
        list5.add("所有类型");    
        list5.add("网购拼单");
        list5.add("实体店拼单");
        
        mySpinner5 = (Spinner)findViewById(R.id.whetheronline); 
        adapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list5); 
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner5.setAdapter(adapter5);  
        mySpinner5.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
    		public void onItemSelected(AdapterView<?> arg0, View arg1,
    					int arg2, long arg3) 
    			{
    				switch (arg2) 
    				{
    				case 0:
    					whetherOnline=null;	
    					Loger.i("执行四次");
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;        
    				case 1:
    					whetherOnline="1";	
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;
    				default:
    					whetherOnline="0";	
    					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
    					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
						break;
    				}
    				// 通知Spinner2数据已更改
    				adapter5.notifyDataSetChanged();
    				//mySpinner5.setSelection(arg2);
    				arg0.setVisibility(View.VISIBLE);
    			}
    			public void onNothingSelected(AdapterView<?> arg0) {
    				arg0.setVisibility(View.VISIBLE);
    			}
    		});

        listView = (PullToRefreshListView)findViewById(R.id.list_searchresult);
        listView.setOnItemClickListener(new OnItemClickListenerImpl());
		Loger.i("list0: " + list0);
		listAdapter = new OrderAdapter(
    		SearchResult.this, list0, R.layout.activity_ping, 
    		new String[] { "nickname", "spot",
					"description", "state", "ordertype", "endtime",
					"gender", "pic1" }, new int[] { R.id.nickname,
					R.id.spot, R.id.description, R.id.state,
					R.id.ordertype, R.id.endtime, R.id.gender, R.id.pic1});
		
		listAdapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                    String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
		setListAdapter(listAdapter);
        
      //actionbar返回到消息界面
        ActionBar actionBar = this.getActionBar();
        actionBar.hide();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
        
    	Button back = (Button)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() 
    	{
    		public void onClick(View v) 
    		{
    			finish();
    		}
    	});
		
		sid = this.getIntent().getCharSequenceExtra("sid").toString();
	}
 
	private void pullTorefresh(final String search_content,final String city_1,
			final String category_1,final String category_2,final String status_1,final String whether_Online) {
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Loger.l();
				MyTaskParams params = new MyTaskParams(search_content, city_1, category_1, category_2, status_1, whether_Online);
				new GetSearchDataTask().execute(params);
			}
		});

		listView.setMode(Mode.PULL_FROM_END);
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				listAdapter.notifyDataSetChanged();
			}else {
//				page = msg.what;
			}
		}
	};
	
	//根据限制条件的搜索
	@SuppressLint("HandlerLeak")
	public void GetSearchListxianzhi(final String search_content,final String city_1,
			final String category_1,final String category_2,final String status_1,final String whether_Online) 
	{
//			page = 1;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("content", search_content);      //输入字段的搜索 
			parameters.put("city", city_1);      
			parameters.put("category1", category_1);      
			parameters.put("category2", category_2);      
			parameters.put("status", status_1);       
			parameters.put("whetherOnline", whether_Online); 
//			parameters.put("page", page);
//			parameters.put("queryTime", currentTime);
			
//			Loger.i("第一次page："+page);
			
			Loger.i("content: " + search_content);
			Loger.i("city: " + city);
			Loger.i("category_1: " + category_1);
			Loger.i("category_2: " + category_2);
			Loger.i("status " + status);
			Loger.i("whetherOnline " + whether_Online);
			AVCloud.callFunctionInBackground("searchOrders", parameters,
					new FunctionCallback<HashMap<String, Object>>()
					{
						@SuppressWarnings("unchecked")
						public void done(HashMap<String, Object> object, AVException e) 
						{
							list0.clear();
							if (e == null) 
							{
//								handler.sendEmptyMessage(page);
								Loger.i("搜索结果: " + object);
								if(object.get("orderList")!=null)
								{
									SearchFragment.Searchresult=(List<Map<String, Object>>) object.get("orderList");		
									for(Map<String, Object> map:SearchFragment.Searchresult) 
									{
										nickNameString = map.get("nickName").toString();
										spotString = map.get("address").toString();
										descriptString = map.get("description").toString();
										stateString = map.get("status").toString();
										orderTypeString = map.get("orderType").toString();
										endTimeString = map.get("endTime").toString();
										genderString = map.get("gender").toString();
										orderIdString = map.get("orderId").toString();
										orderCreatorString = map.get("creator").toString();
										
										boolean isCreator = (Boolean) map.get("isCreator");
										boolean isParticipants = (Boolean) map.get("isParticipants");
										
										sid = object.get("sid").toString();
										
								        Map<String, Object> map1 = new HashMap<String, Object>();  
								        map1.put("nickname", nickNameString);  
								        map1.put("spot", spotString);  
								        map1.put("description", descriptString);  
								        map1.put("state", stateString);  
										map1.put("ordertype", orderTypeString);
										
										map1.put("endtime", TimeStamp2Date(endTimeString));
										map1.put("orderId", orderIdString);
										map1.put("ordercreator", orderCreatorString);
										if (getGender(genderString)) {
											map1.put("gender", R.drawable.male);
										} else {
											map1.put("gender", R.drawable.female);
										}
										if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("实体店拼单")) 
										{
											map1.put("pic1", R.drawable.nopic);
											listAdapter.notifyDataSetChanged();
										} 
										else if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("网购拼单")) 
										{
											map1.put("pic1", R.drawable.nopic1);
											listAdapter.notifyDataSetChanged();
										} 
										else 
										{
											imageIdString = ((ArrayList<String>) map.get("images")).get(0);
											System.out.println("图片ID：" + imageIdString);
											getPicUrl(imageIdString, map1, 1);
										}										
										if(map.get("avatar") == null){
											map1.put("portrait", R.drawable.mine);
										}else {
											Loger.i("map.get(avatar)是:" + map.get("avatar"));
											portraitIdString = map.get("avatar").toString();
											System.out.println("头像ID："+portraitIdString);
											getPicUrl(portraitIdString, map1, 2);
										}
										
										map1.put("isCreator", isCreator);
										map1.put("isParticipants", isParticipants);
								        list0.add(map1);     
									}
								}
								else {
									list0.clear();
									listAdapter.notifyDataSetChanged();
								}
						       //listView.setOnItemClickListener(new OnItemClickListenerImpl());
							} 
							else {
								Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
								Toast.makeText(SearchResult.this, e.getMessage(), Toast.LENGTH_SHORT).show();
							}

						};
						
					});

	}
	
	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(SearchResult.this, SeePindan.class);
			/* 通过Bundle对象存储需要传递的数据 */
			Bundle bundle = new Bundle();
			/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
			bundle.putString("orderId", list0.get(position - 1).get("orderId")
					.toString());
			bundle.putString("ordercreator",
					list0.get(position - 1).get("ordercreator").toString());
			bundle.putString("orderstatus", list0.get(position - 1)
					.get("state").toString());
			bundle.putBoolean("isCreator", (Boolean) list0.get(position - 1)
					.get("isCreator"));
			bundle.putBoolean("isParticipants",
					(Boolean) list0.get(position - 1).get("isParticipants"));
			
			Loger.i("list0:" + list0.get(position-1));
			/* 把bundle对象assign给Intent */
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
	
	// 将unix时间戳转换为普通日期
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	// 判断性别
	public boolean getGender(String genderString1) {
		if (genderString1.equals("M")) {
			return true;
		}
		return false;
	}

//	public Bitmap returnBitMap(String url) {
//		URL myFileUrl = null;
//		Bitmap bitmap = null;
//		try {
//			myFileUrl = new URL(url);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		try {
//			HttpURLConnection conn = (HttpURLConnection) myFileUrl
//					.openConnection();
//			conn.setDoInput(true);
//			conn.connect();
//			InputStream is = conn.getInputStream();
//			bitmap = BitmapFactory.decodeStream(is);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return bitmap;
//	}

	// 通过图片的objectId搜索得到url
	public void getPicUrl(String imageIdString, final Map<String, Object> map1, final int imageIdType) {
		if (imageIdType == 2) {
			Loger.i("头像url获取中");
		}
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
					Loger.i(imageIdType + avObjects.get(0).toString());
					String urlString = (String) avObjects.get(0).get("url");

					if(imageIdType == 1){
						urlString = (String) AVFile.withAVObject(avObjects.get(0)).getThumbnailUrl(false, PinFragment.width*5/8, PinFragment.width*5/8);
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (bitmap != null) {
											Loger.l();
											map1.put("pic1", bitmap);
											listAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							Loger.l();
							map1.put("pic1", bitmap);
							listAdapter.notifyDataSetChanged();
						}
					}else {
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (bitmap != null) {
											Loger.l();
											map1.put("portrait", bitmap);
											listAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							Loger.l();
							map1.put("portrait", bitmap);
							listAdapter.notifyDataSetChanged();
						}
					}
					handler.sendEmptyMessage(0);
					
//					new Thread() {
//						public void run() {
//							Loger.i(urlString);
//							Bitmap b = returnBitMap(urlString);
//							if(imageIdType == 1){
//								map1.put("pic1", b);
//							}else{
//								map1.put("portrait", b);
//							}
//							Loger.i(b + "");
//							handler.sendEmptyMessage(0);
//						};
//					}.start();

					Loger.i(urlString);
				} else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	       switch (item.getItemId()) {  
	       case android.R.id.home:  
	    	   Intent intent = new Intent(SearchResult.this, MainActivity.class);	//其实想返回搜索标签页的
	    	   startActivity(intent);
	    	   finish();
	           break;  
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetSearchDataTask extends
			AsyncTask<MyTaskParams, Void, HashMap<String, Object>> {

		@Override
		protected HashMap<String, Object> doInBackground(MyTaskParams... params) {
//			page++;//每次上拉刷新前page++
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("content", params[0].search_content);      //输入字段的搜索 
			parameters.put("city", params[0].city_1);      
			parameters.put("category1", params[0].category_1);      
			parameters.put("category2", params[0].category_2);      
			parameters.put("status", params[0].status_1);       
			parameters.put("whetherOnline", params[0].whether_Online);
			parameters.put("sid", sid);
//			parameters.put("page", page);
//			parameters.put("queryTime", currentTime);
			Loger.i("上拉刷新:"+parameters);
			
			try {
				HashMap<String, Object> list = AVCloud.callFunction(
						"searchOrders", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			super.onPostExecute(result);
			if (result != null) {
//				handler.sendEmptyMessage(page);
//				list0.clear();
					Loger.i("刷新后搜索结果: " + result);
					if(result.get("orderList")!=null)
					{
						SearchFragment.Searchresult=(List<Map<String, Object>>) result.get("orderList");		
						for(Map<String, Object> map:SearchFragment.Searchresult) 
						{
							nickNameString = map.get("nickName").toString();
							spotString = map.get("address").toString();
							descriptString = map.get("description").toString();
							stateString = map.get("status").toString();
							orderTypeString = map.get("orderType").toString();
							endTimeString = map.get("endTime").toString();
							genderString = map.get("gender").toString();
							orderIdString = map.get("orderId").toString();
							orderCreatorString = map.get("creator").toString();
							
							boolean isCreator = (Boolean) map.get("isCreator");
							boolean isParticipants = (Boolean) map.get("isParticipants");
							
							sid = result.get("sid").toString();
							
					        Map<String, Object> map1 = new HashMap<String, Object>();  
					        map1.put("nickname", nickNameString);  
					        map1.put("spot", spotString);  
					        map1.put("description", descriptString);  
					        map1.put("state", stateString);  
							map1.put("ordertype", orderTypeString);
							
							map1.put("endtime", TimeStamp2Date(endTimeString));
							map1.put("orderId", orderIdString);
							map1.put("ordercreator", orderCreatorString);
							if (getGender(genderString)) {
								map1.put("gender", R.drawable.male);
							} else {
								map1.put("gender", R.drawable.female);
							}
							
							if (((ArrayList<String>) map.get("images")).size()==0) 
							{
								map1.put("pic1", "null");
							} 
							else 
							{
								imageIdString = ((ArrayList<String>) map.get("images")).get(0);
								System.out.println("图片ID：" + imageIdString);
								getPicUrl(imageIdString, map1, 1);
							}
							
							if(map.get("avatar") == null){
								map1.put("portrait", R.drawable.mine);
							}else {
								Loger.i("map.get(avatar)是:" + map.get("avatar"));
								portraitIdString = map.get("avatar").toString();
								System.out.println("头像ID："+portraitIdString);
								getPicUrl(portraitIdString, map1, 2);
							}
							map1.put("isCreator", isCreator);
							map1.put("isParticipants", isParticipants);
					        list0.add(map1);     
						}
					}
			       //listView.setOnItemClickListener(new OnItemClickListenerImpl());
				listAdapter.notifyDataSetChanged();
			}
			listView.onRefreshComplete();
		}
	}

	private static class MyTaskParams {
		String search_content;
		String city_1;
		String category_1;
		String category_2;
		String status_1;
		String whether_Online;
		
		MyTaskParams(String search_content,String city_1,String category_1,
				String category_2,String status_1,String whether_Online) {
			Loger.l();
			this.search_content = search_content;
			this.city_1 = city_1;
			this.category_1 = category_1;
			this.category_2 = category_2;
			this.status_1 = status_1;
			this.whether_Online = whether_Online;
		}
	}
}
