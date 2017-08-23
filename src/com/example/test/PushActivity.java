package com.example.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.ChatDemoMessage.MessageType;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PushActivity extends ListActivity {

	public String userid;
	private List<Map<String, Object>> mData1;
	private PullToRefreshListView listView1; // 定义ListView组件
	private SimpleAdapter simpleAdapter1 = null; // 进行数据的转换操作
	int page1 = 1;
	int flag;
	int dbflag=0;
	static long currentTime;

	String descriptionString;
	String spotString;
	String orderCreatorString;
	String orderIdString;

	DBRecommend dbrecommend;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		page1 = 1;		
		setContentView(R.layout.push_listview);

		dbrecommend = new DBRecommend(this, "pushorderhistory2.db", 1);

		listView1 = (PullToRefreshListView) findViewById(R.id.list_push);
		mData1 = new ArrayList<Map<String, Object>>();

		simpleAdapter1 = new SimpleAdapter(this, mData1, R.layout.activity_push,
				new String[] { "description", "spot", "flag" }, new int[] {
						R.id.description, R.id.spot, flag });
		setListAdapter(simpleAdapter1);

		listView1.setAdapter(simpleAdapter1);

		listView1.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					new GetHeaderDataTask().execute();
				} else {
					new GetDataTask().execute();
				}
			}
		});

		listView1.setMode(Mode.BOTH);

		pushRequest();

		listView1.setOnItemClickListener(new OnItemClickListenerImpl());

		// actionbar返回到消息界面
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         推荐");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				simpleAdapter1.notifyDataSetChanged();
			}
		}
	};

	@Override
	protected void onResume() 
	{
//		new GetHeaderDataTask().execute();
		super.onResume();
	};

	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(PushActivity.this, SeePindan.class);
			/* 通过Bundle对象存储需要传递的数据 */
			Bundle bundle = new Bundle();
			/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
			bundle.putString("orderId", mData1.get(position - 1).get("orderId")
					.toString());
			bundle.putString("ordercreator",
					mData1.get(position - 1).get("ordercreator").toString());
			bundle.putString("orderstatus", "拼单中");
			Loger.i("mData1:" + mData1.get(position - 1));
			/* 把bundle对象assign给Intent */
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	public void pushRequest() {
		currentTime = System.currentTimeMillis() / 1000;
		Loger.i(currentTime + "时间戳");
		// 获取推荐列表
		Map<String, Object> parameters = new HashMap<String, Object>();
		userid = AVUser.getCurrentUser().getObjectId();
		parameters.put("userId", userid);
		parameters.put("page", page1);
		Loger.i("params:" + parameters);
		AVCloud.callFunctionInBackground("getPush", parameters,
				new FunctionCallback<List<Map<String, Object>>>() {
					public void done(List<Map<String, Object>> object,
							AVException e) {
						if (e == null) 
						{
							page1++;
							Loger.i("push成功 : " + object);
							if (object.size() == 0) 
							{
								Toast.makeText(getApplicationContext(),
										"当前没有新的推送消息", Toast.LENGTH_SHORT)
										.show();
								gethistorypush(20);
								dbflag=20;
								Loger.i("开始没有获取push单");
							}
							else
							{
								for (Map<String, Object> map : object) 
								{
									mData1.add(convertResult(map));
									simpleAdapter1.notifyDataSetChanged();
									// TODO 改变逻辑
								}
								gethistorypush(20-object.size());
								dbflag=20-object.size();
								for (Map<String, Object> map : object) 
									insertreadedorder(map);
							}
						} 
						else 
						{
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							if (e.getCode() == 0)
								Toast.makeText(PushActivity.this, "无网络连接",
										Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	public Map<String, Object> convertResult(Map<String, Object> map) {
		spotString = map.get("city").toString();
		descriptionString = map.get("description").toString();
		orderIdString = map.get("orderId").toString();
		orderCreatorString = map.get("creator").toString();
		flag = 0;

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("spot", spotString);
		map1.put("description", descriptionString);
		map1.put("orderId", orderIdString);
		map1.put("ordercreator", orderCreatorString);
		map1.put("flag", flag);

		return map1;
	}

	// 往数据表里面插入已读拼单
	public int insertreadedorder(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return 0;
		}
		SQLiteDatabase db = dbrecommend.getWritableDatabase();
		Loger.i("写数据库路径" + db.getPath());
		db.beginTransaction();
		try {
			Loger.i("开始插入数据");
			ContentValues cv = new ContentValues();
			userid = AVUser.getCurrentUser().getObjectId();
			cv.put("userId", userid);
			cv.put("orderId", map.get("orderId").toString());
			cv.put("ordercreator", map.get("creator").toString());
			cv.put("spot", map.get("city").toString());
			cv.put("description", map.get("description").toString());
			cv.put("timestamp", System.currentTimeMillis());
			long re = db.insert("recommend", null, cv);
			Loger.i("插入结果:" + re);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return 0;
	}

	public void gethistorypush(int size) {
		SQLiteDatabase db = dbrecommend.getReadableDatabase();
		Loger.i("读数据库路径" + db.getPath());
		assert db != null;
		userid = AVUser.getCurrentUser().getObjectId();
		// 根据groupId查询
		Cursor c = db
				.query("recommend", null, "userId=?", new String[] { userid },
						null, null, "timestamp" + " desc", size+""); // 应该还需要反序的
		Loger.i("记录数目" + c.getCount());

		while (c.moveToNext()) {
			flag = 1;
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("spot", c.getString(c.getColumnIndex("spot")));
			map1.put("description",
					c.getString(c.getColumnIndex("description")));
			map1.put("orderId", c.getString(c.getColumnIndex("orderId")));
			map1.put("ordercreator",
					c.getString(c.getColumnIndex("ordercreator")));
			map1.put("flag", flag);
			mData1.add(map1);
			Loger.l();
			simpleAdapter1.notifyDataSetChanged();
		}
		c.close();
		db.close();
	}

	private class GetDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			userid = AVUser.getCurrentUser().getObjectId();
			parameters.put("userId", userid);
			parameters.put("page", 1);
			parameters.put("queryTime", currentTime);
			Loger.i("params:" + parameters);
			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"getPush", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				if (e.getCode() == 0)
					Toast.makeText(PushActivity.this, "无网络连接",
							Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				Loger.i("push成功 : " + result);
				page1++;
				mData1.clear();
				simpleAdapter1.notifyDataSetChanged();
				if (result.size() == 0)
				{
					Loger.i("dbflag"+dbflag);
					gethistorypush(20+dbflag);
					dbflag+=20;
				}
				else
				{
					for (Map<String, Object> map : result) 
					{
						mData1.add(convertResult(map));
						simpleAdapter1.notifyDataSetChanged();
						// TODO 改变逻辑
					}
					gethistorypush(dbflag+20-result.size());
					dbflag+=20-result.size();
					for (Map<String, Object> map : result)
						insertreadedorder(map);
				}
			}
			listView1.onRefreshComplete();
		}
	}

	private class GetHeaderDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			page1 = 1;
			currentTime = System.currentTimeMillis() / 1000;
			Map<String, Object> parameters = new HashMap<String, Object>();
			userid = AVUser.getCurrentUser().getObjectId();
			parameters.put("userId", userid);
			parameters.put("page", page1);
			parameters.put("queryTime", currentTime);

			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"getPush", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(PushActivity.this, e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				mData1.clear();
				simpleAdapter1.notifyDataSetChanged();
				page1++;
				handler.sendEmptyMessage(page1);
				if (result.size() == 0)
					gethistorypush(20);
				else 
				{
					for (Map<String, Object> map : result) 
					{
						mData1.add(convertResult(map));
						simpleAdapter1.notifyDataSetChanged();
					}
					gethistorypush(20-result.size());
					for (Map<String, Object> map : result) 
						insertreadedorder(map);
				}
			}
			listView1.onRefreshComplete();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
