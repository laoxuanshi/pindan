package com.example.test.SearchFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.util.Loger;

public class WatchRegion extends Activity {

	private Button cancelButton;
	private Button addButton;
	private ListView watchRegionListView;
//	private SimpleAdapter simpleAdapter;
	// private ImageView deleteImageView;

//	private List<Map<String, String>> watchRegionList; // ������ʾ�����ݰ�װ
	
	private ArrayAdapter<String> adapter;
	ArrayList<String> watchregionStringList;
	static String homeAddressString = "";
	
	private Map<String, Object> itemsMap = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.watchregion);

		ActionBar actionBar = this.getActionBar();
		actionBar.hide();

		cancelButton = (Button) findViewById(R.id.cancel_watchregion);
		addButton = (Button) findViewById(R.id.add_watchregion);
		watchRegionListView = (ListView) findViewById(R.id.watchregion_list);
		//deleteImageView = (ImageView)findViewById(R.id.regiondelete);
		
//simpleAdapter		
//		watchRegionList = new ArrayList<Map<String, String>>();
//		for (int i = 0; i < watchregionStringList.size(); i++) {
//			Map<String, String> map1 = new HashMap<String, String>();
//			map1.put("regiondescription", watchregionStringList.get(i));
//			Loger.i(map1.get("regiondescription") + "");
//			watchRegionList.add(map1);
//		}
//
//		simpleAdapter = new SimpleAdapter(this, watchRegionList,
//				R.layout.watchregion_item,
//				new String[] { "regiondescription" },
//				new int[] { R.id.regiondescription });
//		watchRegionListView.setAdapter(simpleAdapter);

		watchregionStringList = this.getIntent().getStringArrayListExtra("homelist");
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, watchregionStringList);
		watchRegionListView.setAdapter(adapter);

		watchRegionListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Loger.i("position:" + position + "list����:" + watchregionStringList.get(position));
				dialogForDelete(position);
			}
		});

		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < watchregionStringList.size(); i++) {
					homeAddressString += watchregionStringList.get(i) + "\n";
				}
				Loger.i("homeAddressString:" + homeAddressString);
				Intent intent1 = new Intent();
				intent1.putExtra("homes", homeAddressString);
				WatchRegion.this.setResult(RESULT_OK, intent1);
				WatchRegion.this.finish();
			}
		});

		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WatchRegion.this,
						gerenxinxi_homes.class);
				startActivity(intent);
			}
		});
	}

	protected void dialogForDelete(final int position) {
		AlertDialog.Builder builder = new Builder(WatchRegion.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				itemsMap.put("description", watchregionStringList.get(position));
				deleteWatchRegion(position);
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	protected void deleteWatchRegion(final int position) {
		String userIdString = AVUser.getCurrentUser().getObjectId();
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userIdString);
		params.put("subscribeType", "homes");
		params.put("operation", "remove");
		params.put("items", itemsMap);

		AVCloud.callFunctionInBackground("userSubscribe", params,
				new FunctionCallback<Object>() {
					public void done(Object object, AVException e) {
						if (e == null) {
							Loger.i("ɾ����ע����ɹ�: " + object);
							Toast.makeText(WatchRegion.this, "ɾ���ɹ�",
									Toast.LENGTH_SHORT).show();
							watchregionStringList.remove(position);
							adapter.notifyDataSetChanged();
						} else {
							Loger.d("sms result: " + e.getCode() + ", "
									+ e.getMessage());
							Toast.makeText(WatchRegion.this, "ɾ��ʧ��",
									Toast.LENGTH_SHORT).show();
						}
					};
				});
	}
}
