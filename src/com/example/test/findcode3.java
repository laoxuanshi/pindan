package com.example.test;

import com.example.test.activity.MainActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

public class findcode3  extends Activity 
{
	@Override
	    protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.findcode3);
	        ActionBar actionBar = this.getActionBar();     
	        actionBar.setTitle("                     ÷ÿ÷√√‹¬Î");
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
	        Button btn9=(Button)findViewById(R.id.btn9);
	        btn9.setOnClickListener(new ButtonListener());
	  }
	  
	
	 private class ButtonListener implements OnClickListener
     {
     	public void onClick(View v) 
     	{
     		switch(v.getId())
     		{
     		case R.id.btn9:
     			Intent intent1=new Intent(findcode3.this, MainActivity.class);
				Bundle bundle1 = new Bundle();
				bundle1.putInt("fagment_index", 0);
				intent1.putExtras(bundle1);
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
	    	   Intent intent2=new Intent(findcode3.this, findcode2.class);
			   startActivity(intent2);
			   finish();
	           break;  
	       }  
	       return super.onOptionsItemSelected(item);  
	   } 
}
