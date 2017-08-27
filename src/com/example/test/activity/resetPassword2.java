package com.example.test.activity;

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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

public class resetPassword2 extends Activity{

	private EditText passwordEditText;
	private EditText repasswordEditText;
	private EditText verifyEditText;
	private Button verifyButton;
	
	private String passwordString;
	private String repasswordString;
	private String verifyString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpassword2);
		
		passwordEditText = (EditText)findViewById(R.id.password_reset);
		repasswordEditText = (EditText)findViewById(R.id.repassword_reset);
		verifyEditText = (EditText)findViewById(R.id.verifyText_reset);
		verifyButton = (Button)findViewById(R.id.verify_reset);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         重置密码");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		
		verifyButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				passwordString = passwordEditText.getText().toString();
				repasswordString = repasswordEditText.getText().toString();
				verifyString = verifyEditText.getText().toString();
				
				if(passwordString.length() == 0 || passwordString.equals("") ||
						repasswordString.length() == 0 || repasswordString.equals("") ||
								verifyString.length() == 0 || verifyString.equals("")){
					Toast.makeText(resetPassword2.this, "密码或验证码不可为空", Toast.LENGTH_SHORT).show();
				}else {
					if (!passwordString.equals(repasswordString)) {
						Toast.makeText(resetPassword2.this, "请输入相同密码", Toast.LENGTH_SHORT).show();
					}else {
						verify(verifyString, passwordString);
					}
				}
				
			}
		});
	}
	
	protected void verify(String verifyString, String passwordString) {
		  AVUser.resetPasswordBySmsCodeInBackground(verifyString, passwordString, 
				  new UpdatePasswordCallback() {
		      @Override
		      public void done(AVException e) {
		        if(e == null){
		        //密码更改成功了！
		        	Loger.i("密码更改成功");
		        	Toast.makeText(resetPassword2.this, "密码重置成功", Toast.LENGTH_SHORT).show();
		        	Intent intent = new Intent(resetPassword2.this, login.class);
		        	startActivity(intent);
		        	finish();
		        }
		        else {
		        	Loger.i("密码更改失败" + e.getMessage());
		        	Toast.makeText(resetPassword2.this, "密码重置失败", Toast.LENGTH_SHORT).show();
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
