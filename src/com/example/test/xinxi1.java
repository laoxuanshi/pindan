package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

public class xinxi1 extends Activity {
	private List<String> list = new ArrayList<String>();
	private List<String> list1 = new ArrayList<String>();
	EditText nickNameEditText;
	EditText focusCityEditText;
	int checkCondition[] = new int[10];
	int checkCondition1[];
	int checkNum = 0;

	CheckBox checkBox0;
	CheckBox checkBox1;
	CheckBox checkBox2;
	CheckBox checkBox3;
	CheckBox checkBox4;
	CheckBox checkBox5;
	CheckBox checkBox6;
	CheckBox checkBox7;
	CheckBox checkBox8;
	CheckBox checkBox9;
	CheckBox checkBox10;
	CheckBox checkBox11;

	private static String nickNameString;
	private static String genderString;
	private static int ageRangeString;
	private static String focusCityString;
	String objectIdString;

	private Spinner mySpinner, mySpinner1;
	private ArrayAdapter<String> adapter, adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xinxi1);

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                      完善信息");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		// 下拉列表的选取

		list.add("女");
		list.add("男");

		list1.add("60前");
		list1.add("60后");
		list1.add("70后");
		list1.add("80后");
		list1.add("90后");
		list1.add("00后");
		list1.add("10后");
		
		mySpinner = (Spinner) findViewById(R.id.Spinner_gender);
		mySpinner1 = (Spinner) findViewById(R.id.Spinner_age);

		// 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list1);

		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		mySpinner.setAdapter(adapter);
		mySpinner1.setAdapter(adapter1);

		// 第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
		mySpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						genderString = list.get(arg2);
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});

		mySpinner1
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						ageRangeString = arg2;
						arg0.setVisibility(View.VISIBLE);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						arg0.setVisibility(View.VISIBLE);
					}
				});

		nickNameEditText = (EditText) findViewById(R.id.nickname1);
		focusCityEditText = (EditText) findViewById(R.id.focuscity);

		nickNameString = nickNameEditText.getEditableText().toString();
		genderString = list.get(0);
		ageRangeString = 0;
		focusCityString = focusCityEditText.getEditableText().toString();

		checkBox0 = (CheckBox) findViewById(R.id.item_cb0);
		checkBox1 = (CheckBox) findViewById(R.id.item_cb1);
		checkBox2 = (CheckBox) findViewById(R.id.item_cb2);
		checkBox3 = (CheckBox) findViewById(R.id.item_cb3);
		checkBox4 = (CheckBox) findViewById(R.id.item_cb4);
		checkBox5 = (CheckBox) findViewById(R.id.item_cb5);
		checkBox6 = (CheckBox) findViewById(R.id.item_cb6);
		checkBox7 = (CheckBox) findViewById(R.id.item_cb7);
		checkBox8 = (CheckBox) findViewById(R.id.item_cb8);
		checkBox9 = (CheckBox) findViewById(R.id.item_cb9);
		checkBox10 = (CheckBox) findViewById(R.id.item_cb10);
		checkBox11 = (CheckBox) findViewById(R.id.item_cb11);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.xinxi, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_tijiao1:
			sendInfo(nickNameString, genderString, ageRangeString,
					focusCityString);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void sendInfo(String nickNameString, String genderString,
			final int ageRangeString, String focusCityString) {
		final Map<String, Object> parameters = new HashMap<String, Object>();

		nickNameString = nickNameEditText.getEditableText().toString();
		focusCityString = focusCityEditText.getEditableText().toString();

		if (genderString.equals("男")) {
			genderString = "M";
		} else {
			genderString = "F";
		}

		AVUser currentUser = AVUser.getCurrentUser();
		objectIdString = currentUser.getObjectId();
//		Loger.i(objectIdString);
//		Loger.i(nickNameString);
//		Loger.i(ageRangeString + "");
//		Loger.i(genderString);
//		Loger.i(focusCityString);
		
		parameters.put("userId", objectIdString);
		parameters.put("nickName", nickNameString);
		parameters.put("gender", genderString);
		parameters.put("ageRange", ageRangeString);
		parameters.put("usualCity", focusCityString);

		AVCloud.callFunctionInBackground("updateUserProfile", parameters,
				new FunctionCallback<Object>() {
					public void done(Object object, AVException e) {
						if (e == null) {
							Loger.i("sms result : " + object);
							Toast.makeText(xinxi1.this, String.valueOf(object),
									Toast.LENGTH_SHORT).show();
							subscribe();
						} else {
							Loger.d("sms result: " + e.getCode() + ", "
									+ e.getMessage());
							Toast.makeText(xinxi1.this, "提交失败",
									Toast.LENGTH_SHORT).show();
						}
					};
				});
		
		
	}
	public void subscribe() {
		checkNum=0;

		if (checkBox0.isChecked()) {
			checkCondition[checkNum] = 0;
			checkNum++;
		}
		if (checkBox1.isChecked()) {
			checkCondition[checkNum] = 1;
			checkNum++;
		}
		if (checkBox2.isChecked()) {
			checkCondition[checkNum] = 2;
			checkNum++;
		}
		if (checkBox3.isChecked()) {
			checkCondition[checkNum] = 3;
			checkNum++;
		}
		if (checkBox4.isChecked()) {
			checkCondition[checkNum] = 4;
			checkNum++;
		}
		if (checkBox5.isChecked()) {
			checkCondition[checkNum] = 5;
			checkNum++;
		}
		if (checkBox6.isChecked()) {
			checkCondition[checkNum] = 6;
			checkNum++;
		}
		if (checkBox7.isChecked()) {
			checkCondition[checkNum] = 7;
			checkNum++;
		}
		if (checkBox8.isChecked()) {
			checkCondition[checkNum] = 8;
			checkNum++;
		}
		if (checkBox9.isChecked()) {
			checkCondition[checkNum] = 9;
			checkNum++;
		}
		if (checkBox10.isChecked()) {
			checkCondition[checkNum] = 10;
			checkNum++;
		}
		if (checkBox10.isChecked()) {
			checkCondition[checkNum] = 11;
			checkNum++;
		}

		checkCondition1 = new int[checkNum];
		for (int j = 0; j < checkNum; j++) {
			checkCondition1[j] = checkCondition[j];
		}

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", objectIdString);
		params.put("subscribeType", "category1");
		params.put("operation", "add");
		params.put("items", checkCondition1);

		AVCloud.callFunctionInBackground("userSubscribe", params,
				new FunctionCallback<Object>() {
					public void done(Object object, AVException e) {
						if (e == null) {
							Loger.i("sms result : " + object);
							Intent intent5 = new Intent(xinxi1.this,
									MainActivity.class);
							Bundle bundle1 = new Bundle();
							bundle1.putInt("fagment_index", 0);
							intent5.putExtras(bundle1);
							startActivity(intent5);
						} else {
							Loger.d("sms result: " + e.getCode() + ", "
									+ e.getMessage());
							Toast.makeText(xinxi1.this, "提交失败",
									Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
}
