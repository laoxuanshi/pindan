package com.example.test.PinFragment;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.Group;
import com.avos.avoscloud.Session;
import com.avos.avoscloud.SessionManager;
import com.baidu.location.BDLocation;
import com.example.test.DemoGroupMessageReceiver.JoinedListener;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TimePicker;

public class xianshang2 extends Activity implements View.OnTouchListener {
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private Spinner mySpinner1, mySpinner2;
	private ArrayAdapter<String> adapter1, adapter2;

	private EditText etendTime;
	private Button backButton;
	private Button sendButton;
	
	public final static int SearchAddress1_Result = 4;
	public static String userIdString = null;
	public static String titleString = null;
	public static String linkurl = null;
	public static String imgId = null;
	public static String description1 = null;
	public static String city1 = null;
	public static String address1 = null;
	public static String category1, category2 = null;
	public static long unix;
	public static ArrayList<String> imgarray = new ArrayList<String>();

	EditText description;
	EditText city;
	TextView address;
	TextView showtitle;
	ImageView jiexiImageView;
	Bitmap bitmap;

	double latFromSearchAddress1 = 0;
	double lonFromSearchAddress1 = 0;
	
	private static double lat;// γ��
	private static double lon;// ����
	private static double latSource;
	private static double lonSource;
	private static AVGeoPoint geoPoint;
	private static double latClone;
	private static double lonClone;
	
	String cityString;
	String addressString;
	String addressFromSearchAddress1String;
	String cityFromSearchAddress1String;
	
