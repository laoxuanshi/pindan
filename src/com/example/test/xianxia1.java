package com.example.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.avos.avoscloud.*;
import com.baidu.location.BDLocation;
import com.example.test.DemoGroupMessageReceiver.JoinedListener;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

public class xianxia1 extends Activity implements View.OnTouchListener {
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private Spinner mySpinner1, mySpinner2;
	private ArrayAdapter<String> adapter1, adapter2;
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private Button backButton;
	private Button sendButton;

	private EditText etendTime1;

	public final static int SearchAddress_Result = 3;
	public static final String IMAGE_UNSPECIFIED = "image/*";

	public static String userIdString = null;
	public static String imgId = null;
	public static String description1 = null;
	public static String city1 = null;
	public static String address1 = null;
	public static String category1, category2 = null;
	public static long unix;
	public static ArrayList<String> imgarray = new ArrayList<String>();
	public ByteArrayOutputStream stream = null;
	private Bitmap bitmapFromCloneBitmap;
	private ImageDownLoader mImageDownLoader;
	
    private byte[] mContent;   
    private Bitmap myBitmap;   

	private static double lat;// 纬度
	private static double lon;// 经度
	private static AVGeoPoint geoPoint;
	private static double latSource;
	private static double lonSource;
	private static double latClone;
	private static double lonClone;
	
	double latFromSearchAddress = 0;
	double lonFromSearchAddress = 0;

	EditText description;
	private static EditText city;
	private static TextView address;
	static String cityString;
	static String addressString;
	String addressFromSearchAddressString;
	String cityFromSearchAddressString;
	
	String category1CloneString;
	String category2CloneString;
	String descriptionCloneString;
	String imageUrlCloneString;
	String addressCloneString;
	String cityCloneString;
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Loger.i("----------------create----------------");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xianxia1);
		progressDialogShow();
		mImageDownLoader = new ImageDownLoader(xianxia1.this);

		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
