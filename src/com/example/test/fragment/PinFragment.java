package com.example.test.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.ImageDownLoader;
import com.example.test.MyApplication;
import com.example.test.OrderAdapter;
import com.example.test.R;
import com.example.test.SeePindan;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 功能描述：Pinfragment页面
 */
public class PinFragment extends Fragment {
	public String userid;
	public String[] results;
	private PullToRefreshListView listView; // 定义ListView组件
	private List<Map<String, Object>> mData; // 定义显示的内容包装
	private OrderAdapter simpleAdapter = null; // 进行数据的转换操作
	static int page = 1;
	static long currentTime;

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
	
	public static int width;
	public static int height;

	/**
	 * Image 下载器
	 */
	private ImageDownLoader mImageDownLoader;
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static PinFragment newInstance(int index) {
		PinFragment f = new PinFragment();
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//获取屏幕宽和高
		WindowManager wm = getActivity().getWindowManager();
		width = wm.getDefaultDisplay().getWidth();
	    height = wm.getDefaultDisplay().getHeight();
	    Loger.i("width:" + width + "\nheight" + height);
	     
		//TODO 每隔十分钟一次位置汇报		
		if(AVUser.getCurrentUser()!=null)
			MyApplication.initLocationClient();
		
		mImageDownLoader = new ImageDownLoader(getActivity().getApplicationContext());
		
		page = 1;
		View view = inflater.inflate(R.layout.pinglistview, container, false);
		listView = (PullToRefreshListView) view.findViewById(R.id.list);
		
		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new OrderAdapter(getActivity(), mData,
				R.layout.activity_ping, new String[] { "nickname", "spot",
						"description", "state", "ordertype", "endtime",
						"gender", "pic1" }, new int[] {
						R.id.nickname, R.id.spot, R.id.description, R.id.state,
						R.id.ordertype, R.id.endtime, R.id.gender, R.id.pic1});

		simpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
//					if(data == null){
//						iv.setVisibility(View.GONE);
//					}
					return true;
				}
				return false;
			}
		});

		listView.setAdapter(simpleAdapter);

		this.listView.setOnItemClickListener(new OnItemClickListenerImpl());
		
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (refreshView.isHeaderShown()) {
					new GetHeaderDataTask().execute();
				} else {
					// 得到上一次滚动条的位置，让加载后的页面停在上一次的位置，便于用户操作
					//y = mListItems.size();
					new GetBottomDataTask().execute();
					
				}
			}
		});

		listView.setMode(Mode.BOTH);  
		// 下拉刷新时的提示文本设置
		listView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("下拉刷新");
		listView.getLoadingLayoutProxy(true, false).setPullLabel("");
		listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新");
		listView.getLoadingLayoutProxy(true, false).setReleaseLabel("放开以刷新");
		// 上拉加载更多时的提示文本设置
		listView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上拉加载");
		listView.getLoadingLayoutProxy(false, true).setPullLabel("");
		listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		listView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");
		
		request();

		return view;
	}
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				simpleAdapter.notifyDataSetChanged();
			} else {
				page = msg.what;
			}
		}
	};

	private class OnItemClickListenerImpl implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(getActivity(), SeePindan.class);
			/* 通过Bundle对象存储需要传递的数据 */
			Bundle bundle = new Bundle();
			/* 字符、字符串、布尔、字节数组、浮点数等等，都可以传 */
			bundle.putString("orderId", mData.get(position - 1).get("orderId")
					.toString());
			bundle.putString("ordercreator",
					mData.get(position - 1).get("ordercreator").toString());