	String category1CloneString;
	String category2CloneString;
	String descriptionCloneString;
	String imageUrlCloneString;
	String addressCloneString;
	String cityCloneString;
	
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xianshang2);
		progressDialogShow_Analize();
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
//		actionBar.setTitle("                     ����ƴ��");
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
//				ActionBar.DISPLAY_HOME_AS_UP);
		
		latFromSearchAddress1 = this.getIntent().getDoubleExtra("latdouble", 0);
		lonFromSearchAddress1 = this.getIntent().getDoubleExtra("londouble", 0);
		addressFromSearchAddress1String = this.getIntent().getStringExtra("chooseAddress");
		cityFromSearchAddress1String = this.getIntent().getStringExtra("city");
		
		addressCloneString = this.getIntent().getStringExtra("addressclone");
		cityCloneString = this.getIntent().getStringExtra("cityclone");
		
		address = (TextView) findViewById(R.id.shouhuoaddress);
		city = (EditText) findViewById(R.id.suozaicity);
		
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
				Loger.i(location.getLatitude() + "," + location.getLongitude());
				
				if (addressCloneString != null) {
					address.setText(addressCloneString);
				}else if (addressFromSearchAddress1String != null) {
					address.setText(addressFromSearchAddress1String);
				}else {
					address.setText(addressString);
				}
				
				if (cityCloneString != null) {
					city.setText(cityCloneString);
				}else if (cityFromSearchAddress1String != null) {
					city.setText(cityFromSearchAddress1String.substring(0, cityFromSearchAddress1String.length() - 1));
				}else {
					city.setText(cityString.substring(0, cityString.length() - 1));
				}
				
				progressDialogDismiss();
			}
		});
		
		list1.add("����");
		list1.add("Ьѥ");
		list1.add("�ҵ�");
		list1.add("����");
		list1.add("ͼ��");
		list1.add("ʳƷ");
		list1.add("������Ʒ");
		list1.add("�˶�����");
		list1.add("���԰칫");
		list1.add("�ҾӼ�װ");
		list1.add("������ױ");
		list1.add("������Ʒ");

		description = (EditText) findViewById(R.id.descript);
		mySpinner1 = (Spinner) findViewById(R.id.s_sort1);
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner1.setAdapter(adapter1);
		
		mySpinner2 = (Spinner) findViewById(R.id.s_sort2);
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
				// TODO Auto-generated method stub
				Intent intent = new Intent(xianshang2.this, SearchAddress1.class);
				intent.putExtra("title", titleString);
 				intent.putExtra("imgId", imgId);
 				intent.putExtra("linkurl",linkurl);
				startActivityForResult(intent, SearchAddress1_Result);
			}
		});


		userIdString = AVUser.getCurrentUser().getObjectId();

		titleString = this.getIntent().getCharSequenceExtra("title").toString();
		imgId = this.getIntent().getCharSequenceExtra("imgId").toString();
		imgarray.add(imgId);
		if(this.getIntent().getCharSequenceExtra("linkurl") != null){
			linkurl = this.getIntent().getCharSequenceExtra("linkurl").toString();
		}
		showtitle = (TextView) findViewById(R.id.jiexititle);
		showtitle.setText(titleString);

		jiexiImageView = (ImageView) findViewById(R.id.jiexiimg);
		getPicUrl(imgId);



		mySpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						switch (arg2) {
						case 0:
							category1 = "0";
							list2.clear();
							list2.add("��װ");
							list2.add("Ůװ");
							list2.add("�������");
							list2.add("����");

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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("��Ь");
							list2.add("ŮЬ");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("��ҵ�");
							list2.add("�������");
							list2.add("��������");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("�ֻ�");
							list2.add("�ֻ����");
							list2.add("��Ӱ");
							list2.add("�������");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("������");
							list2.add("����");
							list2.add("��ѧ");
							list2.add("����");
							list2.add("�ٶ�");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("����ʳƷ");
							list2.add("����ʳƷ");
							list2.add("�ط��ز�");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("ά�ޱ���");
							list2.add("����װ��");
							list2.add("������ϴ");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("�˶���Ь");
							list2.add("����װ��");
							list2.add("������Ʒ");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("��������");
							list2.add("�����豸");
							list2.add("�칫��Ʒ");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("����");
							list2.add("�Ҿ�");
							list2.add("�ƾ�");
							list2.add("��װ����");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("�沿����");
							list2.add("���廤��");
							list2.add("ϴͷ����");
							list2.add("��ǻ����");
							list2.add("����");
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
											// ֪ͨSpinner2�����Ѹ���
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
							list2.add("����");
							category1 = "11";
							category2 = "110";
							break;
						}
						// ֪ͨSpinner2�����Ѹ���
						adapter2.notifyDataSetChanged();
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});
		
		// TODO clone
		category1CloneString = this.getIntent().getStringExtra("category1");
		category2CloneString = this.getIntent().getStringExtra("category2");
		descriptionCloneString = this.getIntent().getStringExtra("description");
		imageUrlCloneString = this.getIntent().getStringExtra("imageUrl");

		if (descriptionCloneString != null) {
			Loger.i(descriptionCloneString);
			description.setText(descriptionCloneString);
		}

		if (category1CloneString != null && category2CloneString != null) {
			Loger.i(category1CloneString + category2CloneString);
			category1 = category1CloneString;
			category2 = category2CloneString;
			int categ1 = Integer.parseInt(category1CloneString);
			int categ2 = Integer.parseInt(category2CloneString);
			Loger.i(category1CloneString + category2CloneString + categ1
					+ categ2);
			mySpinner1.setSelection(categ1);
			adapter1.notifyDataSetChanged();
			mySpinner2.setSelection(categ2 % 10);
			adapter2.notifyDataSetChanged();
		}

		// ��ֹʱ�����
		etendTime = (EditText) this.findViewById(R.id.edittext2);
		etendTime.setOnTouchListener(this);
		
		sendButton = (Button) this.findViewById(R.id.send_button_xianshang2);
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �ύ
				description1 = description.getText().toString();

				address1 = address.getText().toString();

				city1 = city.getText().toString();

				String timeString = etendTime.getText().toString();
				if(description1.length() == 0||address1.length() == 0||city1.length() == 0||
						timeString.length() == 0){
					Toast.makeText(getApplicationContext(), "������ƴ����Ϣ", 
							Toast.LENGTH_SHORT).show();
				}else {
					progressDialogShow();
					DemoGroupMessageReceiver.setJoinedListener(new JoinedListener() {
						@Override
						public void onJoined(String groupId) {
							Loger.i("��������ɹ�" + groupId + " Joined");
							shengchengorder(userIdString, description1, city1, address1,
									linkurl, titleString, category1, category2, unix, imgarray);
						}
					});
					
					Session session = SessionManager.getInstance(AVUser.getCurrentUser().getObjectId());
					Group group = session.getGroup();
					group.join();
					
				}
			}
		});

		backButton = (Button) this.findViewById(R.id.xianshang2_back);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO ����
				finish();
			}
		});
	}

	/*
	 * private class ButtonListener implements OnClickListener { public void
	 * onClick(View v) { switch(v.getId()) { case R.id.btnxs: Intent intent3=new
	 * Intent(xianshang2.this, xianshang2.class); startActivity(intent3); break;
	 * 
	 * } } }
	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.xinxi, menu);
//		return true;
//	}

	public void getPicUrl(String imageIdString) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
					final String urlString = (String) avObjects.get(0).get(
							"url");
					new DownloadImageTask(
							(ImageView) findViewById(R.id.jiexiimg))
							.execute(urlString);

					Loger.i("urlString:" + urlString);
				} else {
					Log.d("ʧ��", "��ѯ����: " + e.getMessage());
				}
			}
		});
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

	// ����ƴ��
	private void shengchengorder(final String user_id,
			final String descript_text, final String ct,
			final String shouhuo_address, final String sp_url,
			final String ti_tle, final String cat1, final String cat2,
			final long unix, final ArrayList<String> img_array) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("creator", user_id);
		parameters.put("description", descript_text);
		parameters.put("city", ct);
		parameters.put("address", shouhuo_address);
		parameters.put("targetLocation", geoPoint);
		parameters.put("sourceLatitude", latSource);
		parameters.put("sourceLongitude", lonSource);
		parameters.put("category1", cat1);
		parameters.put("category2", cat2);
		parameters.put("url", sp_url);
		parameters.put("title", ti_tle);
		parameters.put("images", img_array);
		parameters.put("endTime", unix);
		parameters.put("whetherOnline", "1");
		parameters.put("groupId", DemoGroupMessageReceiver.groupid);

		Loger.i("descript_text: " + descript_text);
		Loger.i("ct: " + ct);
		Loger.i("shouhuo_address: " + shouhuo_address);
		Loger.i("sp_url: " + sp_url);
		Loger.i("ti_tle: " + ti_tle);
		Loger.i("cat1: " + cat1);
		Loger.i("cat2: " + cat2);
		Loger.i("unix: " + unix);
		Loger.i("img_array: " + img_array);

		AVCloud.callFunctionInBackground("createOrder", parameters,
				new FunctionCallback<String>() {
					public void done(String object, AVException e) {
						if (e == null) {
							Loger.i("��ӳɹ�: " + object);
							progressDialogDismiss();
							Intent intent1 = new Intent(xianshang2.this, MainActivity.class);
							Bundle bundle1 = new Bundle();
							bundle1.putInt("fagment_index", 0);
							intent1.putExtras(bundle1);
							startActivity(intent1);
							finish();
						} else {
							Loger.d("ʧ��" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.action_tijiao1:
//			
//			description1 = description.getText().toString();
//
//			address1 = address.getText().toString();
//
//			city1 = city.getText().toString();
//
//			String timeString = etendTime.getText().toString();
//			if(description1.length() == 0||address1.length() == 0||city1.length() == 0||
//					timeString.length() == 0){
//				Toast.makeText(getApplicationContext(), "������ƴ����Ϣ", 
//						Toast.LENGTH_SHORT).show();
//			}else {
//				DemoGroupMessageReceiver.setJoinedListener(new JoinedListener() {
//					@Override
//					public void onJoined(String groupId) {
//						Loger.i("��������ɹ�" + groupId + " Joined");
//						shengchengorder(userIdString, description1, city1, address1,
//								linkurl, titleString, category1, category2, unix, imgarray);
//					}
//				});
//				
//				Session session = SessionManager.getInstance(AVUser.getCurrentUser().getObjectId());
//				Group group = session.getGroup();
//				group.join();
//				
//			}
//			
//			break;
//		case android.R.id.home:
//			finish();
//			break;
//
//		default:
//			break;
//		}
//
//		return true;
//	}

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

			if (v.getId() == R.id.edittext2) {
				int inType = etendTime.getInputType();
				etendTime.setInputType(InputType.TYPE_NULL);
				etendTime.onTouchEvent(event);
				etendTime.setInputType(inType);
				etendTime.setSelection(etendTime.getText().length());

				builder.setTitle("ѡ���ֹʱ��");
				builder.setPositiveButton("ȷ  ��",
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
//								etendTime.setText(sb);
								
								String ah = sb.toString();
								SimpleDateFormat df = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm");

								try {
									unix = df.parse(ah).getTime() / 1000;
								} catch (java.text.ParseException e) {
									e.printStackTrace();
								}

								long unixNow = System.currentTimeMillis()/1000;
								Loger.i("�û�ѡ���unixʱ���" + unix);
								Loger.i("��ǰʱ���unixʱ���" + unixNow);
								
								if(unix > unixNow){
									etendTime.setText(sb);
									dialog.cancel();
								}else {
									etendTime.setText(null);
									Toast.makeText(getApplicationContext(), "������δ��ʱ��", 
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SearchAddress1_Result) {
				latFromSearchAddress1 = data.getDoubleExtra("latdouble", 0);
				lonFromSearchAddress1 = data.getDoubleExtra("londouble", 0);
				addressFromSearchAddress1String = data.getStringExtra("chooseAddress");
				cityFromSearchAddress1String = data.getStringExtra("city");
				
				lat = latFromSearchAddress1;
				lon = lonFromSearchAddress1;
				geoPoint = new AVGeoPoint(latSource, lonSource);
				Loger.i("�õ��ľ�γ������:" + lat + "," + lon);

				address.setText(addressFromSearchAddress1String);
				city.setText(cityFromSearchAddress1String.substring(0, cityFromSearchAddress1String.length() - 1));
				Loger.l();
			}
		}
	}
	
	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(xianshang2.this, "",
						xianshang2.this.getResources().getText(
						R.string.dialog_text_wait), true, true);
	}
	
	private void progressDialogShow_Analize() {
		progressDialog = ProgressDialog
				.show(xianshang2.this, "",
						xianshang2.this.getResources().getText(
						R.string.dialog_text_analize), true, true);
	}
}
