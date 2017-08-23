package com.example.test;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.example.test.util.Loger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class resetPassword1 extends Activity{
	
	private EditText phoneNumberEditText;
	private Button getIdentifyCodeButton;
	private String phoneNumberString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpassword1);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         找回密码");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		
		phoneNumberEditText = (EditText)findViewById(R.id.phone_number_reset);
		getIdentifyCodeButton = (Button)findViewById(R.id.get_identifying_code_reset);
		
		getIdentifyCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phoneNumberString = phoneNumberEditText.getText().toString();
				if(phoneNumberString.length() == 0 || phoneNumberString.equals("")){
					Toast.makeText(resetPassword1.this, "手机号码不可为空", Toast.LENGTH_SHORT).show();
				}else {
					sendCode(phoneNumberString);
				}
			}
		});
		
	}
	
	protected void sendCode(String phoneNumber) {
		AVUser.requestPasswordResetBySmsCodeInBackground(phoneNumber,
				new RequestMobileCodeCallback() {
					@Override
					public void done(AVException e) {
						if (e == null) {
							Intent intent = new Intent(resetPassword1.this, resetPassword2.class);
							startActivity(intent);
							Loger.i("重置密码验证请求发送成功");
							finish();
						} else {
							Loger.d("sms result: " + e.getCode() + ", " + e.getMessage());
							Toast.makeText(resetPassword1.this, "重置密码验证请求发送失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
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