//			Loger.i("imgid", mData.get(position - 1).get("pic1").toString());

			/* 把bundle对象assign给Intent */
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void request() {
		currentTime = System.currentTimeMillis()/1000;
		// 获取推荐列表
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(AVUser.getCurrentUser()!=null)
    	{
			userid = AVUser.getCurrentUser().getObjectId();
			parameters.put("userId", userid); 
    	}
		parameters.put("curCity", "北京"); // 暂时试试
		parameters.put("page", page);
		AVCloud.callFunctionInBackground("getRecommend", parameters,
				new FunctionCallback<List<Map<String, Object>>>() {
					public void done(List<Map<String, Object>> object,
							AVException e) {
						if (e == null) {
							page++;
							handler.sendEmptyMessage(page);
							for (Map<String, Object> map : object) {
								mData.add(convertResult(map));
							}
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							if(e.getCode()==0)
								Toast.makeText(getActivity(), "无网络连接",Toast.LENGTH_SHORT).show();
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

//		boolean isCreator = (Boolean) map.get("isCreator");
//		boolean isParticipants = (Boolean) map.get("isParticipants");

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("nickname", nickNameString);
		map1.put("spot", spotString);
		map1.put("description", descriptString);
		map1.put("state", stateString);
		map1.put("ordertype", " "+orderTypeString+" ");

		map1.put("endtime", TimeStamp2Date(endTimeString));
		map1.put("orderId", orderIdString);
		map1.put("ordercreator", orderCreatorString);
		if (getGender(genderString)) {
			map1.put("gender", R.drawable.male);
		} else {
			map1.put("gender", R.drawable.female);
		}
		//拼单大图
		if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("实体店拼单")) 
		{
			map1.put("pic1", R.drawable.nopic);
			simpleAdapter.notifyDataSetChanged();
		} 
		else if (((ArrayList<String>) map.get("images")).size()==0&&map.get("orderType").toString().equals("网购拼单")) 
		{
			map1.put("pic1", R.drawable.nopic1);
			simpleAdapter.notifyDataSetChanged();
		} 
		else 
		{
			imageIdString = ((ArrayList<String>) map.get("images")).get(0);
			System.out.println("图片ID：" + imageIdString);
			getPicUrl(imageIdString, map1, 1);
		}


		if (map.get("avatar") == null) {
			map1.put("portrait", R.drawable.mine);
		} else {
			portraitIdString = map.get("avatar").toString();
			System.out.println("头像ID：" + portraitIdString);
			getPicUrl(portraitIdString, map1, 2);
		}

//		map1.put("isCreator", isCreator);
//		map1.put("isParticipants", isParticipants);

		return map1;
	}

	// 将unix时间戳转换为普通日期
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	// 判断性别
	public boolean getGender(String genderString1) {
		if (genderString1.equals("M")) {
			return true;
		}
		return false;
	}

	// 通过图片的objectId搜索得到url
	public void getPicUrl(String imageIdString, final Map<String, Object> map1,
			final int imageIdType) {
		if (imageIdType == 2) {
		}
		
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
					String urlString = (String) avObjects.get(0).get(
							"url");
					if(imageIdType == 1)
					{
						urlString = (String) AVFile.withAVObject(avObjects.get(0)).getThumbnailUrl(false, width*4/8, width*4/8);
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										if (bitmap != null) {
											map1.put("pic1", bitmap);
											simpleAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							map1.put("pic1", bitmap);
							simpleAdapter.notifyDataSetChanged();
						}
					}else {
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										if (bitmap != null) {
											map1.put("portrait", bitmap);
											simpleAdapter.notifyDataSetChanged();
										}
									}
								});
						if (bitmap != null) {
							map1.put("portrait", bitmap);
							simpleAdapter.notifyDataSetChanged();
						}
					}
//					handler.sendEmptyMessage(0);
//					simpleAdapter.notifyDataSetChanged();
//					new Thread() {
//						public void run() {
//							Bitmap b = returnBitMap(urlString);
//							if (imageIdType == 1) {
//								map1.put("pic1", b);
//							} else {
//								map1.put("portrait", b);
//							}
//							handler.sendEmptyMessage(0);
//						};
//					}.start();

				} 
				else 
				{
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private class GetBottomDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			if(AVUser.getCurrentUser()!=null)
	    	{
				userid = AVUser.getCurrentUser().getObjectId();
				parameters.put("userId", userid); 
	    	}
			parameters.put("curCity", "北京"); // 暂时试试
			parameters.put("page", page);
			parameters.put("queryTime", currentTime);
			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"getRecommend", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
//				Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				page++;
				handler.sendEmptyMessage(page);
				for (Map<String, Object> map : result) {
					mData.add(convertResult(map));
				}
				simpleAdapter.notifyDataSetChanged();
			}
			listView.onRefreshComplete();
		}
	}
	
	private class GetHeaderDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			page = 1;
			currentTime = System.currentTimeMillis()/1000;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", "54584a2ae4b0b14db2a1351e"); // 暂时试试
			parameters.put("curCity", "北京"); // 暂时试试
			parameters.put("page", page);
			
			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"getRecommend", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
//				Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				mData.clear();
				page++;
				handler.sendEmptyMessage(page);
				for (Map<String, Object> map : result) {
					mData.add(convertResult(map));
				}
				simpleAdapter.notifyDataSetChanged();
			}
			listView.onRefreshComplete();
		}
	}
	
	@Override
	public void onResume() {
		Loger.i("onResume~~~~~~~~~~~~~~~~~~~`");
		// TODO Auto-generated method stub
		new GetHeaderDataTask().execute();
		super.onResume();
	}

}
