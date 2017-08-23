package com.example.test;

import com.example.test.activity.MainActivity;
import com.example.test.fragment.MineFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class SeePindan1 extends FragmentActivity{
	
	protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.seepindan1);  
  
        ActionBar actionBar = this.getActionBar();     
        actionBar.setTitle("");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
    }
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	   

	//�ӷ���ƴ�����淵�ص�������
	@Override  
	   public boolean onOptionsItemSelected(MenuItem item) {  
	       switch (item.getItemId()) {  
	       case android.R.id.home:  
	    	   Intent intent2=new Intent(SeePindan1.this, MainActivity.class);
	    	   startActivity(intent2);
	    	   finish();
	           break;  
	       }  
	       return super.onOptionsItemSelected(item);  
	   } 

}
