package com.example.test.SearchFragment;

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
	private OrderAdapter listAdapter = null; // �������ݵ�ת������
	private Spinner mySpinner1, mySpinner2, mySpinner3, mySpinner4, mySpinner5, mySpinner6;    
    private ArrayAdapter<String> adapter1, adapter2, adapter3, adapter4, adapter5, adapter6; 
    
	TextView showsearch; 
	public static String city,category2,status, whetherOnline=null;
	public static String category1=SearchFragment.cato1;
	
	private PullToRefreshListView listView;	// ����ListView���
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
//		Loger.i(currentTime+"ʱ���");
//		page = 1;
		setContentView(R.layout.searchresult_listview);

		mImageDownLoader = new ImageDownLoader(this);
		sid = this.getIntent().getCharSequenceExtra("sid").toString();
		
		showsearch =(TextView)findViewById(R.id.searchtextshow);
		showsearch.setText("������"+SearchFragment.showsearch) ;
		
		//���е�ѡ��
		list1.add("����ʡ��");    
        list1.add("����");
        list1.add("�Ϻ�");
        list1.add("���");
        list1.add("����");
        list1.add("�ӱ�");
        list1.add("ɽ��");
        list1.add("���ɹ�");
        list1.add("����");
        list1.add("����");
        list1.add("������");
        list1.add("����");
        list1.add("�㽭");
        list1.add("����");
        list1.add("����");
        list1.add("����");
        list1.add("ɽ��");
        list1.add("����");
        list1.add("����");
        list1.add("����");
        list1.add("�㶫");
        list1.add("����");
        list1.add("����");
        list1.add("�Ĵ�");
        list1.add("����");
        list1.add("����");
        list1.add("����");
        list1.add("����");
        list1.add("����");
        list1.add("�ຣ");
        list1.add("����");
        list1.add("�½�");
        list1.add("�۰�̨");
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
					list6.add("���г���");
					mySpinner6.setSelection(0);
					city=null;
					Loger.i("ִ��һ��");
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 1:
					list6.clear();
					list6.add("����");
					mySpinner6.setSelection(0);
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 2:
					list6.clear();
					list6.add("�Ϻ�");
					mySpinner6.setSelection(0);
					city="�Ϻ�";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 3:
					list6.clear();
					list6.add("���");
					mySpinner6.setSelection(0);
					city="���";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 4:
					list6.clear();
					list6.add("����");
					mySpinner6.setSelection(0);
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					adapter6.notifyDataSetChanged();
					break;        
				case 5:
					list6.clear();
					list6.add("ʯ��ׯ");
					list6.add("��ɽ");
					list6.add("�ػʵ�");
					list6.add("����");
					list6.add("��̨");
					list6.add("����");
					list6.add("�żҿ�");
					list6.add("�е�");
					list6.add("����");
					list6.add("�ȷ�");
					list6.add("��ˮ");
					adapter6.notifyDataSetChanged();
					
					city="ʯ��ׯ";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="ʯ��ׯ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="�ػʵ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��̨";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="�żҿ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�е�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="�ȷ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��ˮ";	
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
					list6.add("̫ԭ");
					list6.add("��ͬ");
					list6.add("��Ȫ");
					list6.add("����");
					list6.add("����");
					list6.add("˷��");
					list6.add("����");
					list6.add("�˳�");
					list6.add("����");
					list6.add("�ٷ�");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="̫ԭ";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="̫ԭ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="��ͬ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��Ȫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="˷��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�˳�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="�ٷ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("���ͺ���");
					list6.add("��ͷ");
					list6.add("�ں�");
					list6.add("���");
					list6.add("ͨ��");
					list6.add("������˹");
					list6.add("���ױ���");
					list6.add("�����׶�");
					list6.add("�����첼");
					list6.add("�˰�");
					list6.add("���ֹ���");
					list6.add("������");
					adapter6.notifyDataSetChanged();
					
					city="���ͺ���";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="���ͺ���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="��ͷ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="�ں�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="ͨ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="������˹";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="���ױ���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�����׶�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="�����첼";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="�˰�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="���ֹ���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="������";	
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
					list6.add("����");
					list6.add("����");
					list6.add("��ɽ");
					list6.add("��˳");
					list6.add("��Ϫ");
					list6.add("����");
					list6.add("����");
					list6.add("Ӫ��");
					list6.add("����");
					list6.add("����");
					list6.add("�̽�");
					list6.add("����");
					list6.add("����");
					list6.add("��«��");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��˳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��Ϫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="Ӫ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="�̽�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��«��";	
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
					list6.add("����");
					list6.add("����");
					list6.add("��ƽ");
					list6.add("��Դ");
					list6.add("ͨ��");
					list6.add("��ɽ");
					list6.add("��ԭ");
					list6.add("�׳�");
					list6.add("�ӱ�");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��ƽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��Դ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="ͨ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��ԭ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�׳�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="�ӱ�";	
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
					list6.add("������");
					list6.add("�������");
					list6.add("����");
					list6.add("�׸�");
					list6.add("˫Ѽɽ");
					list6.add("����");
					list6.add("����");
					list6.add("��ľ˹");
					list6.add("��̨��");
					list6.add("ĵ����");
					list6.add("�ں�");
					list6.add("�绯");
					list6.add("���˰���");
					adapter6.notifyDataSetChanged();
					
					city="������";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="�������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="�׸�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="˫Ѽɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="��ľ˹";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="��̨��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="ĵ����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="�ں�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="�绯";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="���˰���";	
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
					list6.add("�Ͼ�");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("��ͨ");
					list6.add("���Ƹ�");
					list6.add("����");
					list6.add("�γ�");
					list6.add("����");
					list6.add("��");
					list6.add("̩��");
					list6.add("��Ǩ");
					adapter6.notifyDataSetChanged();
					
					city="�Ͼ�";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="�Ͼ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="��ͨ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="���Ƹ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="�γ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="̩��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��Ǩ";	
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
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("��");
					list6.add("����");
					list6.add("��ɽ");
					list6.add("̨��");
					list6.add("��ˮ");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="̨��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��ˮ";	
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
					list6.add("�Ϸ�");
					list6.add("�ߺ�");
					list6.add("����");
					list6.add("����");
					list6.add("��ɽ");
					list6.add("����");
					list6.add("ͭ��");
					list6.add("����");
					list6.add("��ɽ");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="�Ϸ�";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="�Ϸ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="�ߺ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="ͭ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("Ȫ��");
					list6.add("����");
					list6.add("��ƽ");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="Ȫ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��ƽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("�ϲ�");
					list6.add("������");
					list6.add("Ƽ��");
					list6.add("�Ž�");
					list6.add("����");
					list6.add("ӥ̶");
					list6.add("����");
					list6.add("����");
					list6.add("�˴�");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="�ϲ�";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="�ϲ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="Ƽ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="�Ž�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="ӥ̶";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="�˴�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("�ൺ");
					list6.add("�Ͳ�");
					list6.add("��ׯ");
					list6.add("��Ӫ");
					list6.add("��̨");
					list6.add("Ϋ��");
					list6.add("����");
					list6.add("����");
					list6.add("̩��");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("�ĳ�");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="�ൺ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="�Ͳ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��ׯ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��Ӫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="��̨";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="Ϋ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="̩��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="�ĳ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("֣��");
					list6.add("����");
					list6.add("����");
					list6.add("ƽ��ɽ");
					list6.add("����");
					list6.add("�ױ�");
					list6.add("����");
					list6.add("����");
					list6.add("���");
					list6.add("���");
					list6.add("���");
					list6.add("����Ͽ");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("�ܿ�");
					list6.add("פ���");
					adapter6.notifyDataSetChanged();
					
					city="֣��";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="֣��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="ƽ��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="�ױ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����Ͽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="�ܿ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="פ���";	
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
					list6.add("�人");
					list6.add("��ʯ");
					list6.add("�差");
					list6.add("ʮ��");
					list6.add("����");
					list6.add("�˲�");
					list6.add("����");
					list6.add("����");
					list6.add("Т��");
					list6.add("�Ƹ�");
					list6.add("����");
					list6.add("����");
					list6.add("��ʩ");
					adapter6.notifyDataSetChanged();
					
					city="�人";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="�人";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="��ʯ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="�差";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="ʮ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="�˲�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="Т��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="�Ƹ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��ʩ";	
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
					list6.add("��ɳ");
					list6.add("����");
					list6.add("��̶");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("�żҽ�");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("¦��");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="��ɳ";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="��ɳ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��̶";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�żҽ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="¦��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("����");
					list6.add("�麣");
					list6.add("��ͷ");
					list6.add("�ع�");
					list6.add("��ɽ");
					list6.add("����");
					list6.add("տ��");
					list6.add("ï��");
					list6.add("����");
					list6.add("����");
					list6.add("÷��");
					list6.add("��β");
					list6.add("��Դ");
					list6.add("����");
					list6.add("��Զ");
					list6.add("��ݸ");
					list6.add("��ɽ");
					list6.add("����");
					list6.add("����");
					list6.add("�Ƹ�");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="�麣";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��ͷ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="�ع�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="տ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="ï��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="÷��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="��β";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="��Դ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="��Զ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 16:
								city="��ݸ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 17:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 18:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 19:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="�Ƹ�";	
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
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("���Ǹ�");
					list6.add("����");
					list6.add("���");
					list6.add("����");
					list6.add("��ɫ");
					list6.add("����");
					list6.add("�ӳ�");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="���Ǹ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="��ɫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="�ӳ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("�ɶ�");
					list6.add("�Թ�");
					list6.add("��֦��");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("��Ԫ");
					list6.add("����");
					list6.add("�ڽ�");
					list6.add("��ɽ");
					list6.add("�ϳ�");
					list6.add("�˱�");
					list6.add("�㰲");
					list6.add("����");
					list6.add("üɽ");
					list6.add("�Ű�");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("��ɽ");
					adapter6.notifyDataSetChanged();
					
					city="�ɶ�";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="�ɶ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="�Թ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��֦��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��Ԫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="�ڽ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="�ϳ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="�˱�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="�㰲";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="üɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 15:
								city="�Ű�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 16:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 17:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 18:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 19:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��ɽ";	
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
					list6.add("����");
					list6.add("����ˮ");
					list6.add("����");
					list6.add("��˳");
					list6.add("ͭ��");
					list6.add("�Ͻ�");
					list6.add("ǭ����");
					list6.add("ǭ����");
					list6.add("ǭ��");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����ˮ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��˳";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="ͭ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="�Ͻ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="ǭ����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="ǭ����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="ǭ��";	
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
					list6.add("����");
					list6.add("����");
					list6.add("��Ϫ");
					list6.add("��ɽ");
					list6.add("��ͨ");
					list6.add("����");
					list6.add("�ն�");
					list6.add("�ٲ�");
					list6.add("��ɽ");
					list6.add("���");
					list6.add("��˫����");
					list6.add("����");
					list6.add("����");
					list6.add("�º�");
					list6.add("ŭ��");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��Ϫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��ͨ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="�ն�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�ٲ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="��˫����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 13:
								city="�º�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 14:
								city="ŭ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("����");
					list6.add("ɽ��");
					list6.add("�տ���");
					list6.add("����");
					list6.add("����");
					list6.add("��֥");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="ɽ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="�տ���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="��֥";	
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
					list6.add("����");
					list6.add("ͭ��");
					list6.add("����");
					list6.add("����");
					list6.add("μ��");
					list6.add("�Ӱ�");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="ͭ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="μ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="�Ӱ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("������");
					list6.add("���");
					list6.add("����");
					list6.add("��ˮ");
					list6.add("����");
					list6.add("��Ҵ");
					list6.add("ƽ��");
					list6.add("��Ȫ");
					list6.add("����");
					list6.add("����");
					list6.add("¤��");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="��ˮ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��Ҵ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="ƽ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="��Ȫ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="¤��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("����");
					list6.add("ʯ��ɽ");
					list6.add("����");
					list6.add("��ԭ");
					list6.add("����");
					adapter6.notifyDataSetChanged();
					
					city="����";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="ʯ��ɽ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="��ԭ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����";	
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
					list6.add("��³ľ��");
					list6.add("��������");
					list6.add("��³��");
					list6.add("����");
					list6.add("����");
					list6.add("������");
					list6.add("��ʲ");
					list6.add("�������տ¶�����");
					list6.add("���������ɹ�");
					list6.add("����");
					list6.add("���������ɹ�");
					list6.add("���������");
					list6.add("����");
					list6.add("����̩");
					adapter6.notifyDataSetChanged();
					
					city="��³ľ��";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="��³ľ��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="��������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 2:
								city="��³��";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 3:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 4:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 5:
								city="������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 6:
								city="��ʲ";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 7:
								city="�������տ¶�����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 8:
								city="���������ɹ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 9:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 10:
								city="���������ɹ�";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 11:
								city="���������";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 12:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="����̩";	
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
					list6.add("���");
					list6.add("����");
					list6.add("̨��");
					adapter6.notifyDataSetChanged();
					
					city="���";	
					GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
					
					mySpinner6.setSelection(0);
					mySpinner6.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

						public void onItemSelected(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							switch (arg2) {
							case 0:
								city="���";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							case 1:
								city="����";	
								GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
								break;
							default:
								city="̨��";	
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
				// ֪ͨSpinner2�����Ѹ���
				adapter1.notifyDataSetChanged();
				//mySpinner1.setSelection(arg2);
				arg0.setVisibility(View.VISIBLE);
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				arg0.setVisibility(View.VISIBLE);
			}
		});
        //�����ѡ��
        list2.add("���д���");
        list2.add("����");
		list2.add("Ьѥ");
		list2.add("�ҵ�");
		list2.add("����");
		list2.add("ͼ��");
		list2.add("ʳƷ");
		list2.add("������Ʒ");
		list2.add("�˶�����");
		list2.add("���԰칫");
		list2.add("�ҾӼ�װ");
		list2.add("������ױ");
		list2.add("������Ʒ");
        mySpinner2 = (Spinner)findViewById(R.id.s_sortbig); 
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list2); 
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        mySpinner2.setAdapter(adapter2); 
        mySpinner2.setSelection(SearchFragment.bigcatindex);
        
        //�������ص�С���ѡ��   
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
							list3.add("����С��");
							category1=null;	category2=null;	
							Loger.i("ִ������");
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							break;
						case 1:
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("��װ");
							list3.add("Ůװ");
							list3.add("�������");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("��Ь");
							list3.add("ŮЬ");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("��ҵ�");
							list3.add("�������");
							list3.add("��������");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("�ֻ�");
							list3.add("�ֻ����");
							list3.add("��Ӱ");
							list3.add("�������");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("������");
							list3.add("����");
							list3.add("��ѧ");
							list3.add("����");
							list3.add("�ٶ�");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("����ʳƷ");
							list3.add("����ʳƷ");
							list3.add("�ط��ز�");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("ά�ޱ���");
							list3.add("����װ��");
							list3.add("������ϴ");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("�˶���Ь");
							list3.add("����װ��");
							list3.add("������Ʒ");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("��������");
							list3.add("�����豸");
							list3.add("�칫��Ʒ");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("����");
							list3.add("�Ҿ�");
							list3.add("�ƾ�");
							list3.add("��װ����");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							//SearchFragment.bigcatindex=-1;    //��ʱ��ֹ��ͣˢ��
							list3.clear();
							list3.add("����С��");
							list3.add("�沿����");
							list3.add("���廤��");
							list3.add("ϴͷ����");
							list3.add("��ǻ����");
							list3.add("����");
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
					    				// ֪ͨSpinner2�����Ѹ���
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
							list3.add("����");
							category1="11";	category2="110";	
							GetSearchListxianzhi(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							pullTorefresh(SearchFragment.searchcontent1,city,category1,category2,status,whetherOnline);
							break;
						}
						// ֪ͨSpinner2�����Ѹ���
						adapter3.notifyDataSetChanged();
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});

		
		//ƴ��״̬ѡ��
		list4.add("����״̬");    
        list4.add("�ȴ�ƴ��");
        list4.add("��������");
        list4.add("ƴ���ɹ�");    
        
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
    					Loger.i("ִ������");
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
    				// ֪ͨSpinner2�����Ѹ���
    				adapter4.notifyDataSetChanged();
    				//mySpinner5.setSelection(arg2);
    				arg0.setVisibility(View.VISIBLE);
    			}
    			public void onNothingSelected(AdapterView<?> arg0) {
    				arg0.setVisibility(View.VISIBLE);
    			}
    		});
        
        //ƴ�����
        list5.add("��������");    
        list5.add("����ƴ��");
        list5.add("ʵ���ƴ��");
        
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
    					Loger.i("ִ���Ĵ�");
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
    				// ֪ͨSpinner2�����Ѹ���
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
        
      //actionbar���ص���Ϣ����
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
	
	//������������������
	@SuppressLint("HandlerLeak")
	public void GetSearchListxianzhi(final String search_content,final String city_1,
			final String category_1,final String category_2,final String status_1,final String whether_Online) 
	{
//			page = 1;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("content", search_content);      //�����ֶε����� 
			parameters.put("city", city_1);      
			parameters.put("category1", category_1);      
			parameters.put("category2", category_2);      
			parameters.put("status", status_1);       
			parameters.put("whetherOnline", whether_Online); 
//			parameters.put("page", page);
//			parameters.put("queryTime", currentTime);
			
//			Loger.i("��һ��page��"+page);
			
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
								Loger.i("�������: " + object);
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
										if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("ʵ���ƴ��")) 
										{
											map1.put("pic1", R.drawable.nopic);
											listAdapter.notifyDataSetChanged();
										} 
										else if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("����ƴ��")) 
										{
											map1.put("pic1", R.drawable.nopic1);
											listAdapter.notifyDataSetChanged();
										} 
										else 
										{
											imageIdString = ((ArrayList<String>) map.get("images")).get(0);
											System.out.println("ͼƬID��" + imageIdString);
											getPicUrl(imageIdString, map1, 1);
										}										
										if(map.get("avatar") == null){
											map1.put("portrait", R.drawable.mine);
										}else {
											Loger.i("map.get(avatar)��:" + map.get("avatar"));
											portraitIdString = map.get("avatar").toString();
											System.out.println("ͷ��ID��"+portraitIdString);
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
								Loger.d("ʧ��:" + e.getCode() + ", " + e.getMessage());
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
			/* ͨ��Bundle����洢��Ҫ���ݵ����� */
			Bundle bundle = new Bundle();
			/* �ַ����ַ������������ֽ����顢�������ȵȣ������Դ� */
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
			/* ��bundle����assign��Intent */
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
	
	// ��unixʱ���ת��Ϊ��ͨ����
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	// �ж��Ա�
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

	// ͨ��ͼƬ��objectId�����õ�url
	public void getPicUrl(String imageIdString, final Map<String, Object> map1, final int imageIdType) {
		if (imageIdType == 2) {
			Loger.i("ͷ��url��ȡ��");
		}
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
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
					Log.d("ʧ��", "��ѯ����: " + e.getMessage());
				}
			}
		});
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {  
	       switch (item.getItemId()) {  
	       case android.R.id.home:  
	    	   Intent intent = new Intent(SearchResult.this, MainActivity.class);	//��ʵ�뷵��������ǩҳ��
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
//			page++;//ÿ������ˢ��ǰpage++
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("content", params[0].search_content);      //�����ֶε����� 
			parameters.put("city", params[0].city_1);      
			parameters.put("category1", params[0].category_1);      
			parameters.put("category2", params[0].category_2);      
			parameters.put("status", params[0].status_1);       
			parameters.put("whetherOnline", params[0].whether_Online);
			parameters.put("sid", sid);
//			parameters.put("page", page);
//			parameters.put("queryTime", currentTime);
			Loger.i("����ˢ��:"+parameters);
			
			try {
				HashMap<String, Object> list = AVCloud.callFunction(
						"searchOrders", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("ʧ��:" + e.getCode() + ", " + e.getMessage());
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
					Loger.i("ˢ�º��������: " + result);
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
								System.out.println("ͼƬID��" + imageIdString);
								getPicUrl(imageIdString, map1, 1);
							}
							
							if(map.get("avatar") == null){
								map1.put("portrait", R.drawable.mine);
							}else {
								Loger.i("map.get(avatar)��:" + map.get("avatar"));
								portraitIdString = map.get("avatar").toString();
								System.out.println("ͷ��ID��"+portraitIdString);
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
