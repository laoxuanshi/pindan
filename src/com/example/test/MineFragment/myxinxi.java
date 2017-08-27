package com.example.test.MineFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;  
import android.view.LayoutInflater;  
import android.view.ViewGroup;  

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.fragment.*;
import com.example.test.util.Loger;

@SuppressLint("NewApi")
public class myxinxi  extends Activity 
{
	private Button scButton,btn1,btn2;
	RelativeLayout re,ni,se,ag,ci,ph,in,homes;
	
    String arg0[] = { "从图库上传", "拍照上传" };
    String sex_range[] = { "男", "女" };
    String age_range[] = { "60前", "60后","70后","80后","90后","00后","10后" };
    String interest_range[] = { "服饰", "鞋靴","家电","数码","图书","食品","汽车用品","运动户外","电脑办公","家居家装", "个护化妆","其他商品"};
	private String imageDir;
	public static String userIdString = null;
	public static String imgId = null;
	public final static int PHOTO_ZOOM = 0;
	public final static int TAKE_PHOTO = 1;
	public final static int PHOTO_RESULT = 2;
	public final static int NICKNAME_RESULT = 3;
	public final static int CITY_RESULT = 4;
	public final static int INTEREST_RESULT = 5;
	public final static int REGION_RESULT = 6;
	
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private static ImageView touxiangPicImageView;
	
	static String nickNameString = null;
	static String sexString = null;
	static Integer agenum = 0;
	static String portraitIdString = null;
	static String cityString = null;
	static String phonenum = null;
	static ArrayList<Integer> inter=null;
	static String showinter = "",showinter1 = "";
	TextView nic,sex,age,city,phone,interes,watchRegion;
	
	static String homeAddressString = "";
	ArrayList<String> homeList = new ArrayList<String>();//保存关注区域
	private ImageDownLoader mImageDownLoader;
    
	@Override
	    protected void onCreate(Bundle savedInstanceState) 
	  {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_mine1);
	        
	        mImageDownLoader = new ImageDownLoader(myxinxi.this);
	        
