package com.example.test.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.Group;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.Session;
import com.avos.avoscloud.SessionManager;
import com.example.test.CommentActivity;
import com.example.test.FragmentIndicator;
import com.example.test.PushActivity;
import com.example.test.R;
import com.example.test.StateActivity;
import com.example.test.login;
import com.example.test.xianshang1;
import com.example.test.xianxia1;
import com.example.test.FragmentIndicator.OnIndicateListener;
import com.example.test.util.Loger;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 功能描述：主Activity类，继承自FragmentActivity
 */
public class MainActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	public static Fragment[] mFragments;
	ActionBar actionBar = null;
	int fagindex=0;
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    public static final String WeiXinAPP_ID = "wxa88f9a40269c2a23";
    public static final String WeiboAPP_Key = "3270028111";

    /** 微博分享的接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE); //设置隐藏titlebar
		setContentView(R.layout.activity_main);
		
		if(AVUser.getCurrentUser()!=null)
		{
			String userid=AVUser.getCurrentUser().getObjectId();
			Session session = SessionManager.getInstance(userid);          //打开实时通信功能
			List<String> peerIds = new LinkedList<String>();
			session.open(userid, peerIds);             //peerIds好友id
			
//			String s1=userid+"rec";
//			String s2=userid+"comment";
//			String s3=userid+"state";
//			Loger.i("再订阅一次");
//			PushService.subscribe(this, s1, PushActivity.class);
//			PushService.subscribe(this, s2, CommentActivity.class);
//			PushService.subscribe(this, s3, StateActivity.class);
//			AVInstallation.getCurrentInstallation().saveInBackground();
		}
		
		try 
		{  
			Bundle bundle = this.getIntent().getExtras();
			fagindex = bundle.getInt("fagment_index");
			setFragmentIndicator(fagindex);
		}
		catch(NullPointerException e)
		{
			setFragmentIndicator(0);
		}
		
		try {  
			ViewConfiguration mconfig = ViewConfiguration.get(this);  
			       java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");  
			       if(menuKeyField != null) {  
			           menuKeyField.setAccessible(true);  
			           menuKeyField.setBoolean(mconfig, false);  
			       }  
			   } catch (Exception ex) {  
			   }  
		//TODO weixin
		api = WXAPIFactory.createWXAPI(this, WeiXinAPP_ID, false);
		api.registerApp(WeiXinAPP_ID); 
		
		// 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, MainActivity.WeiboAPP_Key);
        // 注册到新浪微博
        mWeiboShareAPI.registerApp();
        
//        // 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
//        boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
//        int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sponsor_online:
			if(AVUser.getCurrentUser()!=null)
			{
				Intent intent = new Intent(MainActivity.this, xianshang1.class);
				startActivity(intent);
			}
			else 
				Toast.makeText(getApplicationContext(),"请先登录", Toast.LENGTH_SHORT).show();
			break;

		case R.id.sponsor_offline:
			if(AVUser.getCurrentUser()!=null)
			{
				Intent intent2 = new Intent(MainActivity.this, xianxia1.class);
				startActivity(intent2);
			}
			else
				Toast.makeText(getApplicationContext(),"请先登录", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;

		}
		return true;
	}

	/**
	 * 初始化fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[4];
		mFragments[0] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_pin);
		mFragments[1] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_message);
		mFragments[2] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_search);
		mFragments[3] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_mine);

		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
				.show(mFragments[whichIsDefault]).commit();

		FragmentIndicator mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction()
						.hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]).hide(mFragments[3])
						.show(mFragments[which]).commit();
			}
		});
	}

	// menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainh, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		return false;
	}
}
