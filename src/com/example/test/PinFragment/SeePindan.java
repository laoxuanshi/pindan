package com.example.test.PinFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

import com.avos.avoscloud.*;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;
import com.example.test.util.Util;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class SeePindan extends FragmentActivity {

	static String orderCreatorString = null;
	static String orderIdString = null;
	static String nickNameString = null;
	static String descriptionString = null;
	static String addressString = null;
	static String endTimeString = null;
	static String pinTypeString = null;
	static String pinStateString = null;
	static String cityString = null;
	static String genderString = null;
	static String imageIdString = null;
	static String groupidstring = null;
	static String partipeoplestring = "";
	String portraitIdString;
	String commentPortraitIdString;
	String titleString;
	String linkUrlString;

	String creatorNameString;
	String creator;
	static String huifurenid = null;
	String commentToString;
	String commentContentString;
	String commentId;
	String toNameString;

	TextView nickNameTextView;
	TextView descriptionTextView;
	TextView addressTextView;
	TextView endTimeTextView;
	TextView pinTypeTextView;
	TextView pinStateTextView;
	TextView cityTextView;
	TextView partTextView;
	ImageView genderImageView;
	ImageView pic_seeImageView;
	ImageView portraitImageView;
	Bitmap picSeeBitmap, portraitBitmap;
	Button seeMoreButton;
	LinearLayout shareLinearLayout;

	ScrollView sv;
	int chooseItem = -1;
	String arg0[] = { "回复", "删除" };
	String arg1[] = { "回复" };

	private List<Map<String, Object>> mData;
	private ListViewForScrollView comment_listView; // 定义ListView组件
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作

	private Button modifyOrderButton;
	private Button cancelOrderButton;
	private Button finishOrderButton;
	private Button pauseOrderButton;
	private Button resumeOrderButton;
	private Button partcipateOrderButton;
	private Button quitOrderButton;
	private Button cloneOrderButton;
	private Button CollectOrderButton;
	private Button quxiaocollectButton;
	private Button seeurl;
	private LinearLayout popupwindow_LinearLayout;
	int page = 0;

	static String orderStatusString;
	static boolean isCreator;
	static boolean isParticipants;
	static boolean whetheronline;

	static String category1;
	static String category2;
	static String imageUrlString;
	String BigUrlString;

	static boolean whethercollected;
	/**
	 * Image 下载器
	 */
	private ImageDownLoader mImageDownLoader;
	private static AVGeoPoint geoPoint;
	
	private ProgressDialog progressDialog;
	public final static int modifyOnline_Result = 0;
	public final static int modifyOffline_Result = 1;
	
	/** 微信分享接口实例 */
	private IWXAPI Weixinapi;
	/** 微博分享接口实例 */
    private IWeiboShareAPI  mWeiboShareAPI = null;

	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Weixinapi = WXAPIFactory.createWXAPI(this, MainActivity.WeiXinAPP_ID);
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, MainActivity.WeiboAPP_Key);
		
		progressDialogShow();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏actionBar
		setContentView(R.layout.seepindan);

		mImageDownLoader = new ImageDownLoader(this);

		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件 
		View view = inflater.inflate(R.layout.popupwindow_order_operate, null);
		final PopupWindow pop = new PopupWindow(view,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
		Button order_operateButton = (Button) findViewById(R.id.order_operate);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);
		order_operateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Loger.i("点击了操作按钮");
				if (AVUser.getCurrentUser()!= null)
				{	
					if (pop.isShowing()) 
					{
						pop.dismiss();
					} 
					else 
					{
						pop.showAsDropDown(v);
					}
				}
				else
					Toast.makeText(getApplicationContext(), "请先登录",Toast.LENGTH_SHORT).show();
			}
		});

		Button backButton = (Button) findViewById(R.id.seepindan_back);

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		modifyOrderButton= (Button) view.findViewById(R.id.modifyOrder_btn);
		cancelOrderButton = (Button) view.findViewById(R.id.cancelOrder_btn);
		finishOrderButton = (Button) view.findViewById(R.id.finishOrder_btn);
		pauseOrderButton = (Button) view.findViewById(R.id.pauseOrder_btn);
		resumeOrderButton = (Button) view.findViewById(R.id.resumeOrder_btn);
		partcipateOrderButton = (Button) view
				.findViewById(R.id.partcipateOrder_btn);
		quitOrderButton = (Button) view.findViewById(R.id.quitOrder_btn);

		cloneOrderButton = (Button) view.findViewById(R.id.cloneOrder_btn);

		CollectOrderButton = (Button) view.findViewById(R.id.collectorder);
		quxiaocollectButton = (Button) view.findViewById(R.id.quxiaocollect);

		popupwindow_LinearLayout = (LinearLayout) view
				.findViewById(R.id.popupwindow_LinearLayout);

		modifyOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 修改拼单
				if (pinTypeString.equals("网购拼单"))
				{
					Intent intent1 = new Intent(SeePindan.this,modifyonline.class);
					intent1.putExtra("orderId", orderIdString);
					intent1.putExtra("ordercreator", orderCreatorString);
					
					intent1.putExtra("category1", category1);
					intent1.putExtra("category2", category2);
					intent1.putExtra("description", descriptionString);
					intent1.putExtra("imageUrl", imageUrlString);
					intent1.putExtra("addressclone", addressString);
					intent1.putExtra("cityclone", cityString);
					intent1.putExtra("title", titleString);
					intent1.putExtra("imgId", imageIdString);
					intent1.putExtra("endtime", endTimeString);
//					startActivity(intent1);
					startActivityForResult(intent1, modifyOnline_Result);
				}
				else
				{
					Intent intent2 = new Intent(SeePindan.this,modifyoffline.class);
					intent2.putExtra("orderId", orderIdString);
					intent2.putExtra("ordercreator", orderCreatorString);
					
					intent2.putExtra("category1", category1);
					intent2.putExtra("category2", category2);
					intent2.putExtra("description", descriptionString);
					intent2.putExtra("imageUrl", imageUrlString);
					intent2.putExtra("addressclone", addressString);
					intent2.putExtra("cityclone", cityString);
					intent2.putExtra("imgId", imageIdString);
					intent2.putExtra("endtime", endTimeString);
//					startActivity(intent2);
					startActivityForResult(intent2, modifyOffline_Result);
				}
			}
		});
		
		cancelOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取消拼单后返回首页
				AlertDialog.Builder builder = new Builder(SeePindan.this);  
				builder.setTitle("提示")
					   .setMessage("您确定要取消拼单吗？")  
				       .setCancelable(false)  
				       .setPositiveButton("是", new DialogInterface.OnClickListener() {  
				           public void onClick(DialogInterface dialog, int id) {  
				                orderOperation("cancel");
				                Intent intent = new Intent(getApplicationContext(),
				                		MainActivity.class);
				                startActivity(intent);
				                finish();
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

		finishOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 结束拼单后，状态变成拼单成功，菜单栏改变
				orderOperation("finish");
				cancelOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
				resumeOrderButton.setVisibility(View.GONE);
				pinStateTextView.setText("拼单成功");
				
			}
		});

		pauseOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 暂停拼单后，状态变成人数已满，菜单栏改变
				orderOperation("pause");
				pauseOrderButton.setVisibility(View.GONE);
				resumeOrderButton.setVisibility(View.VISIBLE);
				pinStateTextView.setText("人数已满");
				
			}
		});

		resumeOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 暂停拼单后，状态变成人数已满，菜单栏改变
				Loger.i("恢复拼单");
				orderOperation("resume");
				pauseOrderButton.setVisibility(View.VISIBLE);
				resumeOrderButton.setVisibility(View.GONE);
				pinStateTextView.setText("拼单中");
				
			}
		});
		
		partcipateOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 加入拼单后，状态不变，菜单栏改变
				orderOperation("participate");

				if (AVUser.getCurrentUser() == null)
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
				else {
					isParticipants=true;
					String userId = AVUser.getCurrentUser().getObjectId();
					Session session = SessionManager.getInstance(userId);
					Group group = session.getGroup(groupidstring);
					group.join();
				}

				partcipateOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.VISIBLE);
			}
		});

		quitOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 退出拼单后，回到首页
				orderOperation("quit");
				isParticipants=false;

				String userId = AVUser.getCurrentUser().getObjectId();
				Session session = SessionManager.getInstance(userId);
				Group group = session.getGroup(groupidstring);
				group.quit();

				partcipateOrderButton.setVisibility(View.VISIBLE);
				quitOrderButton.setVisibility(View.GONE);
			}
		});

		cloneOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 克隆拼单
				if (AVUser.getCurrentUser() == null) {
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
				} else {
					if (pinTypeString.equals("网购拼单")) {
						Intent intent1 = new Intent(SeePindan.this,
								xianshang2.class);
						intent1.putExtra("category1", category1);
						intent1.putExtra("category2", category2);
						intent1.putExtra("description", descriptionString);
						intent1.putExtra("imageUrl", imageUrlString);
						intent1.putExtra("addressclone", addressString);
						intent1.putExtra("cityclone", cityString);
						intent1.putExtra("title", titleString);
						intent1.putExtra("imgId", imageIdString);
						intent1.putExtra("linkurl", linkUrlString);
						intent1.putExtra("latClone", geoPoint.getLatitude());
						intent1.putExtra("lonClone", geoPoint.getLongitude());
						startActivity(intent1);
					} else {
						Intent intent2 = new Intent(SeePindan.this,
								xianxia1.class);
						intent2.putExtra("category1", category1);
						intent2.putExtra("category2", category2);
						intent2.putExtra("description", descriptionString);
						intent2.putExtra("imageUrl", imageUrlString);
						intent2.putExtra("addressclone", addressString);
						intent2.putExtra("cityclone", cityString);
						intent2.putExtra("latClone", geoPoint.getLatitude());
						intent2.putExtra("lonClone", geoPoint.getLongitude());
						Loger.i(category1 + category2 + descriptionString
								+ imageUrlString);
						startActivity(intent2);
					}
				}
			}
		});

		CollectOrderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 退出拼单后，回到首页
				orderOperation("collect");
				CollectOrderButton.setVisibility(View.GONE);
				quxiaocollectButton.setVisibility(View.VISIBLE);
				
			}
		});

		quxiaocollectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 退出拼单后，回到首页
				orderOperation("deCollect");
				CollectOrderButton.setVisibility(View.VISIBLE);
				quxiaocollectButton.setVisibility(View.GONE);
				
			}
		});

		/* 获取Intent中的Bundle对象 */
		Bundle bundle = this.getIntent().getExtras();

		/* 获取Bundle中的数据，注意类型和key */
		orderIdString = bundle.getString("orderId");
		orderCreatorString = bundle.getString("ordercreator");
		// orderStatusString = bundle.getString("orderstatus");
		// isCreator = bundle.getBoolean("isCreator");
		// isParticipants = bundle.getBoolean("isParticipants");

		nickNameTextView = (TextView) findViewById(R.id.nicknameS);
		descriptionTextView = (TextView) findViewById(R.id.descriptionS);
		addressTextView = (TextView) findViewById(R.id.addressS);
		endTimeTextView = (TextView) findViewById(R.id.endtimeS);
		pinTypeTextView = (TextView) findViewById(R.id.pintype);
		pinStateTextView = (TextView) findViewById(R.id.pinstate);
		partTextView = (TextView) findViewById(R.id.patipeople);
		cityTextView = (TextView) findViewById(R.id.cityS);
		genderImageView = (ImageView) findViewById(R.id.genderS);
		pic_seeImageView = (ImageView) findViewById(R.id.pic_see);
		portraitImageView = (ImageView) findViewById(R.id.headS);
		seeMoreButton = (Button) findViewById(R.id.comment_more);
		sv = (ScrollView) findViewById(R.id.scrollView1);
		sv.smoothScrollTo(0, 0);

		try {
			ViewConfiguration mconfig = ViewConfiguration.get(this);
			java.lang.reflect.Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(mconfig, false);
			}
		} catch (Exception ex) {
		}

		request();
		
		pic_seeImageView.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent(SeePindan.this, bigimage.class);
				Bundle bundle = new Bundle();
				bundle.putString("bigimageurl", BigUrlString);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		LinearLayout linear = (LinearLayout) findViewById(R.id.pl_linerlayout);
		linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				if (AVUser.getCurrentUser() == null)
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
				else
				{
					Intent intent = new Intent(SeePindan.this, fapinglun.class);
					Bundle bundle = new Bundle();

					bundle.putString("ordercreator", orderCreatorString);
					bundle.putString("orderId", orderIdString);
					// bundle.putString("orderstatus", orderStatusString);
					// bundle.putBoolean("isCreator", isCreator);
					// bundle.putBoolean("isParticipants", isParticipants);
					intent.putExtras(bundle);

					startActivity(intent);
					// finish();
				}
			}
		});

		LinearLayout linear1 = (LinearLayout) findViewById(R.id.chat_linerlayout);
		linear1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AVUser.getCurrentUser() == null)
					Toast.makeText(getApplicationContext(), "请先登录",
							Toast.LENGTH_SHORT).show();
				else if (isParticipants == false && isCreator == false)
					Toast.makeText(getApplicationContext(), "请先加入拼单",
							Toast.LENGTH_SHORT).show();
				else {
					String userId = AVUser.getCurrentUser().getObjectId();
					Session session = SessionManager.getInstance(userId);
					Intent intent = new Intent(SeePindan.this,
							GroupChatActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("groupId", groupidstring);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		
		shareLinearLayout = (LinearLayout) findViewById(R.id.qioushi_share_linerlayout);
		shareLinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final String[] arrayChoose = new String[]{"发送给微信朋友","分享到微信朋友圈","分享到微博"};
				Dialog dialog = new AlertDialog.Builder(SeePindan.this).setTitle("请选择操作：")
						.setIcon(R.drawable.icon_small).setItems(arrayChoose, 
								new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									WXWebpageObject webpage = new WXWebpageObject();
									webpage.webpageUrl = "http://pin.avosapps.com/orderDetail/" + orderIdString + "#userconsent#";
									Loger.i("拼单详情url:" + webpage.webpageUrl);
									WXMediaMessage msg = new WXMediaMessage(webpage);
									//此处图片是分享时网页的图片，title和description分别是题目和描述
									msg.title = "拼单";
									msg.description = descriptionString;
									Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_new);
									msg.thumbData = Util.bmpToByteArray(thumb, true);
									
									SendMessageToWX.Req req = new SendMessageToWX.Req();
									req.transaction = buildTransaction("webpage");
									req.message = msg;
									//发送给朋友
									req.scene = SendMessageToWX.Req.WXSceneSession;
									Weixinapi.sendReq(req);
								} else if (which == 1){
									WXWebpageObject webpage = new WXWebpageObject();
									webpage.webpageUrl = "http://pin.avosapps.com/orderDetail/" + orderIdString + "#userconsent#";
									Loger.i("拼单详情url:" + webpage.webpageUrl);
									WXMediaMessage msg = new WXMediaMessage(webpage);
									//此处图片是分享时网页的图片，title和description分别是题目和描述
									msg.title = "拼单";
									msg.description = descriptionString;
									Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_new);
									msg.thumbData = Util.bmpToByteArray(thumb, true);
									
									SendMessageToWX.Req req = new SendMessageToWX.Req();
									req.transaction = buildTransaction("webpage");
									req.message = msg;
									//朋友圈
									req.scene = SendMessageToWX.Req.WXSceneTimeline;
									Weixinapi.sendReq(req);
								} else {
									// 1. 初始化微博的分享消息
							        // 用户可以分享文本、图片、网页、音乐、视频中的一种
							        WeiboMessage weiboMessage = new WeiboMessage();
									WebpageObject mediaObject = new WebpageObject();
								    mediaObject.identify = Utility.generateGUID();
								    mediaObject.title = "拼单";
								    mediaObject.description = descriptionString;
								        
								    // 设置 Bitmap 类型的图片到视频对象里
								    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.icon_new);
								    mediaObject.setThumbImage(thumb);
								    mediaObject.actionUrl = "http://pin.avosapps.com/orderDetail/" + orderIdString + "#userconsent#";
								    weiboMessage.mediaObject = mediaObject;
								    //mediaObject.defaultText = "Webpage 默认文案";
								    // 2.初始化从第三方到微博的消息请求
							        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
							        // 用transaction唯一标识一个请求
							        request.transaction = String.valueOf(System.currentTimeMillis());
							        request.message = weiboMessage;
							        
							        // 3. 发送请求消息到微博，唤起微博分享界面
							        mWeiboShareAPI.sendRequest(SeePindan.this, request);
								}
							}
						}).setNegativeButton("", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						}).create();
				dialog.show();
				
			}
		});

		seeMoreButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SeePindan.this, SeeMoreComment.class);
				Bundle bundle = new Bundle();

				bundle.putString("ordercreator", orderCreatorString);
				bundle.putString("orderId", orderIdString);
				// bundle.putString("orderstatus", orderStatusString);
				// bundle.putBoolean("isCreator", isCreator);
				// bundle.putBoolean("isParticipants", isParticipants);
				intent.putExtras(bundle);

				startActivity(intent);
				finish();
			}
		});

		comment_listView = (ListViewForScrollView) findViewById(R.id.list_comment);

		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, mData,
				R.layout.activity_comment, new String[] { "creatorname",
						"commentcontent", "headcomment" }, new int[] {
						R.id.nickname_comment, R.id.comment_content,
						R.id.head_comment });

		simpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		comment_listView.setAdapter(simpleAdapter);

		comment_listView.setOnItemClickListener(new OnItemClickListenerImpl());
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				simpleAdapter.notifyDataSetChanged();
			}
		}
	};

	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String userIdString;
			if (AVUser.getCurrentUser() == null)
				Toast.makeText(getApplicationContext(), "请先登录",
						Toast.LENGTH_SHORT).show();
			else {
				userIdString = AVUser.getCurrentUser().getObjectId();
				// 得到当前用户的id?以及判断是否为拼单发起者?
				System.out.println("得到的ordercreator2:" + orderCreatorString);
				if (mData.get(position).get("creator").toString().trim()
						.equalsIgnoreCase(userIdString)
						|| orderCreatorString.equals(userIdString)) {
					dialog6(mData.get(position).get("creatorname").toString(),
							mData.get(position).get("creator").toString(),
							mData.get(position).get("commentId").toString(),
							mData.get(position).get("commentcontent").toString());
				} else
					dialog7(mData.get(position).get("creatorname").toString(),
							mData.get(position).get("creator").toString(),
							mData.get(position).get("commentcontent").toString());
			}
		}
	}

	private void request() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderIdString);
		AVCloud.callFunctionInBackground("getOrderDetail", parameters,
				new FunctionCallback<Map<String, Object>>() {
					@SuppressWarnings("unchecked")
					public void done(Map<String, Object> object, AVException e) {
						if (e == null) {
							Loger.i("查看详情成功:" + object);
							groupidstring = object.get("groupId").toString();
							nickNameString = object.get("nickName").toString();
							descriptionString = object.get("description")
									.toString();
							addressString = object.get("address").toString();
							endTimeString = TimeStamp2Date(object
									.get("endTime").toString());
							pinTypeString = object.get("orderType").toString();
							pinStateString = object.get("status").toString();
							cityString = object.get("city").toString();
							genderString = object.get("gender").toString();
							orderStatusString = object.get("status").toString();

							category1 = object.get("category1").toString();
							category2 = object.get("category2").toString();
							
							geoPoint = (AVGeoPoint) object.get("targetLocation");
							

							if (object.get("title") != null) {
								titleString = object.get("title").toString();
							}

							if (object.get("url") != null) {
								linkUrlString = object.get("url").toString();
							}

							isCreator = (Boolean) object.get("isCreator");
							isParticipants = (Boolean) object.get("isParticipants");
							whethercollected = (Boolean) object.get("whetherCollect");
							whetheronline = (Boolean) object.get("whetherOnline");
							
							seeurl = (Button)findViewById(R.id.see_url);
							if(whetheronline==false)
								seeurl.setVisibility(View.GONE);
							else if (linkUrlString == null) {
								seeurl.setVisibility(View.GONE);
							}else
								seeurl.setOnClickListener(new View.OnClickListener() 
								{
									@Override
									public void onClick(View v) 
									{
										Uri uri=Uri.parse(linkUrlString);
										Intent intent=new Intent(Intent.ACTION_VIEW,uri);
										startActivity(intent);
									}
								});

							judgeOrderOperate();

							if (((List<String>) object.get("images")).size() == 0) {
								pic_seeImageView.setVisibility(View.GONE);
							} else {
								imageIdString = ((List<String>) object
										.get("images")).get(0).toString();
								getPicUrl(imageIdString, 1);
							}

							if (object.get("avatar") == null) {
								portraitImageView
										.setImageResource(R.drawable.mine);
							} else {
								portraitIdString = object.get("avatar")
										.toString();
								getPicUrl(portraitIdString, 2);
							}
							getpartipeople(orderIdString);

							nickNameTextView.setText(nickNameString);
							descriptionTextView.setText(descriptionString);
							addressTextView.setText(addressString);
							endTimeTextView.setText(endTimeString);
							pinTypeTextView.setText(pinTypeString);
							pinStateTextView.setText(pinStateString);
							cityTextView.setText(cityString);
//							partTextView.setText(partipeoplestring);
							if (getGender(genderString)) {
								genderImageView
										.setImageResource(R.drawable.male);
							} else {
								genderImageView
										.setImageResource(R.drawable.female);
							}
							progressDialogDismiss();
						} else {
							progressDialogDismiss();
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
	
	public void getpartipeople(String orderId) 
	{
		AVQuery<AVObject> query = new AVQuery<AVObject>("Orders");
		query.whereEqualTo("objectId",orderId);
		query.findInBackground(new FindCallback<AVObject>() 
		{
			public void done(List<AVObject> avObjects, AVException e) 
			{
				if (e == null) 
				{
					Log.d("成功", "查询到" + avObjects.size() + "参与拼单的人的id");
					partTextView.setText(null);
					if(((ArrayList<String>) avObjects.get(0).get("participants")).size()==0)
						partTextView.setText("无");
					for(int i=0;i<((ArrayList<String>) avObjects.get(0).get("participants")).size();i++)
					{
						String partid=((ArrayList<String>) avObjects.get(0).get("participants")).get(i);
						AVQuery<AVObject> query = new AVQuery<AVObject>("UserProfile");
						query.whereEqualTo("userId",partid);
						query.findInBackground(new FindCallback<AVObject>() 
						{
							public void done(List<AVObject> avObjects, AVException e) 
							{
								if (e == null) 
								{
									Log.d("成功", "查询到" + avObjects.size() + "参与拼单的人的昵称");
									partipeoplestring=(String)avObjects.get(0).get("nickName");
									partTextView.setText(partTextView.getText()+partipeoplestring+"   ");
								} 
								else 
								{
									Log.d("失败", "查询错误: " + e.getMessage());
								}
							}
						});
					}
				} 
				else 
				{
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}

	public void getPicUrl(String imageIdString, final int imageType) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");

					String urlString = (String) avObjects.get(0).get(
							"url");
					if (imageType == 1) 
					{
						urlString = (String) AVFile.withAVObject(avObjects.get(0)).getThumbnailUrl(false, 600, 300);
						BigUrlString = (String) avObjects.get(0).get("url");
						imageUrlString = urlString;
						Loger.l();
						picSeeBitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (pic_seeImageView != null
												&& bitmap != null) {
											Loger.l();
											pic_seeImageView
													.setImageBitmap(bitmap);
										}
									}
								});
						if (picSeeBitmap != null) {
							Loger.l();
							pic_seeImageView.setImageBitmap(picSeeBitmap);
						}
						// new DownloadImageTask(
						// (ImageView) findViewById(R.id.pic_see))
						// .execute(urlString);
					} else {
						portraitBitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (portraitImageView != null
												&& bitmap != null) {
											Loger.l();
											portraitImageView
													.setImageBitmap(bitmap);
										}
									}
								});
						if (portraitBitmap != null) {
							Loger.l();
							portraitImageView.setImageBitmap(portraitBitmap);
						}
						// new DownloadImageTask(
						// (ImageView) findViewById(R.id.headS))
						// .execute(urlString);
					}
					System.out.println(urlString);
				} else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}

	public void getPicUrlComment(String imageIdString,
			final Map<String, Object> map1) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					final String urlString = (String) avObjects.get(0).get(
							"url");

					Bitmap bitmap = mImageDownLoader.downloadImage(urlString,
							new onImageLoaderListener() {
								@Override
								public void onImageLoader(Bitmap bitmap,
										String url) {
									Loger.l();
									if (bitmap != null) {
										Loger.l();
										map1.put("headcomment", bitmap);
										simpleAdapter.notifyDataSetChanged();
									}
								}
							});
					if (bitmap != null) {
						Loger.l();
						map1.put("headcomment", bitmap);
						simpleAdapter.notifyDataSetChanged();
					}

					System.out.println(urlString);
				} else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}

	public void requestComment() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderIdString);// 暂时用这个orderId，应该是orderIdString
		params.put("page", page);
		mData.clear();

		Loger.i("request comment~~~~~~~~~~~~~~~~~~~~~~~");
		AVCloud.callFunctionInBackground("getComment", params,
				new FunctionCallback<List<Map<String, Object>>>() {
					public void done(List<Map<String, Object>> object,
							AVException e) {
						if (e == null) {
							//object.size() = 2
							if (object.size() < 2) {
								seeMoreButton.setVisibility(View.INVISIBLE);
							} else {
								seeMoreButton.setVisibility(View.VISIBLE);
							}
							Loger.i("加载评论成功 : " + object);
							if(!object.isEmpty()){
								if(!object.get(0).isEmpty()){
									mData.add(convertResult(object.get(0)));
								}
								if(object.size()> 1){
									mData.add(convertResult(object.get(1)));
								}
								simpleAdapter.notifyDataSetChanged();
							}
							
//							for (Map<String, Object> map : object) {
//								mData.add(convertResult(map));
//								simpleAdapter.notifyDataSetChanged();
//							}
							sv.smoothScrollTo(0, 0);
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	public void rmComment(final String comment_id) {
		Loger.l();
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("commentId", comment_id); // 接收评论的id

		AVCloud.callFunctionInBackground("removeComment", params2,
				new FunctionCallback<String>() {
					public void done(String object, AVException e) {
						if (e == null) {
							Loger.i("删除评论成功 : " + object);

						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	public Map<String, Object> convertResult(Map<String, Object> map) {
		creatorNameString = map.get("creatorName").toString();

		creator = map.get("creator").toString();
		commentId = map.get("commentId").toString();
		if (map.size() == 8) {
			huifurenid = map.get("to").toString();
			toNameString = map.get("toName").toString();
			commentContentString = "回复" + toNameString + ":"
					+ map.get("content").toString();
		} else
			commentContentString = map.get("content").toString();

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("creatorname", creatorNameString);
		map1.put("commentcontent", commentContentString);

		if (map.get("creatorAvatar") == null) {
			map1.put("headcomment", R.drawable.mine);
		} else {
			commentPortraitIdString = map.get("creatorAvatar").toString();
			getPicUrlComment(commentPortraitIdString, map1);
		}

		map1.put("creator", creator);
		map1.put("commentId", commentId);

		return map1;
	}

	// 判断性别
	public boolean getGender(String genderString1) {
		if (genderString1.equals("M")) {
			return true;
		}
		return false;
	}

	// 将unix时间戳转换为普通日期
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	public void judgeOrderOperate() {
		Loger.i("status:" + orderStatusString);
		Loger.i("isCreator:" + isCreator);
		Loger.i("isParticipants:" + isParticipants);
		//修改拼单
		if(!isCreator)
			modifyOrderButton.setVisibility(View.GONE);
		//收藏拼单
		if (whethercollected)
			CollectOrderButton.setVisibility(View.GONE);
		else
			quxiaocollectButton.setVisibility(View.GONE);
		//恢复拼单
		if(!orderStatusString.equals("人数已满")||!isCreator)
			resumeOrderButton.setVisibility(View.GONE);
		
		if (orderStatusString.equals("拼单中")) {
			if (isCreator) {// 是发起人
				Loger.i("发起人");
				partcipateOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.GONE);
			} else if (isParticipants) {// 已加入
				Loger.i("已加入");
				partcipateOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				cancelOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
			} else {// 未加入
				Loger.i("未加入");
				quitOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				cancelOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
			}
		} else if (orderStatusString.equals("人数已满")) {
			if (isCreator) {// 是发起人
				partcipateOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
			} else if (isParticipants) {// 已加入
				partcipateOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				cancelOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
			} else {// 未加入
				partcipateOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				cancelOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.GONE);
//				popupwindow_LinearLayout.setVisibility(View.GONE);
			}
		} else if (orderStatusString.equals("拼单成功")) {
			partcipateOrderButton.setVisibility(View.GONE);
			finishOrderButton.setVisibility(View.GONE);
			cancelOrderButton.setVisibility(View.GONE);
			pauseOrderButton.setVisibility(View.GONE);
			quitOrderButton.setVisibility(View.GONE);
//			popupwindow_LinearLayout.setVisibility(View.GONE);
		} else {
			if (isCreator) {// 是发起人
				partcipateOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
			} else {
				partcipateOrderButton.setVisibility(View.GONE);
				finishOrderButton.setVisibility(View.GONE);
				cancelOrderButton.setVisibility(View.GONE);
				pauseOrderButton.setVisibility(View.GONE);
				quitOrderButton.setVisibility(View.GONE);
//				popupwindow_LinearLayout.setVisibility(View.GONE);
			}
		}
	}

	private void orderOperation(String orderOperate) {
		Map<String, Object> params4 = new HashMap<String, Object>();
		params4.put("orderId", orderIdString);
		params4.put("operation", orderOperate);
		Loger.i(params4 + "");

		AVCloud.callFunctionInBackground("userOrderOperation", params4,
				new FunctionCallback<String>() {
					public void done(String object, AVException e) {
						if (e == null) {
							Toast.makeText(getApplicationContext(), object,
									Toast.LENGTH_SHORT).show();
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	private void dialog6(final String creator_name, final String creator_id,final String comment_id,final String commentcontent) 
	{
		new AlertDialog.Builder(this).setTitle("请选择操作")
				.setIcon(android.R.drawable.ic_media_play)
				.setItems(arg0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							dialog.dismiss();
							Intent intent = new Intent(SeePindan.this,
									huifu.class);
							Bundle bundle = new Bundle();
							bundle.putString("creatorname", creator_name);
							bundle.putString("commentcreatorid", creator_id);
							bundle.putString("orderId", orderIdString);
							bundle.putString("ordercreator", orderCreatorString);
							bundle.putString("commentcontent", commentcontent);
							// bundle.putString("orderstatus",
							// orderStatusString);
							// bundle.putBoolean("isCreator", isCreator);
							// bundle.putBoolean("isParticipants",
							// isParticipants);
							intent.putExtras(bundle);

							startActivity(intent);
							// finish();
							chooseItem = -1;
							break;
						case 1:
							dialog.dismiss();
							// 接收评论的id
							Loger.l();
							rmComment(comment_id);
							// 重新刷新
							Intent intent1 = new Intent(SeePindan.this,
									SeePindan.class);
							Bundle bundle1 = new Bundle();

							bundle1.putString("creatorname", creator_name);
							bundle1.putString("creator", creator_id);
							bundle1.putString("ordercreator",
									orderCreatorString);
							bundle1.putString("orderId", orderIdString);
							// bundle1.putString("orderstatus",
							// orderStatusString);
							// bundle1.putBoolean("isCreator", isCreator);
							// bundle1.putBoolean("isParticipants",
							// isParticipants);
							intent1.putExtras(bundle1);

							startActivity(intent1);
							finish();
							chooseItem = -1;
							break;
						default:
							break;

						}
					}
				}).show();

	}

	private void dialog7(final String creator_name, final String creator_id,final String commentcontent) 
	{
		new AlertDialog.Builder(this).setTitle("请选择操作")
				.setIcon(android.R.drawable.ic_media_play)
				.setItems(arg1, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							dialog.dismiss();
							Intent intent = new Intent(SeePindan.this,
									huifu.class);
							Bundle bundle = new Bundle();

							bundle.putString("creatorname", creator_name);
							bundle.putString("commentcreatorid", creator_id);
							bundle.putString("orderId", orderIdString);
							bundle.putString("ordercreator", orderCreatorString);
							bundle.putString("commentcontent", commentcontent);
							// bundle.putString("orderstatus",
							// orderStatusString);
							// bundle.putBoolean("isCreator", isCreator);
							// bundle.putBoolean("isParticipants",
							// isParticipants);
							intent.putExtras(bundle);

							startActivity(intent);
							// finish();
							chooseItem = -1;
							break;

						default:
							break;

						}
					}
				}).show();

	}
	
	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(SeePindan.this, "",
						SeePindan.this.getResources().getText(
						R.string.dialog_text_wait), true, true);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			if(requestCode == modifyOnline_Result){
				String descriptionFromModifyOnline = data.getExtras().getString("description");
				descriptionString = descriptionFromModifyOnline;
				descriptionTextView.setText(descriptionFromModifyOnline);
			}
			if(requestCode == modifyOffline_Result){
				String descriptionFromModifyOffline = data.getExtras().getString("description");
				descriptionString = descriptionFromModifyOffline;
				descriptionTextView.setText(descriptionFromModifyOffline);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Loger.i("onResume!!!!!!!!!!!!!!!!!!!!!");
		requestComment();
		super.onResume();
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
}
