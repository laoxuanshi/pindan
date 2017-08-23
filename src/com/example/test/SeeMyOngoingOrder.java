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
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.fragment.PinFragment;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class SeeMyOngoingOrder extends Activity{
	
	private PullToRefreshListView listView; // ����ListView���
	private List<Map<String, Object>> mData; // ������ʾ�����ݰ�װ
	private OrderAdapter simpleAdapter = null; // �������ݵ�ת������
	private ImageDownLoader mImageDownLoader;
	
	String nickNameString;
	String spotString;
	String descriptString;
	String stateString;
	String orderTypeString;
	String endTimeString;
	String genderString;
	String imageIdString;
	String portraitIdString;
	String orderIdString;
	String orderCreatorString;
	
	int page = 1;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinglistview);
		
		mImageDownLoader = new ImageDownLoader(SeeMyOngoingOrder.this);
		
		listView = (PullToRefreshListView) findViewById(R.id.list);
		
		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new OrderAdapter(SeeMyOngoingOrder.this, mData,
				R.layout.activity_ping, new String[] { "nickname", "spot",
						"description", "state", "ordertype", "endtime",
						"gender", "pic1"}, new int[] { R.id.nickname,
						R.id.spot, R.id.description, R.id.state,
						R.id.ordertype, R.id.endtime, R.id.gender, R.id.pic1});
		
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
				Loger.l();
				new GetDataTask().execute();
			}
		});

		listView.setMode(Mode.PULL_FROM_END);
		
		myongoingorder();
//		
		listView.setOnItemClickListener(new OnItemClickListenerImpl());
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("���ڽ���");
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
				ActionBar.DISPLAY_HOME_AS_UP);

		
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				simpleAdapter.notifyDataSetChanged();
			}
		}
	};
	
	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(SeeMyOngoingOrder.this, SeePindan.class);
			/* ͨ��Bundle����洢��Ҫ���ݵ����� */
			Bundle bundle = new Bundle();
			/* �ַ����ַ������������ֽ����顢�������ȵȣ������Դ� */
			bundle.putString("orderId", mData.get(position - 1).get("orderId")
					.toString());
			bundle.putString("ordercreator",
					mData.get(position - 1).get("ordercreator").toString());
			bundle.putString("orderstatus", mData.get(position - 1)
					.get("state").toString());
			Loger.i("mData:" + mData.get(position-1));
			/* ��bundle����assign��Intent */
			intent.putExtras(bundle);
			startActivity(intent);
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
	
	
   	public void myongoingorder() 
   	{
  		Map<String, Object> params = new HashMap<String, Object>();
  		params.put("page", page);
		Loger.i("page:" + page); 
		AVCloud.callFunctionInBackground("myOnGoingOrder", params,
				new FunctionCallback<List<Map<String, Object>>>() {
					public void done(List<Map<String, Object>> object,
							AVException e) {
						if (e == null) {
							Loger.i("�������ڽ���ƴ���ɹ� : " + object);
							page++;
							for (Map<String, Object> map : object) {
								mData.add(convertResult(map));
								Loger.i(mData + "");
								Loger.l();
								simpleAdapter.notifyDataSetChanged();
							}
						} else {
							Loger.d("ʧ��" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
  	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Object> convertResult(Map<String, Object> map) {
		nickNameString = map.get("nickName").toString();
		spotString = map.get("address").toString();
		descriptString = map.get("description").toString();
		stateString = map.get("status").toString();
		orderTypeString = map.get("orderType").toString();
		endTimeString = map.get("endTime").toString();
		genderString = map.get("gender").toString();
		orderIdString = map.get("orderId").toString();
		orderCreatorString = map.get("creator").toString();

		
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("nickname", nickNameString);
		map1.put("spot", spotString);
		map1.put("description", descriptString);
		map1.put("state", stateString);
		map1.put("ordertype", orderTypeString);
		
		map1.put("endtime", TimeStamp2Date(endTimeString));
		map1.put("orderId", orderIdString);
		map1.put("ordercreator", orderCreatorString);
		if (getGender(genderString)) {
			map1.put("gender", R.drawable.male);
		} else {
			map1.put("gender", R.drawable.female);
		}
		
		if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("ʵ���ƴ��")) 
		{
			map1.put("pic1", R.drawable.nopic);
			simpleAdapter.notifyDataSetChanged();
		} 
		else if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("����ƴ��")) 
		{
			map1.put("pic1", R.drawable.nopic1);
			simpleAdapter.notifyDataSetChanged();
		} 
		else 
		{
			imageIdString = ((ArrayList<String>) map.get("images")).get(0);
			System.out.println("ͼƬID��" + imageIdString);
			getPicUrl(imageIdString, map1, 1);
		}
		
		if(map.get("avatar") == null){
			map1.put("portrait", R.drawable.mine);
		}else {
			Loger.i("map.get(avatar)��:" + map.get("avatar"));
			portraitIdString = map.get("avatar").toString();
			System.out.println("ͷ��ID��"+portraitIdString);
			getPicUrl(portraitIdString, map1, 2);
		}
		return map1;
	}
	
	// ��unixʱ���ת��Ϊ��ͨ����
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	// �ж��Ա�
	public boolean getGender(String genderString1) {
		if (genderString1.equals("M")) {
			return true;
		}
		return false;
	}

	// ͨ��ͼƬ��objectId�����õ�url
	public void getPicUrl(String imageIdString, final Map<String, Object> map1, final int imageIdType) {
		if (imageIdType == 2) {
			Loger.i("ͷ��url��ȡ��");
		}
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
					Loger.i(imageIdType + avObjects.get(0).toString());
					String urlString = (String) avObjects.get(0).get(
							"url");

					if(imageIdType == 1){
						urlString = (String) AVFile.withAVObject(avObjects.get(0)).getThumbnailUrl(false, PinFragment.width*5/8, PinFragment.width*5/8);
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (bitmap != null) {
											Loger.l();
											map1.put("pic1", bitmap);
											simpleAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							Loger.l();
							map1.put("pic1", bitmap);
							simpleAdapter.notifyDataSetChanged();
						}
					}else {
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										Loger.l();
										if (bitmap != null) {
											Loger.l();
											map1.put("portrait", bitmap);
											simpleAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							Loger.l();
							map1.put("portrait", bitmap);
							simpleAdapter.notifyDataSetChanged();
						}
					}
//					new Thread() {
//						public void run() {
//							Loger.i(urlString);
//							Bitmap b = returnBitMap(urlString);
//							if(imageIdType == 1){
//								map1.put("pic1", b);
//							}else{
//								map1.put("portrait", b);
//							}
//							Loger.i(b + "");
//							handler.sendEmptyMessage(0);
//						};
//					}.start();

					Loger.i(urlString);
				} else {
					Log.d("ʧ��", "��ѯ����: " + e.getMessage());
				}
			}
		});
	}
	
	private class GetDataTask extends
	AsyncTask<Void, Void, List<Map<String, Object>>> 
	{
		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) 
		{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("page", page);
			Loger.i("ˢ��ʱ��page��" + page);
			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"myOnGoingOrder", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("ʧ��:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			return null;
		}



		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				Loger.i("�鿴�ҵ�ƴ���ɹ� : " + result);
				page++;
				for (Map<String, Object> map : result) {
					mData.add(convertResult(map));
				}
				simpleAdapter.notifyDataSetChanged();
			}
			listView.onRefreshComplete();
		}
	}

}