//		actionBar.setTitle("                     线下拼单");
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
//				ActionBar.DISPLAY_HOME_AS_UP);
		
		addressCloneString = this.getIntent().getStringExtra("addressclone");
		cityCloneString = this.getIntent().getStringExtra("cityclone");
		
		address = (TextView) findViewById(R.id.gouwuaddress);
		city = (EditText) findViewById(R.id.suozaiciti1);
		
		latClone = this.getIntent().getDoubleExtra("latClone", 0);
		lonClone = this.getIntent().getDoubleExtra("lonClone", 0);
		
		MyApplication.requestLocation();
		MyApplication.setPinLocationListener(new PinLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				latSource = location.getLatitude();
				lonSource = location.getLongitude();
				if(latClone != 0 && lonClone != 0){
					geoPoint =  new AVGeoPoint(latClone, lonClone);
					Loger.i("geoPoint:" + geoPoint.getLatitude() + geoPoint.getLongitude());
				}else {
					geoPoint = new AVGeoPoint(latSource, lonSource);
					Loger.l();
				}
				
				cityString = location.getCity();
				addressString = location.getAddrStr();
				
				if (addressCloneString != null) {
					address.setText(addressCloneString);
				}else {
					Loger.i("address.setText(addressString)~~~~~~~~~~~~~~~");
					address.setText(addressString);
				}
				
				if (cityCloneString != null) {
					city.setText(cityCloneString);
				}else {
					city.setText(cityString.substring(0, cityString.length() - 1));
				}
				progressDialogDismiss();
			}
		});
		
		list1.add("服饰");
		list1.add("鞋靴");
		list1.add("家电");
		list1.add("数码");
		list1.add("图书");
		list1.add("食品");
		list1.add("汽车用品");
		list1.add("运动户外");
		list1.add("电脑办公");
		list1.add("家居家装");
		list1.add("个护化妆");
		list1.add("其他商品");
		
		description = (EditText) findViewById(R.id.description_xia);
		mySpinner1 = (Spinner) findViewById(R.id.s_sort1_xia);
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner1.setAdapter(adapter1);
		
		mySpinner2 = (Spinner) findViewById(R.id.s_sort2_xia);
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner2.setAdapter(adapter2);
		

		address.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					address.setBackgroundColor(Color.GRAY);
                }else if(event.getAction() == MotionEvent.ACTION_UP){    
                	address.setBackgroundColor(Color.WHITE);    
                }   
				return false;
			}
		});
		
		address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(xianxia1.this, SearchAddress.class);
				startActivityForResult(intent, SearchAddress_Result);
			}
		});
		

		userIdString = AVUser.getCurrentUser().getObjectId();

		mySpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						switch (arg2) {
						case 0:
							category1 = "0";
							list2.clear();
							list2.add("男装");
							list2.add("女装");
							list2.add("服饰配件");
							list2.add("其他");

							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "0";
												break;
											case 1:
												category2 = "1";
												break;
											case 2:
												category2 = "2";
												break;
											default:
												category2 = "3";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});

							break;
						case 1:
							category1 = "1";
							list2.clear();
							list2.add("男鞋");
							list2.add("女鞋");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "10";
												break;
											case 1:
												category2 = "11";
												break;
											default:
												category2 = "12";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 2:
							category1 = "2";
							list2.clear();
							list2.add("大家电");
							list2.add("生活电器");
							list2.add("厨房电器");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "20";
												break;
											case 1:
												category2 = "21";
												break;
											case 2:
												category2 = "22";
												break;
											default:
												category2 = "23";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 3:
							category1 = "3";
							list2.clear();
							list2.add("手机");
							list2.add("手机配件");
							list2.add("摄影");
							list2.add("数码配件");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "30";
												break;
											case 1:
												category2 = "31";
												break;
											case 2:
												category2 = "32";
												break;
											case 3:
												category2 = "33";
												break;
											default:
												category2 = "34";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 4:
							category1 = "4";
							list2.clear();
							list2.add("电子书");
							list2.add("人文");
							list2.add("科学");
							list2.add("教育");
							list2.add("少儿");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "40";
												break;
											case 1:
												category2 = "41";
												break;
											case 2:
												category2 = "42";
												break;
											case 3:
												category2 = "43";
												break;
											case 4:
												category2 = "44";
												break;
											default:
												category2 = "45";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 5:
							category1 = "5";
							list2.clear();
							list2.add("进口食品");
							list2.add("休闲食品");
							list2.add("地方特产");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "50";
												break;
											case 1:
												category2 = "51";
												break;
											case 2:
												category2 = "52";
												break;
											default:
												category2 = "53";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 6:
							category1 = "6";
							list2.clear();
							list2.add("维修保养");
							list2.add("汽车装饰");
							list2.add("美容清洗");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "60";
												break;
											case 1:
												category2 = "61";
												break;
											case 2:
												category2 = "62";
												break;
											default:
												category2 = "63";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 7:
							category1 = "7";
							list2.clear();
							list2.add("运动服鞋");
							list2.add("户外装备");
							list2.add("体育用品");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "70";
												break;
											case 1:
												category2 = "71";
												break;
											case 2:
												category2 = "72";
												break;
											default:
												category2 = "73";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 8:
							category1 = "8";
							list2.clear();
							list2.add("电脑整机");
							list2.add("电脑设备");
							list2.add("办公用品");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "80";
												break;
											case 1:
												category2 = "81";
												break;
											case 2:
												category2 = "82";
												break;
											default:
												category2 = "83";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 9:
							category1 = "9";
							list2.clear();
							list2.add("厨具");
							list2.add("家居");
							list2.add("灯具");
							list2.add("家装软饰");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "90";
												break;
											case 1:
												category2 = "91";
												break;
											case 2:
												category2 = "92";
												break;
											case 3:
												category2 = "93";
												break;
											default:
												category2 = "94";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;
						case 10:
							category1 = "10";
							list2.clear();
							list2.add("面部护肤");
							list2.add("身体护肤");
							list2.add("洗头护发");
							list2.add("口腔护理");
							list2.add("其他");
							mySpinner2
									.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											switch (arg2) {
											case 0:
												category2 = "100";
												break;
											case 1:
												category2 = "101";
												break;
											case 2:
												category2 = "102";
												break;
											case 3:
												category2 = "103";
												break;
											default:
												category2 = "104";
												break;
											}
											// 通知Spinner2数据已更改
											adapter2.notifyDataSetChanged();
											// mySpinner5.setSelection(arg2);
											arg0.setVisibility(View.VISIBLE);
										}

										public void onNothingSelected(
												AdapterView<?> arg0) {
											arg0.setVisibility(View.VISIBLE);
										}
									});
							break;

						default:
							list2.clear();
							list2.add("其他");
							category1 = "11";
							category2 = "110";
							break;
						}
						// 通知Spinner2数据已更改
						adapter2.notifyDataSetChanged();
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});
		
		category1CloneString = this.getIntent().getStringExtra("category1");
		category2CloneString = this.getIntent().getStringExtra("category2");
		descriptionCloneString = this.getIntent().getStringExtra("description");
		imageUrlCloneString = this.getIntent().getStringExtra("imageUrl");
		
		if (descriptionCloneString != null) {
			Loger.i(descriptionCloneString);
			description.setText(descriptionCloneString);
		}

		if (category1CloneString != null && category2CloneString != null) 
		{
			Loger.i(category1CloneString + category2CloneString);
			category1 = category1CloneString;
			category2 = category2CloneString;
			int categ1 = Integer.parseInt(category1CloneString);
			int categ2 = Integer.parseInt(category2CloneString);
			Loger.i(category1CloneString + category2CloneString + categ1 + categ2);
			mySpinner1.setSelection(categ1);
			adapter1.notifyDataSetChanged();
			mySpinner2.setSelection(categ2%10);
			adapter2.notifyDataSetChanged();
		}
		
		
		if(imageUrlCloneString != null)
		{
			//TODO 图片转换
			bitmapFromCloneBitmap = mImageDownLoader.downloadImage(
					imageUrlCloneString, new onImageLoaderListener() {
						@Override
						public void onImageLoader(Bitmap bitmap,
								String url) {
							Loger.l();
							if (bitmap != null) {
								if(Bimp.bmp.size()==0)
									Bimp.bmp.add(bitmap);
								stream=new ByteArrayOutputStream();
								bitmapFromCloneBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
							}
						}
					});
			if (bitmapFromCloneBitmap != null) {
				if(Bimp.bmp.size()==0)
					Bimp.bmp.add(bitmapFromCloneBitmap);
				stream=new ByteArrayOutputStream();
				bitmapFromCloneBitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
			}
		}
		else 
			Bimp.bmp.clear();
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update1();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				if (arg2 == Bimp.bmp.size())
				{
					new PopupWindows(xianxia1.this, noScrollgridview);
				} else
				{
					Intent intent = new Intent(xianxia1.this,
							PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		

		// 截止时间的事
		etendTime1 = (EditText) this.findViewById(R.id.edittextjj2);
		etendTime1.setOnTouchListener(this);
		
		backButton = (Button) this.findViewById(R.id.xianxia1_back);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 返回按钮
				AVQuery<AVObject> query = new AVQuery<AVObject>("AVOSRealtimeGroup");
				try 
				{
					query.get(DemoGroupMessageReceiver.groupid);
					query.deleteAll();
					Loger.i("成功删除");
				} 
				catch (AVException e) 
				{
					e.printStackTrace();
				}
				finish();
			}
		});
		
		sendButton = (Button) this.findViewById(R.id.send_button_xianxia);
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 提交按钮
				description1 = description.getText().toString();

				address1 = address.getText().toString();

				city1 = city.getText().toString();
				
				String timeString = etendTime1.getText().toString();
				if(description1.length() == 0||address1.length() == 0||city1.length() == 0||
						timeString.length() == 0){
					Toast.makeText(getApplicationContext(), "请完善拼单信息", 
							Toast.LENGTH_SHORT).show();
				}
				else 
				{
					progressDialogShow();
					stream=new ByteArrayOutputStream();
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < Bimp.drr.size(); i++)
					{
						String Str = Bimp.drr.get(i).substring(
								Bimp.drr.get(i).lastIndexOf("/") + 1,
								Bimp.drr.get(i).lastIndexOf("."));
						list.add(FileUtils.SDPATH + Str + ".JPEG");
					}
					stream=new ByteArrayOutputStream();
					if(Bimp.bmp.isEmpty())
					{
						Loger.i("空");
						stream=null;
					}
					else
					{
						Loger.i("fei空");
						Bimp.bmp.get(0).compress(Bitmap.CompressFormat.JPEG, 100, stream);
					}
					FileUtils.deleteDir();
					if(stream!=null)
					{
						byte[] dd = stream.toByteArray();
						final AVFile imageFile = new AVFile(userIdString + ".jpg",dd);
						imageFile.saveInBackground(new SaveCallback() {
						@Override
							public void done(AVException e) {
								if (e == null) {
									imgId = imageFile.getObjectId();
									Loger.i("上传后返回的imgId:" + imgId);
									imgarray.clear();
									imgarray.add(imgId);
									
									DemoGroupMessageReceiver.setJoinedListener(new JoinedListener() {
										@Override
										public void onJoined(String groupId) {
											Loger.i("建线下组成功" + groupId + " Joined");
											shengchengorder(userIdString, description1, city1, address1,
													category1, category2, unix, imgarray);
										}
									});
									
									Session session = SessionManager.getInstance(AVUser.getCurrentUser().getObjectId());
									Group group = session.getGroup();
									group.join();
									
								}
								setProgressBarIndeterminateVisibility(false);
							}
						}, new ProgressCallback() {
							@Override
							public void done(Integer percentDone) {
								LogUtil.log.d("uploading: " + percentDone);
							}
						});
					}
					else
					{
						imgarray.clear();
						DemoGroupMessageReceiver.setJoinedListener(new JoinedListener() {
							@Override
							public void onJoined(String groupId) {
								Loger.i("建线下组成功" + groupId + " Joined");
								shengchengorder(userIdString, description1, city1, address1,
										category1, category2, unix, imgarray);
							}
						});
						
						Session session = SessionManager.getInstance(AVUser.getCurrentUser().getObjectId());
						Group group = session.getGroup();
						group.join();
						
					}
				}
				
			}
		});
		
	
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.date_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view
					.findViewById(R.id.date_picker);
			final TimePicker timePicker = (android.widget.TimePicker) view
					.findViewById(R.id.time_picker);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH)+1, null);
			
			timePicker.setIs24HourView(true);
			timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));

			if (v.getId() == R.id.edittextjj2) {
				int inType = etendTime1.getInputType();
				etendTime1.setInputType(InputType.TYPE_NULL);
				etendTime1.onTouchEvent(event);
				etendTime1.setInputType(inType);
				etendTime1.setSelection(etendTime1.getText().length());

				builder.setTitle("选择截止时间");// 截止时间不能比当前时间早
				builder.setPositiveButton("确  定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d-%02d-%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
								sb.append("  ");
								sb.append(timePicker.getCurrentHour())
										.append(":")
										.append(timePicker.getCurrentMinute());
//								etendTime1.setText(sb);

								String ah = sb.toString();
								SimpleDateFormat df = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm");
								
								try {
									unix = df.parse(ah).getTime() / 1000;
								} catch (java.text.ParseException e) {
									e.printStackTrace();
								}
								
								long unixNow = System.currentTimeMillis()/1000;
								Loger.i("用户选择的unix时间戳" + unix);
								Loger.i("当前时间的unix时间戳" + unixNow);
								
								if(unix > unixNow){
									etendTime1.setText(sb);
									dialog.cancel();
								}else {
									etendTime1.setText(null);
									Toast.makeText(getApplicationContext(), "请输入未来时间", 
											Toast.LENGTH_SHORT).show();
								}
								
//								dialog.cancel();
							}
						});

			}
			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	private void shengchengorder(final String user_id,
			final String descript_text, final String ct,
			final String shouhuo_address, final String cat1, final String cat2,
			final long unix, final ArrayList<String> img_array) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("creator", user_id);
		parameters.put("description", descript_text);
		parameters.put("city", ct);
		parameters.put("address", shouhuo_address);
		Loger.i("提交前geopoint：" + geoPoint);
		parameters.put("targetLocation", geoPoint);
		parameters.put("sourceLatitude", latSource);
		parameters.put("sourceLongitude", lonSource);
		parameters.put("category1", cat1);
		parameters.put("category2", cat2);
		parameters.put("images", img_array);
		parameters.put("endTime", unix);
		parameters.put("whetherOnline", "0");
		parameters.put("groupId", DemoGroupMessageReceiver.groupid);

		Loger.i("parameters:" + parameters);
		Loger.i("descript_text: " + descript_text);
		Loger.i("ct: " + ct);
		Loger.i("shouhuo_address: " + shouhuo_address);
		Loger.i("cat1: " + cat1);
		Loger.i("cat2: " + cat2);
		Loger.i("unix: " + unix);
		Loger.i("img_array: " + img_array);
		Loger.i("groupid: " + DemoGroupMessageReceiver.groupid);

