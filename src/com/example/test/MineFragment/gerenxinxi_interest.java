package com.example.test.MineFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.util.Loger;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class gerenxinxi_interest extends Activity 
{
	private ProgressDialog progressDialog;
	public static String userIdString = null;
	static ArrayList<Integer> inter=null;
	static ArrayList<Integer> interestList=null;
	int checkCondition[] = new int[12];
	int checkCondition1[];
	int checkNum = 0;
	String interest_range[] = { "服饰", "鞋靴","家电","数码","图书","食品","汽车用品","运动户外","电脑办公","家居家装", "个护化妆","其他商品"};

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

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		interestList = this.getIntent().getIntegerArrayListExtra("interstList");
		
		setContentView(R.layout.interestedit);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Button btn = (Button) findViewById(R.id.saveinterest);
		btn.setOnClickListener(new ButtonListener());
		Button btn1 = (Button) findViewById(R.id.quxiaointerest);
		btn1.setOnClickListener(new ButtonListener());
		
		checkBox0 = (CheckBox) findViewById(R.id.item_cb00);
		checkBox1 = (CheckBox) findViewById(R.id.item_cb01);
		checkBox2 = (CheckBox) findViewById(R.id.item_cb02);
		checkBox3 = (CheckBox) findViewById(R.id.item_cb03);
		checkBox4 = (CheckBox) findViewById(R.id.item_cb04);
		checkBox5 = (CheckBox) findViewById(R.id.item_cb05);
		checkBox6 = (CheckBox) findViewById(R.id.item_cb06);
		checkBox7 = (CheckBox) findViewById(R.id.item_cb07);
		checkBox8 = (CheckBox) findViewById(R.id.item_cb08);
		checkBox9 = (CheckBox) findViewById(R.id.item_cb09);
		checkBox10 = (CheckBox) findViewById(R.id.item_cb010);
		checkBox11 = (CheckBox) findViewById(R.id.item_cb011);
		
		initCheckBoxes();
	}

	private void initCheckBoxes() {
		for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 0) {
				checkBox0.setChecked(true);
			}
		}
		for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 1) {
				checkBox1.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 2) {
				checkBox2.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 3) {
				checkBox3.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 4) {
				checkBox4.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 5) {
				checkBox5.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 6) {
				checkBox6.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 7) {
				checkBox7.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 8) {
				checkBox8.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 9) {
				checkBox9.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 10) {
				checkBox10.setChecked(true);
			}
		}for (int i = 0; i < interestList.size(); i++) {
			if (interestList.get(i) == 11) {
				checkBox11.setChecked(true);
			}
		}
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.saveinterest:
				progressDialogShow();
				userIdString = AVUser.getCurrentUser().getObjectId();
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
				if (checkBox11.isChecked()) {
					checkCondition[checkNum] = 11;
					checkNum++;
				}

				checkCondition1 = new int[checkNum];
				for (int j = 0; j < checkNum; j++) {
					checkCondition1[j] = checkCondition[j];
				}
				
				inter = new ArrayList<Integer>();
				
				for (int j = 0; j < checkNum; j++) {
					Loger.i(checkCondition1[j] + "");
					inter.add(checkCondition1[j]);
					Loger.i("" + inter.get(j));
				}
		
				final Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("userId", userIdString);
				params1.put("subscribeType", "category1");
				params1.put("operation", "remove");
				params1.put("items", myxinxi.inter);

				AVCloud.callFunctionInBackground("userSubscribe", params1,
						new FunctionCallback<Object>() {
							public void done(Object object, AVException e) {
								if (e == null) {
									Loger.i("删除兴趣成功: " + object);
									addinterest(checkCondition1);
								} else {
									Loger.d("sms result: " + e.getCode() + ", "
											+ e.getMessage());
									Toast.makeText(gerenxinxi_interest.this, "提交失败",
											Toast.LENGTH_SHORT).show();
								}
							};
						});

				break;
			case R.id.quxiaointerest:
				finish();
				break;

			}
		}
	}

	public void addinterest(final int[] a)
	{
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userIdString);
		params.put("subscribeType", "category1");
		params.put("operation", "add");
		params.put("items", a);

		AVCloud.callFunctionInBackground("userSubscribe", params,
				new FunctionCallback<Object>() {
					public void done(Object object, AVException e) {
						if (e == null) {
							Loger.i("添加兴趣成功: " + object);
//							Intent intent5 = new Intent(gerenxinxi_interest.this,
//									myxinxi.class);
//							startActivity(intent5);
//							finish();
							String[] interest = new String[checkNum];
							for (int i = 0; i < checkNum; i++) {
								interest[i] = interest_range[checkCondition1[i]];
								Loger.i(interest[i]);
							}
							progressDialogDismiss();
							Intent intent1 = new Intent();
							intent1.putExtra("chooseInterest", interest);
							intent1.putIntegerArrayListExtra("inter", inter);
							gerenxinxi_interest.this.setResult(RESULT_OK, intent1);
							gerenxinxi_interest.this.finish();
						} else {
							Loger.d("sms result: " + e.getCode() + ", "
									+ e.getMessage());
							Toast.makeText(gerenxinxi_interest.this, "提交失败",
									Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(gerenxinxi_interest.this,
						gerenxinxi_interest.this.getResources().getText(
								R.string.dialog_title_remind),
						gerenxinxi_interest.this.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}

}
