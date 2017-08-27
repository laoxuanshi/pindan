package com.example.test.SearchFragment;

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
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StateActivity extends ListActivity{
	public String userid;
	private PullToRefreshListView listView; // ����ListView���
	private List<Map<String, Object>> mData; // ������ʾ�����ݰ�װ
	private SimpleAdapter simpleAdapter = null; // �������ݵ�ת������
	int dbflag=0;
	String orderIdString;
	String statecontent1;
	static long currentTime;
	DBState dbstate;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.state_listview);
		
		dbstate = new DBState(this, "statehistory.db", 1);
		
		listView = (PullToRefreshListView) findViewById(R.id.list_state);
		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, mData,
				R.layout.activity_state, new String[] {"content"}, new int[] {R.id.statecontent});
		setListAdapter(simpleAdapter);
		
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
		
		getStatechange();
		
		listView.setOnItemClickListener(new OnItemClickListenerImpl());

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("                   ƴ��״̬�仯");
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
			query.whereEqualTo("objectId", mData.get(position - 1).get("orderId").toString());
			query.findInBackground(new FindCallback<AVObject>() {
				public void done(List<AVObject> avObjects, AVException e) {
					if (e == null) 
					{
						Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
						Intent intent = new Intent(StateActivity.this, SeePindan.class);
						/* ͨ��Bundle����洢��Ҫ���ݵ����� */
						Bundle bundle = new Bundle();
						/* �ַ����ַ������������ֽ����顢�������ȵȣ������Դ� */
						bundle.putString("orderId", mData.get(position - 1).get("orderId")
								.toString());
						bundle.putString("ordercreator",(String) avObjects.get(0).get("creator"));
						/* ��bundle����assign��Intent */
						intent.putExtras(bundle);
						startActivity(intent);
					} 
					else 
					{
						Toast.makeText(getApplicationContext(),"��ƴ����ʧЧ", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			

		}
	}
	
	public void getStatechange() 
	{
		currentTime = System.currentTimeMillis() / 1000;
		AVCloud.callFunctionInBackground("getStateNotice", null, 
				new FunctionCallback<List<Map<String, Object>>>() {
			@Override
			public void done(List<Map<String, Object>> object, AVException e) {
				if(e == null){
					if(object != null){
						Loger.i(object + "");
						if(object.size()==0)
						{
							Toast.makeText(getApplicationContext(),"��ǰû��ƴ��״̬�仯����Ϣ", Toast.LENGTH_SHORT).show();
							gethistorystate(20);
							dbflag=20;
						}
						else
						{
							for (Map<String, Object> map : object) 
							{
								mData.add(convertResult(map));
								simpleAdapter.notifyDataSetChanged();
							}
							gethistorystate(20-object.size());
							dbflag=20-object.size();
							for (Map<String, Object> map : object) 
								insertreadedstate(map);
						}
					}
				}else {
					Loger.d(e.getMessage());
					if(e.getCode()==0)
						Toast.makeText(StateActivity.this, "����������",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public Map<String, Object> convertResult(Map<String, Object> map) {

		orderIdString = map.get("orderId").toString();
		statecontent1 = map.get("content").toString();
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("orderId", orderIdString);
		map1.put("content", statecontent1);
		
		return map1;
	}
	
	// �����ݱ���������Ѷ�ƴ��
	public int insertreadedstate(Map<String, Object> map) 
	{
		if (map == null || map.size() == 0) {
			return 0;
		}
		SQLiteDatabase db = dbstate.getWritableDatabase();
		Loger.i("д���ݿ�·��" + db.getPath());
		db.beginTransaction();
		try {
			Loger.i("��ʼ��������");
			ContentValues cv = new ContentValues();
			userid = AVUser.getCurrentUser().getObjectId();
			cv.put("userId", userid);
			cv.put("orderId", map.get("orderId").toString());
			cv.put("content", map.get("content").toString());
			cv.put("timestamp", System.currentTimeMillis());
			long re = db.insert("state", null, cv);
			Loger.i("������:" + re);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return 0;
	}
	
	public void gethistorystate(int size) 
	{
		SQLiteDatabase db = dbstate.getReadableDatabase();
		Loger.i("�����ݿ�·��" + db.getPath());
		assert db != null;
		userid = AVUser.getCurrentUser().getObjectId();
		// ����groupId��ѯ
		Cursor c = db
				.query("state", null, "userId=?", new String[] { userid },
						null, null, "timestamp" + " desc", size+""); // Ӧ�û���Ҫ�����
		Loger.i("��¼��Ŀ" + c.getCount());

		while (c.moveToNext()) 
		{
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("content", c.getString(c.getColumnIndex("content")));
			map1.put("orderId", c.getString(c.getColumnIndex("orderId")));
			mData.add(map1);
			simpleAdapter.notifyDataSetChanged();
		}
		c.close();
		db.close();
	}

	private class GetDataTask extends
	AsyncTask<Void, Void, List<Map<String, Object>>> 
	{
		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) 
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("queryTime", currentTime);
			Loger.i("params:" + parameters);
			try 
			{
				List<Map<String, Object>> list = AVCloud.callFunction(
				"getStateNotice", null);
				return list;
			} 
			catch (AVException e) 
			{
				e.printStackTrace();
				Loger.d("ʧ��:" + e.getCode() + ", " + e.getMessage());
				if (e.getCode() == 0)
					Toast.makeText(StateActivity.this, "����������",
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
				Loger.i("state�ɹ� : " + result);
				mData.clear();
				simpleAdapter.notifyDataSetChanged();
				if (result.size() == 0)
				{
					Loger.i("dbflag"+dbflag);
					gethistorystate(20+dbflag);
					dbflag+=20;
				}
				else
				{
					for (Map<String, Object> map : result) 
					{
						mData.add(convertResult(map));
						simpleAdapter.notifyDataSetChanged();
						// TODO �ı��߼�
					}
					gethistorystate(dbflag+20-result.size());
					dbflag+=20-result.size();
					for (Map<String, Object> map : result)
						insertreadedstate(map);
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
			currentTime = System.currentTimeMillis() / 1000;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("queryTime", currentTime);
			Loger.i("params:" + parameters);
			try 
			{
				List<Map<String, Object>> list = AVCloud.callFunction(
				"getStateNotice", null);
				return list;
			} 
			catch (AVException e) 
			{
				e.printStackTrace();
				Loger.d("ʧ��:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(StateActivity.this, e.getMessage(),
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
				Loger.i("����ˢ�³ɹ� : " + result);
				mData.clear();
				simpleAdapter.notifyDataSetChanged();
				if (result.size() == 0)
					gethistorystate(20);
				else 
				{
					for (Map<String, Object> map : result) 
					{
						mData.add(convertResult(map));
						simpleAdapter.notifyDataSetChanged();
					}
					gethistorystate(20-result.size());
					for (Map<String, Object> map : result) 
						insertreadedstate(map);
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
