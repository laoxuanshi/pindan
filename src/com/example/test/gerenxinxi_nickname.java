package com.example.test;

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

public class gerenxinxi_nickname extends Activity 
{
	EditText nicknamecontent = null;
	public static String nicknamecontent1 = null;
	public static String userIdString = null;
	String nickNameString;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nicknameedit);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Button btn = (Button) findViewById(R.id.savenickname);
		btn.setOnClickListener(new ButtonListener());
		Button btn1 = (Button) findViewById(R.id.quxiaonickname);
		btn1.setOnClickListener(new ButtonListener());
		nicknamecontent = (EditText) findViewById(R.id.editnc);

		Bundle bundle = this.getIntent().getExtras();
		nickNameString = bundle.getString("nickname");
		nicknamecontent.setText(nickNameString);
	}


	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.savenickname:
				nicknamecontent1 = nicknamecontent.getText().toString();
				if (!nicknamecontent1.trim().isEmpty()) 
				{
			        userIdString = AVUser.getCurrentUser().getObjectId();
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("userId", userIdString);
					parameters.put("nickName", nicknamecontent1);
					AVCloud.callFunctionInBackground("updateUserProfile", parameters,
							new FunctionCallback<Object>() {
								public void done(Object object, AVException e) {
									if (e == null) {
										Loger.i("昵称更改成功: " + object);
										Intent intent1 = new Intent();
										intent1.putExtra("nickName", nicknamecontent1);
										gerenxinxi_nickname.this.setResult(RESULT_OK, intent1);
										gerenxinxi_nickname.this.finish();
									} else {
										Loger.d("sms result: " + e.getCode() + ", "
												+ e.getMessage());
									}
								};
							});
				} 
				else
					Toast.makeText(gerenxinxi_nickname.this, "输入内容为空", Toast.LENGTH_SHORT)
							.show();
				break;
			case R.id.quxiaonickname:
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
