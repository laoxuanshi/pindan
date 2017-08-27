package com.example.test.MineFragment;

import java.util.HashMap;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
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

public class gerenxinxi_city extends Activity 
{
	EditText citycontent = null;
	public static String citycontent1 = null;
	public static String userIdString = null;
	String cityString;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cityedit);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Button btn = (Button) findViewById(R.id.savecity);
		btn.setOnClickListener(new ButtonListener());
		Button btn1 = (Button) findViewById(R.id.quxiaocity);
		btn1.setOnClickListener(new ButtonListener());
		citycontent = (EditText) findViewById(R.id.editct);

		Bundle bundle = this.getIntent().getExtras();
		cityString = bundle.getString("city");
		citycontent.setText(cityString);
	}


	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.savecity:
				citycontent1 = citycontent.getText().toString();
				if (!citycontent1.trim().isEmpty()) 
				{
			        userIdString = AVUser.getCurrentUser().getObjectId();
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("userId", userIdString);
					parameters.put("usualCity", citycontent1);
					AVCloud.callFunctionInBackground("updateUserProfile", parameters,
							new FunctionCallback<Object>() {
								public void done(Object object, AVException e) {
									if (e == null) {
										Loger.i("���и��ĳɹ�: " + object);
										Intent intent1 = new Intent();
										intent1.putExtra("city", citycontent1);
										gerenxinxi_city.this.setResult(RESULT_OK, intent1);
										gerenxinxi_city.this.finish();
									} else {
										Loger.d("sms result: " + e.getCode() + ", "
												+ e.getMessage());
									}
								};
							});
				} 
				else
					Toast.makeText(gerenxinxi_city.this, "��������Ϊ��", Toast.LENGTH_SHORT)
							.show();
				break;
			case R.id.quxiaocity:
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

}
