package com.example.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.*;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class fapinglun extends Activity {
	EditText commentcontent = null;
	public static String commentcontent1 = null;
	String orderIdString;
	String orderCreatorString;
	String creator;
//	static String orderStatusString;
//	static boolean isCreator;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fapinglun);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Button btn = (Button) findViewById(R.id.fasongpinglun);
		btn.setOnClickListener(new ButtonListener());
		Button btn1 = (Button) findViewById(R.id.qpinglun);
		btn1.setOnClickListener(new ButtonListener());
		commentcontent = (EditText) findViewById(R.id.pl);
		commentcontent.setFocusable(true);
		commentcontent.setFocusableInTouchMode(true);
		commentcontent.requestFocus();
		Timer timer = new Timer();
	     timer.schedule(new TimerTask()
	     {         
	         public void run()
	         {
	             InputMethodManager inputManager =
	                 (InputMethodManager)commentcontent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	             inputManager.showSoftInput(commentcontent, 0);
	         }	         
	     }, 500);


		Bundle bundle = this.getIntent().getExtras();
		orderIdString = bundle.getString("orderId");
		orderCreatorString=bundle.getString("ordercreator");
	}

	private void commit(final String comment_content) {
		System.out.println("即将发送2");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderIdString); // 拼单id
		parameters.put("content", comment_content);
		progressDialogShow();
		AVCloud.callFunctionInBackground("addComment", parameters,
				new FunctionCallback<Map<String, Object>>() {
					public void done(Map<String, Object> object, AVException e) {
						if (e == null) {
							Loger.i("添加评论成功 : " + object);
							Loger.i("要跳转了1:");
							try {
								Thread.sleep(1000);
								progressDialogDismiss();
								Toast.makeText(getApplicationContext(),"评论成功", Toast.LENGTH_SHORT).show();
							} catch (InterruptedException e1) {
							}
							
							fapinglun.this.finish();
							
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fasongpinglun:
				commentcontent1 = commentcontent.getText().toString();
				if (!commentcontent1.trim().isEmpty()) {
					commit(commentcontent1);

				} else
					Toast.makeText(fapinglun.this, "输入内容为空", Toast.LENGTH_SHORT)
							.show();
				break;
			case R.id.qpinglun:
				finish();
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

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(fapinglun.this,
						fapinglun.this.getResources().getText(
								R.string.dialog_title_remind),
						fapinglun.this.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}
}
