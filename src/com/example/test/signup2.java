package com.example.test;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;

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

public class signup2 extends Activity {
	EditText verifyText;
	Button verifyButton;
	String phongString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup2);
		phongString = (String) this.getIntent().getCharSequenceExtra("phonenumber");

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         зЂВс");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		verifyButton = (Button) findViewById(R.id.verify);
		verifyText = (EditText) findViewById(R.id.verifyText);

		verifyButton.setOnClickListener(new ButtonListener());
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.verify:
				String code = verifyText.getText().toString();
				verifyCode(code);
				break;
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
			Intent intent = new Intent(signup2.this,signup1.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void verifyCode(String code) {
		AVOSCloud.verifySMSCodeInBackground(code,phongString,
				new AVMobilePhoneVerifyCallback() {
					@Override
					public void done(AVException e) {
						if (e == null) {
							toast(R.string.verifySucceed);
							Intent intent = new Intent(signup2.this,
									signup3.class);
							startActivity(intent);
							finish();
						} else {
							e.printStackTrace();
							toast(R.string.verifyFailed);
						}
					}
				});
	}

	private void toast(int id) {
		Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
	}
}
