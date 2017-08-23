package com.example.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSType;
import com.example.test.activity.MainActivity;
import com.example.test.login.AuthListener;
import com.example.test.util.Loger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.UsersAPI;

public class shezhi extends Activity 
{
	public static int tishiyin = 1;
	String Installationstring;
	private ToggleButton weiboSwitch;
	private TextView weiboTextView;
	private String nicknameString;
	
	private AuthInfo mAuthInfo;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;
    private LinearLayout changePassword,logoutbtn;
    
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shezhi);
		
        ActionBar actionBar = this.getActionBar();     
        actionBar.setTitle("                        设置");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
        
        Installationstring=AVInstallation.getCurrentInstallation().getObjectId();
        Loger.i("Installationstring"+Installationstring);
        
        final ToggleButton switchTest1 = (ToggleButton) findViewById(R.id.switch1); 
        final ToggleButton switchTest2 = (ToggleButton) findViewById(R.id.switch2); 
        final ToggleButton switchTest3 = (ToggleButton) findViewById(R.id.switch3); 
        final ToggleButton switchTest4 = (ToggleButton) findViewById(R.id.switch4); 
		final String s1=AVUser.getCurrentUser().getObjectId()+"rec";
		final String s2=AVUser.getCurrentUser().getObjectId()+"comment";
		final String s3=AVUser.getCurrentUser().getObjectId()+"state";
		
		changePassword = (LinearLayout) findViewById(R.id.changePasswordLinearLayout);
		logoutbtn = (LinearLayout)findViewById(R.id.logoutbtn);
		weiboTextView = (TextView) findViewById(R.id.weiboName);
		weiboSwitch = (ToggleButton) findViewById(R.id.weiboSwitch);
		if(AccessTokenKeeper.readAccessToken(shezhi.this).getUid().equals("uid")||
				AccessTokenKeeper.readAccessToken(shezhi.this).getUid() == ""||
				AccessTokenKeeper.readAccessToken(shezhi.this).getUid() == null){
			//微博未授权
			weiboSwitch.setChecked(false);
		}else {
			//微博已授权
			weiboSwitch.setChecked(true);
			final long weiboUid = Long.valueOf(AccessTokenKeeper.readAccessToken(shezhi.this).getUid());
			
			new Thread() {
				public void run() {
					// 获得用户信息
					UsersAPI usersAPI = new UsersAPI(shezhi.this,
							MainActivity.WeiboAPP_Key,
							AccessTokenKeeper.readAccessToken(shezhi.this));
					JSONObject object = null;
					try {
						object = new JSONObject(usersAPI.showSync(weiboUid));//uid must be long 
					} catch (JSONException e) {
						e.printStackTrace();
					}
					try {
						nicknameString = (String) object.get("name");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Loger.i("nickname:" + nicknameString);
					handler.sendEmptyMessage(0);
				};
			}.start();
		}
		
        
		AVQuery<AVObject> query = new AVQuery<AVObject>("_Installation");
		query.whereEqualTo("objectId", Installationstring);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
					ArrayList<String> r=(ArrayList<String>)avObjects.get(0).get("channels");
					for(int i=0;i<r.size();i++)
					{
	        			if(r.get(i).equals(s1))
	        				switchTest1.setChecked(true);
	        			if(r.get(i).equals(s2))
	        				switchTest2.setChecked(true);
	        			if(r.get(i).equals(s3))
	        				switchTest3.setChecked(true);
					}
				} 
				else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
		
		if(tishiyin==1)
			switchTest4.setChecked(true);
		
		switchTest1.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{             
            @Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
            {  
                if(isChecked) 
					PushService.subscribe(shezhi.this, s1, PushActivity.class);
                else 
                	PushService.unsubscribe(shezhi.this, s1);
            }  
        }); 
		
		switchTest2.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{             
            @Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
            {  
                if(isChecked) 
					PushService.subscribe(shezhi.this, s2, CommentActivity.class);
                else 
                	PushService.unsubscribe(shezhi.this, s2);
            }  
        }); 
		
		switchTest3.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{             
            @Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
            {  
                if(isChecked) 
					PushService.subscribe(shezhi.this, s3, StateActivity.class);
                else 
                	PushService.unsubscribe(shezhi.this, s3);
            }  
        }); 
		
		switchTest4.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{             
            @Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
            {  
                if(isChecked) 
					tishiyin=1;
                else 
                	tishiyin=0;
            }  
        }); 

		weiboSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					//微博授权操作
					mAuthInfo = new AuthInfo(shezhi.this, MainActivity.WeiboAPP_Key, "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1", login.SCOPE);
			        mSsoHandler = new SsoHandler(shezhi.this, mAuthInfo);
			        mSsoHandler.authorize(new AuthListener());
			        
				} else {
					
					AccessTokenKeeper.clear(shezhi.this);
					  SNS.logout(AVUser.getCurrentUser(), SNSType.AVOSCloudSNSSinaWeibo, new SaveCallback() {
					      @Override
					      public void done(AVException e) {
					    	  Loger.i("解除绑定成功！");
					    	  weiboTextView.setText("新浪微博");
					    	  Toast.makeText(shezhi.this, "解除绑定成功", Toast.LENGTH_SHORT).show();
					      }
					  });
					
//					//弹出对话框，询问是否取消微博绑定 但是这样在点击取消时会再次绑定，未找到合适解决方案
//					Dialog dialog = new AlertDialog.Builder(shezhi.this).setMessage("确定要取消微博账户的绑定吗？")
//							.setNegativeButton("取消", new OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									weiboSwitch.setChecked(true);
//								}
//							})
//							.setPositiveButton("确定", new OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									weiboSwitch.setChecked(false);
//									AccessTokenKeeper.clear(shezhi.this);
//									  SNS.logout(AVUser.getCurrentUser(), SNSType.AVOSCloudSNSSinaWeibo, new SaveCallback() {
//									      @Override
//									      public void done(AVException e) {
//									    	  Loger.i("解除绑定成功！");
//									    	  weiboTextView.setText("新浪微博");
//									    	  Toast.makeText(shezhi.this, "解除绑定成功", Toast.LENGTH_SHORT).show();
//									      }
//									  });
//								}
//							}).show();
				}
			}
		});
		
		changePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(shezhi.this, resetPassword1.class);
				startActivity(intent);
			}
		});
		
		logoutbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				AlertDialog.Builder builder = new Builder(shezhi.this);  
				builder.setTitle("提示")
					   .setMessage("您确定要退出登录吗？")  
				       .setCancelable(false)  
				       .setPositiveButton("是", new DialogInterface.OnClickListener() {  
				           public void onClick(DialogInterface dialog, int id) {  
								String s1=AVUser.getCurrentUser().getObjectId()+"rec";
				    			String s2=AVUser.getCurrentUser().getObjectId()+"comment";
				    			String s3=AVUser.getCurrentUser().getObjectId()+"state";
				    			PushService.unsubscribe(shezhi.this, s1);
				    			PushService.unsubscribe(shezhi.this, s2);
				    			PushService.unsubscribe(shezhi.this, s3);
								AVInstallation.getCurrentInstallation().saveInBackground();
								AVUser.logOut();
								Intent intent = new Intent(shezhi.this, login.class);
								startActivity(intent);
				           }  
				       })  
				       .setNegativeButton("否", new DialogInterface.OnClickListener() {  
				           public void onClick(DialogInterface dialog, int id) {  
				                dialog.cancel();  
				           }  
				       });  
				AlertDialog alert = builder.create(); 
				builder.show();
			}
		});
		
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				weiboTextView.setText(nicknameString);
			}
		}
	};

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
	                AccessTokenKeeper.writeAccessToken(shezhi.this, mAccessToken);
	                Toast.makeText(shezhi.this, 
	                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
	                Loger.i("微博授权成功!");
	                final long weiboUid = Long.valueOf(mAccessToken.getUid());
	                new Thread() {
	    				public void run() {
	    					// 获得用户信息
	    					UsersAPI usersAPI = new UsersAPI(shezhi.this,
	    							MainActivity.WeiboAPP_Key,
	    							AccessTokenKeeper.readAccessToken(shezhi.this));
	    					JSONObject object = null;
	    					try {
	    						object = new JSONObject(usersAPI.showSync(weiboUid));//uid must be long 
	    					} catch (JSONException e) {
	    						e.printStackTrace();
	    					}
	    					try {
	    						nicknameString = (String) object.get("name");
	    					} catch (JSONException e) {
	    						e.printStackTrace();
	    					}
	    					Loger.i("nickname:" + nicknameString);
	    					handler.sendEmptyMessage(0);
	    				};
	    			}.start();
	                
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
	                Toast.makeText(shezhi.this, message, Toast.LENGTH_LONG).show();
	            }
	        }

	        @Override
	        public void onCancel() {
	            Toast.makeText(shezhi.this, 
	                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
	        }

	        @Override
	        public void onWeiboException(WeiboException e) {
	            Toast.makeText(shezhi.this, 
	                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
	        }
	    }
	    
}
