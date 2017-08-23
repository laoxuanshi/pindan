package com.example.test;

import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.util.Loger;

public class signup1 extends Activity {

	EditText phoneNumber;
	Button getIdentifyingCode;
	boolean res;
	private static String phone = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup1);

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         зЂВс");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		phoneNumber = (EditText) findViewById(R.id.phone_number);
		getIdentifyingCode = (Button) findViewById(R.id.get_identifying_code);

		getIdentifyingCode.setOnClickListener(new ButtonListener());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		phoneNumber.setEnabled(true);
		if (phone != null && !phone.equals("")) {
			phoneNumber.setText(phone);
		}
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.get_identifying_code:
				phone = phoneNumber.getText().toString();
				sendCode(phone);
				phoneNumber.setEnabled(false);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

	public void sendCode(final String phone) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("mobilePhoneNumber", phone);
		AVCloud.callFunctionInBackground("requestRegisterSms", parameters,
				new FunctionCallback<Object>() {
					public void done(Object object, AVException e) {
						if (e == null) {
							Intent intent = new Intent(signup1.this, signup2.class);
							intent.putExtra("phonenumber", phone);
							startActivity(intent);
							Loger.i("sms result : " + object);
							Toast.makeText(signup1.this, String.valueOf(object), Toast.LENGTH_SHORT).show();
							MyApplication.setUserPhoneNume(phone);
							finish();
						} else {
							Loger.d("sms result: " + e.getCode() + ", " + e.getMessage());
							Toast.makeText(signup1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
						}
						phoneNumber.setEnabled(true);
					};
				});
	}
}
