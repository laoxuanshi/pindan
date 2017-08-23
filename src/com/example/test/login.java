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
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
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
				Toast.makeText(login.this, "登录失败", Toast.LENGTH_SHORT).show();
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
		actionBar.setTitle("                         登录");
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
			// 已缓存用户
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
					Toast.makeText(login.this, "手机号码不可为空", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (codeString.isEmpty()) {
					Toast.makeText(login.this, "密码不可为空", Toast.LENGTH_SHORT)
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
									// //打开实时通信功能
									// List<String> peerIds = new
									// LinkedList<String>();
									// session.open(userid, peerIds);
									// //peerIds好友id

									Map<String, Object> parameters = new HashMap<String, Object>();
									parameters.put("userId", userid);
									AVCloud.callFunctionInBackground(
											"verifyProfile", parameters,
											new FunctionCallback<String>() {
												public void done(String object,
														AVException e) {
													if (e == null) {
														Loger.i("是否设置过 : "
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
														Loger.d("失败"
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
									Toast.makeText(login.this, "登录错误",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
				break;

			case R.id.button3:
				
				// 创建授权认证信息
		        mAuthInfo = new AuthInfo(login.this, MainActivity.WeiboAPP_Key, "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1", SCOPE);
		        mSsoHandler = new SsoHandler(login.this, mAuthInfo);
		        mSsoHandler.authorize(new AuthListener());
		        
				//以下是使用avoscloud提供的微博授权登录接口，但是在分享到微博时jar包发生冲突，所以改用微博官方的授权登录
//				// weibo
//				type = SNSType.AVOSCloudSNSSinaWeibo;
//				// callback 函数
//		        final SNSCallback myCallback = new SNSCallback() {
//		            @Override
//		            public void done(SNSBase object, SNSException e) {
//		                if (e == null) {
//		                	snsBase = object;
//		                	Loger.i("微博授权成功，请注册账号绑定!");
//							Toast.makeText(getApplicationContext(), "微博授权成功，请注册账号绑定!", Toast.LENGTH_SHORT).show();
//							Intent intent = new Intent(login.this, signup1.class);
//							startActivity(intent);
//		                }
//		            }
//		        };
//
//		        // 关联
//		        try {
////					SNS.setupPlatform(type, "3270028111", "a8f90ed32f508712b3549f952ef23d53", "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1");
//					SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo,
//					      "https://leancloud.cn/1.1/sns/goto/fi6uhskbvx9xrzk1");
//				} catch (AVException e1) {
//					e1.printStackTrace();
//				}
//		        SNS.loginWithCallback(login.this, SNSType.AVOSCloudSNSSinaWeibo, myCallback);
		        //下面的方法与上面的方法没有区别，均是直接登录
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
//									Loger.i("授权成功!!");
//									Toast.makeText(getApplicationContext(), "授权成功!", Toast.LENGTH_SHORT).show();
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
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     * 
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(login.this, mAccessToken);
                Toast.makeText(login.this, 
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                long ex = AccessTokenKeeper.readAccessToken(login.this).getExpiresTime();
                Loger.i("微博授权成功，请注册账号绑定!" + "\nexpiresTime:" +ex);
				Intent intent = new Intent(login.this, signup1.class);
				startActivity(intent);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
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
