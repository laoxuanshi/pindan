package com.example.test.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.test.util.Loger;

public class signup3 extends Activity {
	private EditText password = null;
	private EditText repassword = null;
	private Button commit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup3);

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         ע��");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		commit = (Button) findViewById(R.id.commit);
		password = (EditText) findViewById(R.id.password);
		repassword = (EditText) findViewById(R.id.repassword);

		commit.setEnabled(false);
		commit.setOnClickListener(new ButtonListener());
		TextWatcher watcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String word = password.getText().toString();
				String reword = repassword.getText().toString();
				if (!word.equals(reword)) {
					commit.setEnabled(false);
				} else {
					commit.setEnabled(true);
				}
			}
		};
		password.addTextChangedListener(watcher);
		repassword.addTextChangedListener(watcher);
		repassword.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					String word = password.getText().toString();
					String reword = repassword.getText().toString();
					if (!word.equals(reword)) {
						Toast.makeText(signup3.this, "���벻һ��",
								Toast.LENGTH_SHORT).show();
					} else {
						commit.setEnabled(true);
					}
				}
			}
		});
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.commit:
				String word = password.getText().toString();
				String reword = repassword.getText().toString();
				if (word.equals(reword)) {
					final String phone = MyApplication.getUserPhoneNume();
					final AVUser user = new AVUser();
					user.setUsername(phone);
					user.setPassword(word);
					user.put("mobilePhoneNumber", phone);
					user.signUpInBackground(new SignUpCallback() {
						public void done(AVException e) {
							if (e == null) {
								MyApplication.setUsername(phone);
								String s1=user.getObjectId()+"rec";
								String s2=user.getObjectId()+"comment";
								String s3=user.getObjectId()+"state";
								PushService.subscribe(signup3.this, s1, PushActivity.class);
								PushService.subscribe(signup3.this, s2, CommentActivity.class);
								PushService.subscribe(signup3.this, s3, StateActivity.class);
								AVInstallation.getCurrentInstallation().saveInBackground();
								Toast.makeText(signup3.this, "ע��ɹ�",
										Toast.LENGTH_SHORT).show();
								
								//��΢���˺ź͸ո�ע����˺�
								if (null != AccessTokenKeeper.readAccessToken(signup3.this)) {
									String expiresAtString = AccessTokenKeeper.readAccessToken(signup3.this).getExpiresTime() + "";
									AVUser.AVThirdPartyUserAuth userAuth = new AVUser.AVThirdPartyUserAuth(
											AccessTokenKeeper.readAccessToken(signup3.this).getToken(),
											expiresAtString, "weibo",
											AVUser.getCurrentUser()
													.getObjectId());
									AVUser.associateWithAuthData(
											AVUser.getCurrentUser(), userAuth,
											new SaveCallback() {
												@Override
												public void done(
														AVException arg0) {
													Loger.i("�󶨳ɹ�!!!");
//													SNS.logout(signup3.this, login.type);
												}
											});
								}
								Intent intent = new Intent(signup3.this,
										xinxi1.class);
								startActivity(intent);
								finish();
							} else {
								Toast.makeText(signup3.this, e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
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
			Intent intent = new Intent(signup3.this,signup2.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
