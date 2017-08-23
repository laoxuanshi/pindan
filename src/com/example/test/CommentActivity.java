package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class CommentActivity extends Activity{
	public String userid;
	private PullToRefreshListView listView; // 定义ListView组件
	private List<Map<String, Object>> mData; // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
	int dbflag=0;
	String creatorNameString;
	String toNameString;
	String content;
	String creatorAvatarIdString;
	
	String orderIdString;
	String orderCreatorString;
	DBComment dbcomment;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply_listview);
		
		dbcomment = new DBComment(this, "commenthistory.db", 1);
		
		listView = (PullToRefreshListView) findViewById(R.id.list_reply);
		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, mData,
				R.layout.activity_reply, new String[] { "creatorname",
						"content", "headreply" }, new int[] {
						R.id.nickname_reply, R.id.reply_content,
						R.id.head_reply });
		
		simpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		listView.setAdapter(simpleAdapter);

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					new GetHeaderDataTask().execute();
				} else {
					new GetDataTask().execute();
				}
			}
		});

		listView.setMode(Mode.BOTH);
		
		getReply();	
		
		listView.setOnItemClickListener(new OnItemClickListenerImpl());

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                         评论");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				simpleAdapter.notifyDataSetChanged();
			}
		}
	};
	
	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, final int position,
				long id) 
		{
			AVQuery<AVObject> query = new AVQuery<AVObject>("Orders");
			query.whereEqualTo("objectId", mData.get(position - 1).get("orderId")
					.toString());
			query.findInBackground(new FindCallback<AVObject>() {
				public void done(List<AVObject> avObjects, AVException e) {
					if (e == null) 
					{
						Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
						Intent intent = new Intent(CommentActivity.this, SeePindan.class);
						/* 通过Bundle对象存储需要传递的数据 */
						Bundle bundle = new Bundle();
						/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
						bundle.putString("orderId", mData.get(position - 1).get("orderId")
								.toString());
						bundle.putString("ordercreator",
								mData.get(position - 1).get("ordercreator").toString());
						Loger.i("mData:" + mData.get(position - 1));
						/* 把bundle对象assign给Intent */
						intent.putExtras(bundle);
						startActivity(intent);
					} 
					else 
					{
						Toast.makeText(getApplicationContext(),"此拼单已失效", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			

		}
	}
	
	public void getReply() {
		AVCloud.callFunctionInBackground("getReply", null, 
				new FunctionCallback<List<Map<String, Object>>>() {
			@Override
			public void done(List<Map<String, Object>> object, AVException e) {
				if(e == null){
					if(object != null){
						Loger.i(object + "");
						if(object.size()==0)
						{
							Toast.makeText(getApplicationContext(),"当前没有评论消息", Toast.LENGTH_SHORT).show();
							gethistorycomment(20);
							dbflag=20;
						}
						else
						{
							for (Map<String, Object> map : object) 
							{
								mData.add(convertResult(map));
								simpleAdapter.notifyDataSetChanged();
							}
							gethistorycomment(20-object.size());
							dbflag=20-object.size();
							for (Map<String, Object> map : object) 
								insertreadedcomment(map);
						}
					}
				}else {
					Loger.d(e.getMessage());
					if(e.getCode()==0)
						Toast.makeText(CommentActivity.this, "无网络连接",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public Map<String, Object> convertResult(Map<String, Object> map) {

		creatorNameString = map.get("creatorName").toString();
		orderIdString = map.get("orderId").toString();
		orderCreatorString = map.get("creator").toString();
		
		if(map.size()==8)
		{	
			toNameString = map.get("toName").toString();
			content = "回复"+toNameString+":"+map.get("content").toString();
		}
		else 
			content = map.get("content").toString();
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("creatorname", creatorNameString);
		map1.put("content", content);
		
		if (map.get("creatorAvatar") == null) {
			Loger.i("空头像");
			map1.put("headreply", R.drawable.mine);
		} else {
			creatorAvatarIdString = map.get("creatorAvatar").toString();
			Loger.i("头像ID：" + creatorAvatarIdString);
			getPicUrlComment(creatorAvatarIdString, map1);
		}

		map1.put("orderId", orderIdString);
		map1.put("ordercreator", orderCreatorString);
		
		return map1;
	}
	
	public int insertreadedcomment(Map<String, Object> map) 
	{
		if (map == null || map.size() == 0) 
		{
			return 0;
		}
		SQLiteDatabase db = dbcomment.getWritableDatabase();
		Loger.i("写数据库路径" + db.getPath());
		db.beginTransaction();
		try {
			Loger.i("开始插入数据");
			ContentValues cv = new ContentValues();
			userid = AVUser.getCurrentUser().getObjectId();
			cv.put("userId", userid);
			
			cv.put("orderId", map.get("orderId").toString());
			cv.put("creatorname", map.get("creatorName").toString());
			cv.put("ordercreator", map.get("creator").toString());
			if(map.size()==8)
			{	
				toNameString = map.get("toName").toString();
				content = "回复"+toNameString+":"+map.get("content").toString();
			}
			else 
				content = map.get("content").toString();
			
			cv.put("content", content);
			if(map.get("creatorAvatar") == null)
				cv.put("creatorAvatar", "null");
			else
				cv.put("creatorAvatar", map.get("creatorAvatar").toString());
			cv.put("timestamp", System.currentTimeMillis());
			long re = db.insert("comment", null, cv);
			Loger.i("插入结果:" + re);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return 0;
	}
	
	public void gethistorycomment(int size) 
	{
		SQLiteDatabase db = dbcomment.getReadableDatabase();
		Loger.i("读数据库路径" + db.getPath());
		assert db != null;
		userid = AVUser.getCurrentUser().getObjectId();
		// 根据groupId查询
		Cursor c = db
				.query("comment", null, "userId=?", new String[] { userid },
						null, null, "timestamp" + " desc", size+""); // 应该还需要反序的
		Loger.i("记录数目" + c.getCount());

		while (c.moveToNext()) 
		{
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("orderId", c.getString(c.getColumnIndex("orderId")));
			map1.put("creatorname", c.getString(c.getColumnIndex("creatorname")));
			map1.put("ordercreator", c.getString(c.getColumnIndex("ordercreator")));
			map1.put("content", c.getString(c.getColumnIndex("content")));
			
			if (c.getString(c.getColumnIndex("creatorAvatar")).equals("null")) {
				Loger.i("空头像");
				map1.put("headreply", R.drawable.mine);
			} else {
				creatorAvatarIdString = c.getString(c.getColumnIndex("creatorAvatar"));
				Loger.i("头像ID：" + creatorAvatarIdString);
				getPicUrlComment(creatorAvatarIdString, map1);
			}
			
			mData.add(map1);
			simpleAdapter.notifyDataSetChanged();
		}
		c.close();
		db.close();
	}
	
	public void getPicUrlComment(String imageIdString, final Map<String, Object> map1){
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					final String urlString = (String) avObjects.get(0).get(
							"url");
					
					new Thread() {
						public void run() {
							Loger.i(urlString);
							Bitmap b = returnBitMap(urlString);
							map1.put("headreply", b);
							Loger.i("把得到的头像放入map1");
							handler.sendEmptyMessage(0);
						};
					}.start();

					System.out.println(urlString);
				} else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}
	
	public Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	private class GetDataTask extends
	AsyncTask<Void, Void, List<Map<String, Object>>> 
	{
		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) 
		{
			try 
			{
				List<Map<String, Object>> list = AVCloud.callFunction(
				"getReply", null);
				return list;
			} 
			catch (AVException e) 
			{
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				if (e.getCode() == 0)
					Toast.makeText(CommentActivity.this, "无网络连接",
					Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) 
		{
			super.onPostExecute(result);
			if (result != null) 
			{
				Loger.i("state成功 : " + result);
				mData.clear();
				simpleAdapter.notifyDataSetChanged();
				if (result.size() == 0)
				{
					Loger.i("dbflag"+dbflag);
					gethistorycomment(20+dbflag);
					dbflag+=20;
				}
				else
				{
					for (Map<String, Object> map : result) 
					{
						mData.add(convertResult(map));
						simpleAdapter.notifyDataSetChanged();
						// TODO 改变逻辑
					}
					gethistorycomment(dbflag+20-result.size());
					dbflag+=20-result.size();
					for (Map<String, Object> map : result)
						insertreadedcomment(map);
				}
			}
			listView.onRefreshComplete();
		}
	}
	
	private class GetHeaderDataTask extends
	AsyncTask<Void, Void, List<Map<String, Object>>> 
	{
		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) 
		{
			try 
			{
				List<Map<String, Object>> list = AVCloud.callFunction(
				"getReply", null);
				return list;
			} 
			catch (AVException e) 
			{
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(CommentActivity.this, e.getMessage(),
				Toast.LENGTH_SHORT).show();
			}
			return null;
		}
		@Override
		protected void onPostExecute(List<Map<String, Object>> result) 
		{
			super.onPostExecute(result);
			if (result != null) 
			{
				Loger.i("下拉刷新成功 : " + result);
				mData.clear();
				simpleAdapter.notifyDataSetChanged();
				if (result.size() == 0)
					gethistorycomment(20);
				else 
				{
					for (Map<String, Object> map : result) 
					{
						mData.add(convertResult(map));
						simpleAdapter.notifyDataSetChanged();
					}
					gethistorycomment(20-result.size());
					for (Map<String, Object> map : result) 
						insertreadedcomment(map);
				}
			}
			listView.onRefreshComplete();
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
