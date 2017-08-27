package com.example.test.PinFragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
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
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class huifu extends Activity {
	EditText huifucontent = null;
	TextView caidan;
	String orderIdString;
	String creator;
	String commentcreatorid;
	String creatorname;
	String commentcontent;
//	static String orderStatusString;
//	static boolean isCreator;
//	static boolean isParticipants;
	public static String huifucontent1 = null;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fahuifu);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Button btn = (Button) findViewById(R.id.fasonghuifu);
		btn.setOnClickListener(new ButtonListener());
		Button btn1 = (Button) findViewById(R.id.qhuifu);
		btn1.setOnClickListener(new ButtonListener());
		huifucontent = (EditText) findViewById(R.id.hf);
		huifucontent.setFocusable(true);
		huifucontent.setFocusableInTouchMode(true);
		huifucontent.requestFocus();
		Timer timer = new Timer();
	     timer.schedule(new TimerTask()
	     {         
	         public void run()
	         {
	             InputMethodManager inputManager =
	                 (InputMethodManager)huifucontent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	             inputManager.showSoftInput(huifucontent, 0);
	         }	         
	     }, 500);
	     
		Bundle bundle = this.getIntent().getExtras();
		creatorname = bundle.getString("creatorname");
		orderIdString = bundle.getString("orderId");
		creator = bundle.getString("ordercreator");
		commentcreatorid=bundle.getString("commentcreatorid");
		commentcontent=bundle.getString("commentcontent");
		Loger.i("commentcontent"+commentcontent);
//		orderStatusString = bundle.getString("orderstatus");
//		isCreator = bundle.getBoolean("isCreator");
//		isParticipants = bundle.getBoolean("isParticipants");
		caidan= (TextView) findViewById(R.id.huifucaidan);
		caidan.setText("�ظ�"+creatorname);
		huifucontent.setHint("�ظ�\""+commentcontent+"\"");

	}

	private void commit(final String huifu_content, final String creator_id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderIdString);
		parameters.put("content", huifu_content);
		parameters.put("to", creator_id);
		Loger.i("creator_id : " + creator_id);
		
		progressDialogShow();
		AVCloud.callFunctionInBackground("addComment", parameters,
				new FunctionCallback<Map<String, Object>>() {
					public void done(Map<String, Object> object, AVException e) {
						if (e == null) {
							Loger.i("�ظ��ɹ� : " + object);
							Loger.i("Ҫ��ת��1:");
							try {
								Thread.sleep(1000);
								progressDialogDismiss();
								Toast.makeText(getApplicationContext(),"�ظ��ɹ�", Toast.LENGTH_SHORT).show();
							} catch (InterruptedException e1) {
							}
//							Intent intent = new Intent(huifu.this, SeePindan.class);
//							Bundle bundle1 = new Bundle();
//
//							bundle1.putString("ordercreator", creator);
//							bundle1.putString("orderId", orderIdString);
////							bundle1.putString("orderstatus", orderStatusString);
////							bundle1.putBoolean("isCreator", isCreator);
////							bundle1.putBoolean("isParticipants", isParticipants);
//							intent.putExtras(bundle1);
//							startActivity(intent);
							finish();
						} else {
							Loger.d("ʧ��" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fasonghuifu:
				//huifucontent1 = "�ظ�" + creatorname + "��"+ huifucontent.getText().toString();
				huifucontent1 = huifucontent.getText().toString();
				if (!huifucontent1.trim().isEmpty()) {
					commit(huifucontent1, commentcreatorid);
				} else
					Toast.makeText(huifu.this, "��������Ϊ��", Toast.LENGTH_SHORT)
							.show();
				break;
			case R.id.qhuifu:
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
				.show(huifu.this,
						huifu.this.getResources().getText(
								R.string.dialog_title_remind),
						huifu.this.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}
}
