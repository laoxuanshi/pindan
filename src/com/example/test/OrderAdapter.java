package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OrderAdapter extends SimpleAdapter 
{
	private List<? extends Map<String, ?>> mData;  
	private LayoutInflater listContainer;           //ÊÓÍ¼ÈÝÆ÷  
	
	public OrderAdapter(Context context,List<? extends Map<String, ?>> data, int resource, String[] from,int[] to) 
	{
		super(context, data, resource, from, to);
		mData=data;
	}
	
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) 
	 {
//		 Loger.i("mData"+mData);
		 View myView = super.getView(position, convertView, parent);
		 TextView type = (TextView) myView.findViewById(R.id.ordertype);
		 TextView status = (TextView) myView.findViewById(R.id.state);
		 TextView nic = (TextView) myView.findViewById(R.id.nickname);
		 TextView description = (TextView) myView.findViewById(R.id.description);
		 Typeface typeface = Typeface.create("Times New Roman", Typeface.BOLD);
		 nic.setTypeface(typeface);
		 description.setTypeface(typeface);
		 ImageView pic = (ImageView) myView.findViewById(R.id.pic1);
		 try
		 {
			 if (mData.get(position).get("ordertype").toString().equals(" Íø¹ºÆ´µ¥ ")) 
			 { 
				 status.setBackgroundResource(R.drawable.onlineorder);
				 status.setTextColor(android.graphics.Color.parseColor("#00A09A"));
				 type.setBackgroundColor(android.graphics.Color.parseColor("#00A09A"));
				 pic.setBackgroundColor(android.graphics.Color.parseColor("#00A09A"));
			 }
			 else
			 {
				 type.setBackgroundColor(android.graphics.Color.parseColor("#FF5C48"));
				 status.setTextColor(android.graphics.Color.parseColor("#FF5C48"));
				 status.setBackgroundResource(R.drawable.offlineorder);
				 pic.setBackgroundColor(android.graphics.Color.parseColor("#FF5C48"));
			 }
		 }
		catch(NullPointerException e)
		{
		}
		 return myView;
	 }
}