	        Loger.i("加载页面");
	        ActionBar actionBar = this.getActionBar();     
	        actionBar.setTitle("                     编辑信息");
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,ActionBar.DISPLAY_HOME_AS_UP); 
	        
			touxiangPicImageView = (ImageView) findViewById(R.id.touxiangsuccess);
			nic = (TextView) findViewById(R.id.changenickname);
			sex = (TextView) findViewById(R.id.changesex);
			age = (TextView) findViewById(R.id.changeage);
			city = (TextView) findViewById(R.id.changecity);
			phone = (TextView) findViewById(R.id.changephone);
			interes = (TextView) findViewById(R.id.changinterest);
			watchRegion = (TextView) findViewById(R.id.watchregion);
			
	        re=(RelativeLayout)findViewById(R.id.settouxiang);
	        re.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
        			dialog();
        		}
        	});
	        
	        ni=(RelativeLayout)findViewById(R.id.setnickname);
	        ni.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
					Intent intent = new Intent(myxinxi.this, gerenxinxi_nickname.class);
					Bundle bundle1 = new Bundle();

					bundle1.putString("nickname", nic.getText().toString());
					intent.putExtras(bundle1);
					startActivityForResult(intent, NICKNAME_RESULT);
        		}
        	});
	        
	        se=(RelativeLayout)findViewById(R.id.setsex);
	        se.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
        			dialog1();
        		}
        	});
	        
	        ag=(RelativeLayout)findViewById(R.id.setage);
	        ag.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
        			dialog2();
        		}
        	});
	        
	        ci=(RelativeLayout)findViewById(R.id.setcity);
	        ci.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
					Intent intent = new Intent(myxinxi.this, gerenxinxi_city.class);
					Bundle bundle1 = new Bundle();

					bundle1.putString("city", city.getText().toString());
					intent.putExtras(bundle1);
					startActivityForResult(intent, CITY_RESULT);
        		}
        	});
	        
	        ph=(RelativeLayout)findViewById(R.id.setphone);
	        ph.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
					Toast.makeText(myxinxi.this, "不允许更改",Toast.LENGTH_SHORT).show();
        		}
        	});
	        
	        in=(RelativeLayout)findViewById(R.id.setinterest);
	        in.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
					Intent intent = new Intent(myxinxi.this, gerenxinxi_interest.class);
					intent.putIntegerArrayListExtra("interstList", inter);
					startActivityForResult(intent, INTEREST_RESULT);
        		}
        	});
	        
	        homes=(RelativeLayout)findViewById(R.id.sethomes);
	        homes.setOnClickListener(new OnClickListener() 
        	{
        		public void onClick(View v) 
        		{
					Intent intent = new Intent(myxinxi.this, WatchRegion.class);
					Loger.i(homeList + "");
					intent.putStringArrayListExtra("homelist", homeList);
					startActivityForResult(intent, REGION_RESULT);
        		}
        	});
	        
	        userIdString = AVUser.getCurrentUser().getObjectId();
	        phonenum=AVUser.getCurrentUser().getMobilePhoneNumber();
			AVQuery<AVObject> query = new AVQuery<AVObject>("UserProfile");
			query.whereEqualTo("userId", userIdString);
			query.findInBackground(new FindCallback<AVObject>() {
				public void done(List<AVObject> avObjects, AVException e) {
					if (e == null) 
					{
						portraitIdString = (String)avObjects.get(0).get("avatar");
						if(portraitIdString==null)
							touxiangPicImageView.setImageResource(R.drawable.mine);
						else
							getPicUrl(portraitIdString);      
						nickNameString = (String)avObjects.get(0).get("nickName");
						nic.setText(nickNameString);
						sexString = (String)avObjects.get(0).get("gender");
						if(sexString.trim().equals("M"))
							sex.setText("男");
						else
							sex.setText("女");
						agenum = (Integer)avObjects.get(0).get("ageRange");
						age.setText(age_range[agenum]);
						
						cityString=(String)avObjects.get(0).get("usualCity");
						city.setText(cityString);
						phone.setText(phonenum);
						Loger.i("phonenum:"+phonenum);
						inter=(ArrayList<Integer>)avObjects.get(0).get("category1");
						showinter="";
						for(int i=0;i<inter.size();i++)
							showinter+=interest_range[inter.get(i)]+"、";
						interes.setText(showinter.substring(0, showinter.length()-1));
					} 
					else 
					{
						Log.d("失败", "查询错误: " + e.getMessage());
					}
				}
			});
			
			AVQuery<AVObject> query1 = new AVQuery<AVObject>("UserHomes");
			query1.whereEqualTo("userId", userIdString);
			query1.findInBackground(new FindCallback<AVObject>() {
				public void done(List<AVObject> avObjects, AVException e) {
					if (e == null) 
					{
						homeAddressString = "";
						for (int i = 0; i < avObjects.size(); i++) {
							homeList.add(i, (String)avObjects.get(i).get("description"));
							Loger.i((String)avObjects.get(i).get("description"));
							homeAddressString += (String)avObjects.get(i).get("description") + "\n";
							Loger.i("关注区域:" + homeAddressString);
						}
						watchRegion.setText(homeAddressString);
					} 
					else 
					{
						Log.d("失败", "查询错误: " + e.getMessage());
					}
				}
			});
	     
	  }
	  
	public void getPicUrl(String imageIdString) 
	{
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条头像url");
					if(avObjects.size()!=0)
					{
						final String urlString = (String) avObjects.get(0).get("url");
						Loger.i("urlString:"+urlString);
						Bitmap bitmap = mImageDownLoader.downloadImage(urlString,
								new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (bitmap != null) {
											Loger.l();
											touxiangPicImageView.setImageBitmap(bitmap);
										}
									}
								});
						if (bitmap != null) {
							Loger.l();
							touxiangPicImageView.setImageBitmap(bitmap);
						}
