package com.example.test;

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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.ActionBar;
import android.app.Activity;
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

public class modifyonline extends Activity  
{
	private List<String> list1 = new ArrayList<String>();
	private List<String> list2 = new ArrayList<String>();
	private Spinner mySpinner1, mySpinner2;
	private ArrayAdapter<String> adapter1, adapter2;

	private TextView etendTime;
	static String orderIdString = null;
	static String orderCreatorString = null;
	public final static int SearchAddress1_Result = 4;
	public static String userIdString = null;
	public static String titleString = null;
	public static String linkurl = null;
	public static String imgId = null;
	public static String description1 = null;
	public static String city1 = null;
	public static String address1 = null;
	public static String category1, category2 = null;
	public static ArrayList<String> imgarray = new ArrayList<String>();

	EditText description;
	TextView city;
	TextView address;
	TextView showtitle;
	ImageView jiexiImageView;
	Bitmap bitmap;

	String cityString;
	
	String category1CloneString;
	String category2CloneString;
	String descriptionCloneString;
	String imageUrlCloneString;
	String addressCloneString;
	String cityCloneString;
	String endTimeString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifyonline);
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                     �޸�ƴ��");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
		
		orderIdString=this.getIntent().getStringExtra("orderId");				
		addressCloneString = this.getIntent().getStringExtra("addressclone");
		cityCloneString = this.getIntent().getStringExtra("cityclone");
		orderCreatorString= this.getIntent().getStringExtra("ordercreator");
		
		address = (TextView) findViewById(R.id.shouhuoaddress);
		address.setText(addressCloneString);
		city = (TextView) findViewById(R.id.suozaicity);
		city.setText(cityCloneString);
				
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
		
		userIdString = AVUser.getCurrentUser().getObjectId();

		titleString = this.getIntent().getCharSequenceExtra("title").toString();
		imgId = this.getIntent().getCharSequenceExtra("imgId").toString();
		imgarray.add(imgId);
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

		if (descriptionCloneString != null) 
		{
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

			mySpinner1.setSelection(categ1);
			adapter1.notifyDataSetChanged();
			mySpinner2.setSelection(categ2 % 10);
			adapter2.notifyDataSetChanged();
		}

		// ��ֹʱ�����
		endTimeString = this.getIntent().getStringExtra("endtime");
		etendTime = (TextView) this.findViewById(R.id.edittext2);
		etendTime.setText(endTimeString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xinxi, menu);
		return true;
	}

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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_tijiao1:	
			description1 = description.getText().toString();
			if(description1.trim().isEmpty())
				Toast.makeText(getApplicationContext(), "��������Ϊ��", Toast.LENGTH_SHORT).show();
			else
			{
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("orderId", orderIdString);
				parameters.put("description", description1);
				AVCloud.callFunctionInBackground("modifyOrder", parameters,
						new FunctionCallback<String>() {
							public void done(String object, AVException e) {
								if (e == null) 
								{
									Loger.i("�޸ĳɹ�: " + object);
									Toast.makeText(getApplicationContext(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
									Intent intent1 = new Intent();
									intent1.putExtra("description", description1);
									modifyonline.this.setResult(RESULT_OK, intent1);
									modifyonline.this.finish();
								
								} 
								else 
								{
									Loger.d("ʧ��" + e.getCode() + ", " + e.getMessage());
								}
							};
						});
			}
			break;
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}

		return true;
	}	
}