//		progressDialogShow();
		AVCloud.callFunctionInBackground("createOrder", parameters,
				new FunctionCallback<String>() {
					public void done(String object, AVException e) {
						if (e == null) {
							Loger.i("添加成功: " + object);
							progressDialogDismiss();
							Intent intent1 = new Intent(xianxia1.this, MainActivity.class);
							Bundle bundle1 = new Bundle();
							bundle1.putInt("fagment_index", 0);
							intent1.putExtras(bundle1);
							startActivity(intent1);
							finish();
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
//							Toast.makeText(getApplicationContext(),
//									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
	
	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(xianxia1.this, "",
						xianxia1.this.getResources().getText(
						R.string.dialog_text_wait), true, true);
	}
	
	@Override
	protected void onStart() {
		Loger.i("------------start--------------------------------------");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Loger.i("----------stop------------------------------");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Loger.i("-----------destroy----------------------------");
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		Loger.i("-------------pause----------------------");
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Loger.i("----------------resume----------------");
		super.onResume();
	}
	
	/** 
     * Activity被系统杀死时被调用. 
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死. 
     * 另外,当跳转到其他Activity或者按Home键回到主屏时该方法也会被调用,系统是为了保存当前View组件的状态. 
     * 在onPause之前被调用. 
     */  
    @Override  
    protected void onSaveInstanceState(Bundle outState) {  
        Loger.i("onSaveInstanceState called.--------------------");  
        super.onSaveInstanceState(outState);  
    }  
      
    /** 
     * Activity被系统杀死后再重建时被调用. 
     * 例如:屏幕方向改变时,Activity被销毁再重建;当前Activity处于后台,系统资源紧张将其杀死,用户又启动该Activity. 
     * 这两种情况下onRestoreInstanceState都会被调用,在onStart之后. 
     */  
    @Override  
    protected void onRestoreInstanceState(Bundle savedInstanceState) {  
        Loger.i("onRestoreInstanceState called----------------------");  
        super.onRestoreInstanceState(savedInstanceState);  
    }  
    
 
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter
	{
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape()
		{
			return shape;
		}

		public void setShape(boolean shape)
		{
			this.shape = shape;
		}

		public GridAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);
		}

		public void update1()
		{
			loading1();
		}

		public int getCount()
		{
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0)
		{

			return null;
		}

		public long getItemId(int arg0)
		{

			return 0;
		}

		public void setSelectedPosition(int position)
		{
			selectedPosition = position;
		}

		public int getSelectedPosition()
		{
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent)
		{
			//final int coord = position;
			ViewHolder holder = null;
			
			System.out.println("测试下表="+position);
			if (convertView == null)
			{

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.image.setVisibility(View.VISIBLE);

			if (position == Bimp.bmp.size())
			{
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				
			} else
			{
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}
			
			if (position == 9)
			{
				holder.image.setVisibility(View.GONE);
			}

			return convertView;
		}

		public class ViewHolder
		{
			public ImageView image;
		}

		Handler handler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		
		public void loading1()
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					while (true)
					{
						if (Bimp.max == Bimp.drr.size())
						{
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else
						{
							try
							{
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e)
							{

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s)
	{
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++)
		{
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart()
	{
		adapter.update1();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow
	{

		public PopupWindows(Context mContext, View parent)
		{
			
			 super(mContext);

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					photo();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					Intent intent = new Intent(xianxia1.this,
							TestPicActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void onConfigurationChanged(Configuration config) 
	{ 
	    super.onConfigurationChanged(config); 
	} 
	
	public void photo()
	{
		String status=Environment.getExternalStorageState(); 
		if(status.equals(Environment.MEDIA_MOUNTED)) 
		{
			File dir=new File(Environment.getExternalStorageDirectory() + "/myimage/"); 
			if(!dir.exists())dir.mkdirs(); 
			
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(dir, String.valueOf(System.currentTimeMillis())
					+ ".jpg");
			path = file.getPath();
			Uri imageUri = Uri.fromFile(file);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		}
		else{ 
			Toast.makeText(xianxia1.this, "没有储存卡",Toast.LENGTH_LONG).show(); 
			} 
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);  
		switch (requestCode)
		{
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1)
			{
				Bimp.drr.add(path);
			}
			break;
		case SearchAddress_Result:
			if(resultCode == RESULT_OK){
				addressFromSearchAddressString = data.getExtras().getString("chooseAddress");
				latFromSearchAddress = data.getDoubleExtra("latdouble", 0);
				lonFromSearchAddress = data.getDoubleExtra("londouble", 0);
				cityFromSearchAddressString = data.getExtras().getString("city");
				Loger.i(cityFromSearchAddressString + addressFromSearchAddressString);
				
				address.setText(addressFromSearchAddressString);
				lat = latFromSearchAddress;
				lon = lonFromSearchAddress;
				geoPoint = new AVGeoPoint(lat, lon);
				city.setText(cityFromSearchAddressString.substring(0, cityFromSearchAddressString.length() - 1));
				Loger.l();
			}
		}
	}

}
