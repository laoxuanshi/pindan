package com.example.test;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

@SuppressLint("NewApi")
public class findcode2  extends Activity 
{
	@Override
	    protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.findcode2);
	        ActionBar actionBar = this.getActionBar();     
	        actionBar.setTitle("                     安全验证");
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
	        Button btn8=(Button)findViewById(R.id.btn8);
	        btn8.setOnClickListener(new ButtonListener());
	  }
	  
	
	 private class ButtonListener implements OnClickListener
     {
     	public void onClick(View v) 
     	{
     		switch(v.getId())
     		{
     		case R.id.btn8:
     			Intent intent1=new Intent(findcode2.this, findcode3.class);
 				startActivity(intent1);
 				finish();
 				break;
     			
     		}
     	}
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
	    	   Intent intent2=new Intent(findcode2.this, findcode1.class);
			   startActivity(intent2);
			   finish();
	           break;  
	       }  
	       return super.onOptionsItemSelected(item);  
	   } 
}