//						new DownloadImageTask((ImageView)findViewById(R.id.touxiangsuccess)).execute(urlString);
					}
				} else {
					Log.d("失败", "查询头像url错误: " + e.getMessage());
				}
			}
		});
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
    			Intent intent1=new Intent(myxinxi.this, MainActivity.class);
				Bundle bundle1 = new Bundle();
				bundle1.putInt("fagment_index", 3);
				intent1.putExtras(bundle1);
				startActivity(intent1);
				finish();
	           break;  
	       }  
	       return super.onOptionsItemSelected(item);  
	   } 
	  
		private void dialog() {

			new AlertDialog.Builder(this).setTitle("上传头像")
					.setIcon(android.R.drawable.ic_media_play)
					.setItems(arg0, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							switch (which) {
							case 0:
								dialog.dismiss();
								Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
								intent.setType(IMAGE_UNSPECIFIED);
								Intent wrapperIntent = Intent.createChooser(intent, null);
								String brand = Build.BRAND;
								if (brand.equalsIgnoreCase("Xiaomi")){
									photoZoom(wrapperIntent.getData());
								}else {
									startActivityForResult(wrapperIntent, PHOTO_ZOOM);
								}
								break;
							case 1:
								dialog.dismiss();
								imageDir = "temp.jpg";
								Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
										Environment.getExternalStorageDirectory(), imageDir)));
								startActivityForResult(intent1, TAKE_PHOTO);
								break;
							default:
								break;

							}
						}
					}).show();

		}
		
		private void dialog1() {

			new AlertDialog.Builder(this).setTitle("选择性别")
					.setIcon(android.R.drawable.ic_media_play)
					.setItems(sex_range, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							switch (which) {
							case 0:
								dialog.dismiss();
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("userId", userIdString);
								parameters.put("gender", "M");
								AVCloud.callFunctionInBackground("updateUserProfile", parameters,
										new FunctionCallback<Object>() {
											public void done(Object object, AVException e) {
												if (e == null) {
													Loger.i("性别更改成功: " + object);
												} else {
													Loger.d("sms result: " + e.getCode() + ", "
															+ e.getMessage());
												}
											};
										});
								sex.setText("男");
								break;
							case 1:
								dialog.dismiss();
								Map<String, Object> parameters1 = new HashMap<String, Object>();
								parameters1.put("userId", userIdString);
								parameters1.put("gender", "F");
								AVCloud.callFunctionInBackground("updateUserProfile", parameters1,
										new FunctionCallback<Object>() {
											public void done(Object object, AVException e) {
												if (e == null) {
													Loger.i("性别更改成功: " + object);
												} else {
													Loger.d("sms result: " + e.getCode() + ", "
															+ e.getMessage());
												}
											};
										});
								sex.setText("女");
								break;
							default:
								break;

							}
						}
					}).show();
		}
		
		private void dialog2() {

			new AlertDialog.Builder(this).setTitle("选择年龄段")
					.setIcon(android.R.drawable.ic_media_play)
					.setItems(age_range, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

							switch (which) {
							case 0:
								dialog.dismiss();
								changeage("0");
								age.setText(age_range[0]);
								break;
							case 1:
								dialog.dismiss();
								changeage("1");
								age.setText(age_range[1]);
								break;
							case 2:
								dialog.dismiss();
								changeage("2");
								age.setText(age_range[2]);
								break;
							case 3:
								dialog.dismiss();
								changeage("3");
								age.setText(age_range[3]);
								break;
							case 4:
								dialog.dismiss();
								changeage("4");
								age.setText(age_range[4]);
								break;
							case 5:
								dialog.dismiss();
								changeage("5");
								age.setText(age_range[5]);
								break;
							case 6:
								dialog.dismiss();
								changeage("6");
								age.setText(age_range[6]);
								break;
							default:
								break;

							}
						}
					}).show();
		}
		
		public void changeage(final String a)
		{
			Map<String, Object> parameters1 = new HashMap<String, Object>();
			parameters1.put("userId", userIdString);
			parameters1.put("ageRange", a);
			AVCloud.callFunctionInBackground("updateUserProfile", parameters1,
					new FunctionCallback<Object>() {
						public void done(Object object, AVException e) {
							if (e == null) {
								Loger.i("年龄更改成功: " + object);
							} else {
								Loger.d("sms result: " + e.getCode() + ", "
										+ e.getMessage());
							}
						};
					});
		}
		
		private Uri imageUri = null;
		
	public void photoZoom(Uri uri) {
		String brand = Build.BRAND;
		Loger.i(brand);
		if (brand.equalsIgnoreCase("Xiaomi")) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 300);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			startActivityForResult(intent, PHOTO_RESULT);
		} else {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 5);
			intent.putExtra("aspectY", 5);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 500);
			intent.putExtra("outputY", 500);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PHOTO_RESULT);
		}
	}
		private Bitmap decodeUriAsBitmap(Uri uri){
			    Bitmap bitmap = null;
			    try {
			        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			        return null;
			    }
			    return bitmap;
			}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				if(requestCode == NICKNAME_RESULT){
					String nickNameString = data.getExtras().getString("nickName");
					nic.setText(nickNameString);
				}
				if(requestCode == CITY_RESULT){
					String cityString = data.getExtras().getString("city");
					city.setText(cityString);
				}
				if(requestCode == INTEREST_RESULT){
					String[] interest = data.getExtras().getStringArray("chooseInterest");
					inter = data.getExtras().getIntegerArrayList("inter");
					showinter1 = "";
					for(int i=0;i<interest.length;i++)
						showinter1+=interest[i]+"、";
					interes.setText(showinter1.substring(0, showinter1.length()-1));
				}
				if(requestCode == REGION_RESULT){
					homeAddressString = data.getExtras().getString("homes");
					watchRegion.setText(homeAddressString);
				}
				if (requestCode == PHOTO_ZOOM) {
					photoZoom(data.getData());

				}
				if (requestCode == TAKE_PHOTO) {
					File picture = new File(
							Environment.getExternalStorageDirectory() + "/"
									+ imageDir);
					photoZoom(Uri.fromFile(picture));

				}

				if (requestCode == PHOTO_RESULT) {
//					if(uri != null){
//						        Bitmap bitmap = decodeUriAsBitmap(imageUri);//decode bitmap
//						    }
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap photo = extras.getParcelable("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
						userIdString = AVUser.getCurrentUser().getObjectId();
//						touxiangPicImageView.setImageBitmap(photo);
						byte[] dd = stream.toByteArray();
						final AVFile imageFile = new AVFile(userIdString + "avatar.jpg",
								dd);
						imageFile.saveInBackground(new SaveCallback() {
							@Override
							public void done(AVException e) {
								if (e == null) {
									imgId = imageFile.getObjectId();
									final Map<String, Object> parameters = new HashMap<String, Object>();
									parameters.put("userId", userIdString);
									parameters.put("avatar", imgId);
									AVCloud.callFunctionInBackground("updateUserProfile", parameters,
											new FunctionCallback<Object>() {
												public void done(Object object, AVException e) {
													if (e == null) {
														Loger.i("头像上传成功: " + object);
														Intent intent5 = new Intent(myxinxi.this,myxinxi.class);
														startActivity(intent5);
														finish();
													} else {
														Loger.d("sms result: " + e.getCode() + ", "
																+ e.getMessage());
														Toast.makeText(myxinxi.this, "提交失败",
																Toast.LENGTH_SHORT).show();
													}
												};
											});
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
				}
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
}
