package com.example.test.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.content.Intent;
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.support.v4.app.FragmentActivity;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; 
   


import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.*;
//import com.example.pindan.TitleView.OnLeftButtonClickListener;
import com.example.test.util.Loger;


  
/**  
 *  功能描述：Searchfragment页面 
 */  
public class SearchFragment extends Fragment 
{  
  
    private View mParent;  
      
    private FragmentActivity mActivity;        
    private TextView mText;  
      
    EditText searchcontent=null; 
    Button getsearch;
    Button dalei1,dalei2,dalei3,dalei4,dalei5,dalei6,dalei7,dalei8,dalei9,dalei10,dalei11,dalei12;
    public static String searchcontent1 = null;
    public static String cato1 = null;
    public static String showsearch = null;
    public static int bigcatindex=0;
    
    public static  List<Map<String, Object>> Searchresult=new ArrayList<Map<String, Object>>();
   

    public static SearchFragment newInstance(int index) {  
        SearchFragment f = new SearchFragment();  
  
        // Supply index input as an argument.  
        Bundle args = new Bundle();  
        args.putInt("index", index);  
        f.setArguments(args);  
  
        return f;  
    }  
  
    public int getShownIndex() {  
        return getArguments().getInt("index", 0);  
    }  
  
 	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) 
 	{  
        View view = inflater.inflate(R.layout.activity_search2, container, false);
        
        searchcontent = (EditText)view.findViewById(R.id.searchcontent);
        getsearch = (Button)view.findViewById(R.id.getsearch);
        getsearch.setOnClickListener(new OnClickListener() 
        {

        	public void onClick(View v) 
        	{
        		bigcatindex=0;
        		searchcontent1 = searchcontent.getText().toString();
        		if(!searchcontent1.trim().isEmpty())
        		{
        			cato1=null;
        			showsearch=searchcontent1;
        			GetSearchList(searchcontent1,cato1);
        		}
        		else
        			Toast.makeText(getActivity(), "输入错误",Toast.LENGTH_SHORT).show();
        	}
		});
        
        dalei1 = (Button)view.findViewById(R.id.img_fushi);
        dalei1.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
        			showsearch="服饰";
        		}
        		cato1="0";
        		bigcatindex=1;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        
        dalei2 = (Button)view.findViewById(R.id.img_xiexue);
        dalei2.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
        			showsearch="鞋靴";
        		}
        		cato1="1";
        		bigcatindex=2;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        
        dalei3 = (Button)view.findViewById(R.id.img_jiadian);
        dalei3.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="家电";
        		}
        		cato1="2";
        		bigcatindex=3;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        
        dalei4 = (Button)view.findViewById(R.id.img_shuma);
        dalei4.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="数码";
        		}
        		cato1="3";
        		bigcatindex=4;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        
        dalei5 = (Button)view.findViewById(R.id.img_tushu);
        dalei5.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="图书";
        		}
        		cato1="4";
        		bigcatindex=5;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        
        dalei6 = (Button)view.findViewById(R.id.img_shipin);
        dalei6.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="食品";
        		}
        		cato1="5";
        		bigcatindex=6;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei7 = (Button)view.findViewById(R.id.img_qicheyongpin);
        dalei7.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="汽车用品";
        		}
        		cato1="6";
        		bigcatindex=7;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei8 = (Button)view.findViewById(R.id.img_yundonghuwai);
        dalei8.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="运动户外";
        		}
        		cato1="7";
        		bigcatindex=8;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei9 = (Button)view.findViewById(R.id.img_diannaobangong);
        dalei9.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="电脑办公";
        		}
        		cato1="8";
        		bigcatindex=9;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei10 = (Button)view.findViewById(R.id.img_jiajujiazhuang);
        dalei10.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="家居家装";
        		}
        		cato1="9";
        		bigcatindex=10;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei11 = (Button)view.findViewById(R.id.img_gehuhuazhuang);
        dalei11.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="个护化妆";
        		}
        		cato1="10";
        		bigcatindex=11;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        dalei12 = (Button)view.findViewById(R.id.img_qitashangpin);
        dalei12.setOnClickListener(new OnClickListener() 
        {
        	public void onClick(View v) 
        	{
        		searchcontent1 = searchcontent.getText().toString();
        		showsearch=searchcontent1;
        		if(searchcontent1.trim().isEmpty())
        		{
        			searchcontent1=",";
            		showsearch="其它";
        		}
        		cato1="11";
        		bigcatindex=12;
        		GetSearchList(searchcontent1,cato1);
        	}
		});
        return view;  
   }  
  //根据内容或大类的搜索
 	public void GetSearchList(final String search_content,final String cat_1) 
 	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("content", search_content);      //输入字段的搜索 
		parameters.put("category1", cat_1);      //输入字段的搜索 
		AVCloud.callFunctionInBackground("searchOrders", parameters,
				new FunctionCallback<HashMap<String, Object>>()
				{
					@SuppressWarnings("unchecked")
					public void done(HashMap<String, Object> object, AVException e) {
						if (e == null) {
							if(object.get("orderList") != null){
								Loger.i("搜索结果: " + object);
								Loger.i("sid:" + object.get("sid"));
							
								Searchresult=(List<Map<String, Object>>) object.get("orderList");							
								//Toast.makeText(getActivity(), String.valueOf(object), Toast.LENGTH_SHORT).show();
							}
							Intent intent = new Intent(getActivity(), SearchResult.class);
							intent.putExtra("sid", object.get("sid").toString());
							startActivity(intent);
						} 
						else {
							Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
							if(e.getCode()==0)
								Toast.makeText(getActivity(), "无网络连接",Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
 	
 	
    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        
    }  
    @Override  
    public void onHiddenChanged(boolean hidden) {  
        super.onHiddenChanged(hidden);  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
    }  
  
}  