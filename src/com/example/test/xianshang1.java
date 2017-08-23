package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

public class xianshang1  extends Activity 
{
	EditText linkurl=null; 
	public String linkurl1 = null;
	public String titleString;
	public String imgId;
	@Override
	    protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.xianshang1);
	        
	        ActionBar actionBar = this.getActionBar();     
	        actionBar.setTitle("                     线上拼单");
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
	        
	        Button btn3=(Button)findViewById(R.id.ensurelink);	        
	        btn3.setOnClickListener(new ButtonListener());
	        
	        Button btn4=(Button)findViewById(R.id.zhijietianxie);	        
	        btn4.setOnClickListener(new ButtonListener());
	        
	        linkurl = (EditText)findViewById(R.id.shangpinlinkurl);
	  }
	
	 private class ButtonListener implements OnClickListener
     {
     	public void onClick(View v) 
     	{
     		switch(v.getId())
     		{
     		case R.id.ensurelink:
     			linkurl1 = linkurl.getText().toString();
     			if(!linkurl1.trim().isEmpty())
     				jiexilink(linkurl1);
     			else
     				Toast.makeText(xianshang1.this, "输入内容为空",Toast.LENGTH_SHORT).show();
 				break; 	
     		case R.id.zhijietianxie:
 				Intent intent=new Intent(xianshang1.this, xianshang3.class);
 				startActivity(intent);
     			
     		}
     	}
     }
	  
	 
	 private void jiexilink(final String copy_link) 
	 {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("url", copy_link);  			
			Loger.i("copy_link: " + copy_link);
			AVCloud.callFunctionInBackground("parsePage", parameters,
					new FunctionCallback<Map<String, Object>>() {
						public void done(Map<String, Object>  object,
								AVException e) {
							if (e == null) 
							{
								Loger.i("解析成功: " + object);	
								titleString=object.get("title").toString();
								imgId=object.get("imgId").toString();
			     				Intent intent=new Intent(xianshang1.this, xianshang2.class);
			     				intent.putExtra("title", titleString);
			     				intent.putExtra("imgId", imgId);
			     				intent.putExtra("linkurl",linkurl1);
			     				startActivity(intent);
							} 
							else {
								Loger.d("失败" + e.getCode() + ", " + e.getMessage());
								Toast.makeText(getApplicationContext(), "无法解析",
										Toast.LENGTH_SHORT).show();
			     				Intent intent=new Intent(xianshang1.this, xianshang3.class);
			     				startActivity(intent);
							}
						};
					});
	}
	 
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	   
	  @Override  
	   public boolean onOptionsItemSelected(MenuItem item) {  
	       switch (item.getItemId()) {  
	       case android.R.id.home:  
	    	   finish();
	           break;  
	       }  
	       return super.onOptionsItemSelected(item);  
	   } 
}
