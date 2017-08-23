package com.example.test;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.Session;
import com.avos.avoscloud.SessionManager;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.widget.LoginButton;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
	EditText phoneNameText;
	EditText codeText;
//	static SNSType type;
//	static SNSBase snsBase;
	private AuthInfo mAuthInfo;
	/** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
    private SsoHandler mSsoHandler;
    /** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ�����  */
    private Oauth2AccessToken mAccessToken;
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	

//	int[] imgId = { R.drawable.ic_launcher, R.drawable.ic_launcher2,
//			R.drawable.ic_launcher3, R.drawable.i4, R.drawable.i5 };

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(login.this, "��¼ʧ��", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
//		snsBase = null;
//		type = null;

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         ��¼");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		phoneNameText = (EditText) findViewById(R.id.phonenumber);
		codeText = (EditText) findViewById(R.id.code);

		Button loginbtn = (Button) findViewById(R.id.loginbtn);
		Button registerbtn = (Button) findViewById(R.id.registerbtn);
		Button forgetbtn = (Button) findViewById(R.id.forgetbtn);
		Button btn3 = (Button) findViewById(R.id.button3);
		Button btn4 = (Button) findViewById(R.id.button4);
		Button btn5 = (Button) findViewById(R.id.button5);

		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			// �ѻ����û�
			Intent intent = new Intent(login.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("fagment_index", 3);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		}

		loginbtn.setOnClickListener(new ButtonListener());
		registerbtn.setOnClickListener(new ButtonListener());
		forgetbtn.setOnClickListener(new ButtonListener());
		btn3.setOnClickListener(new ButtonListener());
		btn4.setOnClickListener(new ButtonListener());
		btn5.setOnClickListener(new ButtonListener());

	}

	public class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.registerbtn:
				Intent intent1 = new Intent(login.this, signup1.class);
				startActivity(intent1);
				break;

			case R.id.forgetbtn:
				Intent intent2 = new Intent(login.this, resetPassword1.class);
				startActivity(intent2);
				break;

			case R.id.loginbtn:
				final String phoneNumberString = phoneNameText.getText()
						.toString();
				final String codeString = codeText.getText().toString();

				if (phoneNumberString.isEmpty()) {
					Toast.makeText(login.this, "�ֻ����벻��Ϊ��", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (codeString.isEmpty()) {
					Toast.makeText(login.this, "���벻��Ϊ��", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				AVUser.loginByMobilePhoneNumberInBackground(phoneNumberString,
						codeString, new LogInCallback() {
							@Override
							public void done(AVUser user, AVException e) {
								if (user != null) {
									String userid = user.getObjectId();
									Loger.i("userid: " + userid);
									String s1 = userid + "rec";
									String s2 = userid + "comment";
									String s3 = userid + "state";
									PushService.subscribe(login.this, s1,PushActivity.class);
									PushService.subscribe(login.this, s2,CommentActivity.class);
									PushService.subscribe(login.this, s3,StateActivity.class);
									AVInstallation.getCurrentInstallation().saveInBackground();

									// Session session =
									// SessionManager.getInstance(userid);
									// //��ʵʱͨ�Ź���
									// List<String> peerIds = new
									// LinkedList<String>();
									// session.open(userid, peerIds);
									// //peerIds����id

									Map<String, Object> parameters = new HashMap<String, Object>();
									parameters.put("userId", userid);
									AVCloud.callFunctionInBackground(
											"verifyProfile", parameters,
											new FunctionCallback<String>() {
												public void done(String object,
														AVException e) {
													if (e == null) {
														Loger.i("�Ƿ����ù� : "
																+ object);
														if (object.trim()
																.equals("0")) {
															Intent intent = new Intent(
																	login.this,
																	xinxi1.class);
															startActivity(intent);
														} else {
															Intent intent = new Intent(
																	login.this,
																	MainActivity.class);
															Bundle bundle = new Bundle();
															bundle.putInt(
																	"fagment_index",
																	3);
															intent.putExtras(bundle);
															startActivity(intent);
															finish();
														}

													} else {
														Loger.d("ʧ��"
																+ e.getCode()
																+ ", "
																+ e.getMessage());
														Toast.makeText(
																login.this,
																e.getMessage(),
																Toast.LENGTH_SHORT)
																.show();
													}
												};
											});

								} else {
									Loger.i(e.getMessage());
									Loger.l();
									Toast.makeText(login.this, "��¼����",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
				break;

			case R.id.button3:
				
				// ������Ȩ��֤��Ϣ
		        mAuthInfo = new AuthInfo(login.this, MainActivity.WeiboAPP_Key, "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1", SCOPE);
		        mSsoHandler = new SsoHandler(login.this, mAuthInfo);
		        mSsoHandler.authorize(new AuthListener());
		        
				//������ʹ��avoscloud�ṩ��΢����Ȩ��¼�ӿڣ������ڷ���΢��ʱjar��������ͻ�����Ը���΢���ٷ�����Ȩ��¼
//				// weibo
//				type = SNSType.AVOSCloudSNSSinaWeibo;
//				// callback ����
//		        final SNSCallback myCallback = new SNSCallback() {
//		            @Override
//		            public void done(SNSBase object, SNSException e) {
//		                if (e == null) {
//		                	snsBase = object;
//		                	Loger.i("΢����Ȩ�ɹ�����ע���˺Ű�!");
//							Toast.makeText(getApplicationContext(), "΢����Ȩ�ɹ�����ע���˺Ű�!", Toast.LENGTH_SHORT).show();
//							Intent intent = new Intent(login.this, signup1.class);
//							startActivity(intent);
//		                }
//		            }
//		        };
//
//		        // ����
//		        try {
////					SNS.setupPlatform(type, "3270028111", "a8f90ed32f508712b3549f952ef23d53", "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1");
//					SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo,
//					      "https://leancloud.cn/1.1/sns/goto/fi6uhskbvx9xrzk1");
//				} catch (AVException e1) {
//					e1.printStackTrace();
//				}
//		        SNS.loginWithCallback(login.this, SNSType.AVOSCloudSNSSinaWeibo, myCallback);
		        //����ķ���������ķ���û�����𣬾���ֱ�ӵ�¼
//				try {
//    		          SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo,
//    		              "https://leancloud.cn/1.1/sns/goto/fi6uhskbvx9xrzk1");
//
//    		          SNS.loginWithCallback(login.this, SNSType.AVOSCloudSNSSinaWeibo,
//    		              new SNSCallback() {
//
//							@Override
//							public void done(SNSBase base, SNSException error) {
//								if(error == null){
//									Loger.i("��Ȩ�ɹ�!!");
//									Toast.makeText(getApplicationContext(), "��Ȩ�ɹ�!", Toast.LENGTH_SHORT).show();
//									Intent intent = new Intent(login.this, signup1.class);
//    		                    	startActivity(intent);
//								}
//							}
//    		              });
//    		        } catch (AVException e) {
//    		          e.printStackTrace();
//    		        }
				
				break;
			case R.id.button4:
				// weixin
				break;
			case R.id.button5:
				// koukou
//				type = SNSType.AVOSCloudSNSQQ;
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
			Intent intent5 = new Intent(login.this, MainActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("fagment_index", 3);
			intent5.putExtras(bundle);
			startActivity(intent5);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
    /**
     * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
     * 
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // SSO ��Ȩ�ص�
        // ��Ҫ������ SSO ��½�� Activity ������д onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * ΢����֤��Ȩ�ص��ࡣ
     * 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е��� {@link SsoHandler#authorizeCallBack} ��
     *    �ûص��Żᱻִ�С�
     * 2. �� SSO ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С�
     * ����Ȩ�ɹ����뱣��� access_token��expires_in��uid ����Ϣ�� SharedPreferences �С�
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // �� Bundle �н��� Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                
                // ���� Token �� SharedPreferences
                AccessTokenKeeper.writeAccessToken(login.this, mAccessToken);
                Toast.makeText(login.this, 
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                long ex = AccessTokenKeeper.readAccessToken(login.this).getExpiresTime();
                Loger.i("΢����Ȩ�ɹ�����ע���˺Ű�!" + "\nexpiresTime:" +ex);
				Intent intent = new Intent(login.this, signup1.class);
				startActivity(intent);
            } else {
                // ���¼�������������յ� Code��
                // 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
                // 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
                // 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(login.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(login.this, 
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(login.this, 
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
}
