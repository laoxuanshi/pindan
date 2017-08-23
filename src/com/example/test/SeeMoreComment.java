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
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.util.Loger;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

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

public class SeeMoreComment extends Activity {

	private PullToRefreshListView listView; // 定义ListView组件
	private List<Map<String, Object>> mData; // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作

	String orderIdString;
	String orderCreatorString;

	String toNameString;
	String creator;
	String commentId;
	String creatorNameString;
	String commentContentString;
	static String huifurenid=null;

//	static String orderStatusString;
//	static boolean isCreator;
////	static boolean isParticipants;

	int chooseItem = -1;
	String arg0[] = { "回复", "删除" };
	String arg1[] = { "回复" };
	int page = 1;
	private static long currentTime;
	private ImageDownLoader mImageDownLoader;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.see_more_comment);

		mImageDownLoader = new ImageDownLoader(SeeMoreComment.this);
		Bundle bundle = this.getIntent().getExtras();
		orderIdString = bundle.getString("orderId");
		orderCreatorString = bundle.getString("ordercreator");
//		orderStatusString = bundle.getString("orderstatus");
//		isCreator = bundle.getBoolean("isCreator");
//		isParticipants = bundle.getBoolean("isParticipants");

		listView = (PullToRefreshListView) findViewById(R.id.list_comment2);

		mData = new ArrayList<Map<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, mData,
				R.layout.activity_comment, new String[] { "creatorname",
						"commentcontent", "headcomment" }, new int[] {
						R.id.nickname_comment, R.id.comment_content,
						R.id.head_comment });

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

		requestComment();

		listView.setOnItemClickListener(new OnItemClickListenerImpl());

		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle("拼单评论");
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
				long id) 
		{
			String userIdString;
			if(AVUser.getCurrentUser()==null)
				Toast.makeText(getApplicationContext(),"请先登录", Toast.LENGTH_SHORT).show();
			else
			{
				userIdString = AVUser.getCurrentUser().getObjectId();
				// 得到当前用户的id?以及判断是否为拼单发起者?
				System.out.println("得到的ordercreator2:" + orderCreatorString);
				if (mData.get(position - 1).get("creator").toString().trim()
					.equalsIgnoreCase(userIdString)
					|| orderCreatorString.equals(userIdString)) {
				dialog6(mData.get(position - 1).get("creatorname").toString(),
						mData.get(position - 1).get("creator").toString(),
						mData.get(position - 1).get("commentId").toString(),
						mData.get(position- 1).get("commentcontent").toString());
				} 
				else
					dialog7(mData.get(position - 1).get("creatorname").toString(),
						mData.get(position - 1).get("creator").toString(),
						mData.get(position- 1).get("commentcontent").toString());
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent1 = new Intent(SeeMoreComment.this,
					SeePindan.class);
			Bundle bundle1 = new Bundle();

			bundle1.putString("orderId", orderIdString);
			bundle1.putString("ordercreator", orderCreatorString);
//			bundle1.putString("orderstatus", orderStatusString);
//			bundle1.putBoolean("isCreator", isCreator);
//			bundle1.putBoolean("isParticipants", isParticipants);
			intent1.putExtras(bundle1);

			startActivity(intent1);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void requestComment() {
		currentTime = System.currentTimeMillis()/1000;
		Loger.i(currentTime+"时间戳");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderIdString);
		params.put("page", page);
		Loger.i("page:" + page);
		Loger.i(params + "");

		AVCloud.callFunctionInBackground("getComment", params,
				new FunctionCallback<List<Map<String, Object>>>() {
					public void done(List<Map<String, Object>> object,
							AVException e) {
						if (e == null) {
							Loger.i("加载评论成功 : " + object);
							page++;
							for (Map<String, Object> map : object) {
								mData.add(convertResult(map));
								Loger.i(mData + "");
								Loger.l();
								simpleAdapter.notifyDataSetChanged();
							}
						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
				});
	}

	public void rmComment(final String comment_id) {
		Loger.l();
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("commentId", comment_id); // 接收评论的id

		AVCloud.callFunctionInBackground("removeComment", params2,
				new FunctionCallback<String>() {
					public void done(String object, AVException e) {
						if (e == null) {
							Loger.i("删除评论成功 : " + object);

						} else {
							Loger.d("失败" + e.getCode() + ", " + e.getMessage());
							Toast.makeText(getApplicationContext(),
									e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					};
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
	
	public void getPicUrlComment(String imageIdString, final Map<String, Object> map1){
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					final String urlString = (String) avObjects.get(0).get(
							"url");
					
					Bitmap bitmap = mImageDownLoader.downloadImage(urlString,
							new onImageLoaderListener() {
								@Override
								public void onImageLoader(Bitmap bitmap,
										String url) {
									Loger.l();
									if (bitmap != null) {
										Loger.l();
										map1.put("headcomment", bitmap);
										simpleAdapter.notifyDataSetChanged();
									}
								}
							});
					if (bitmap != null) {
						Loger.l();
						map1.put("headcomment", bitmap);
						simpleAdapter.notifyDataSetChanged();
					}
//					new Thread() {
//						public void run() {
//							Loger.i(urlString);
//							Bitmap b = returnBitMap(urlString);
//							map1.put("headcomment", b);
//							Loger.i("把得到的头像放入map1");
//							handler.sendEmptyMessage(0);
//						};
//					}.start();

					System.out.println(urlString);
				} else {
					Log.d("失败", "查询错误: " + e.getMessage());
				}
			}
		});
	}

	public Map<String, Object> convertResult(Map<String, Object> map) {

		creatorNameString = map.get("creatorName").toString();
		commentContentString = map.get("content").toString();

		creator = map.get("creator").toString();
		commentId = map.get("commentId").toString();

		if(map.size()==8)
		{	
			huifurenid=map.get("to").toString();
			toNameString=map.get("toName").toString();
			commentContentString = "回复"+toNameString+":"+map.get("content").toString();
		}
		else 
			commentContentString = map.get("content").toString();
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("creatorname", creatorNameString);
		map1.put("commentcontent", commentContentString);
		
		if (map.get("creatorAvatar") == null) {
			Loger.i("空头像");
			map1.put("headcomment", R.drawable.mine);
		} else {
			String commentPortraitIdString;
			commentPortraitIdString = map.get("creatorAvatar").toString();
			Loger.i("评论头像ID：" + commentPortraitIdString);
			getPicUrlComment(commentPortraitIdString, map1);
		}

		map1.put("creator", creator);
		map1.put("commentId", commentId);

		return map1;
	}

	private void dialog6(final String creator_name, final String creator_id,
			final String comment_id,final String commentcontent) {

		new AlertDialog.Builder(this).setTitle("请选择操作")
				.setIcon(android.R.drawable.ic_media_play)
				.setItems(arg0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							dialog.dismiss();
							Intent intent = new Intent(SeeMoreComment.this,
									huifu.class);

							Bundle bundle = new Bundle();

							bundle.putString("creatorname", creator_name);
							bundle.putString("commentcreatorid", creator_id);
							bundle.putString("orderId", orderIdString);
							bundle.putString("ordercreator", orderCreatorString);
							bundle.putString("commentcontent", commentcontent);
//							bundle.putString("orderstatus", orderStatusString);
//							bundle.putBoolean("isCreator", isCreator);
//							bundle.putBoolean("isParticipants", isParticipants);
							intent.putExtras(bundle);

							startActivity(intent);
//							finish();
							chooseItem = -1;
							break;
						case 1:
							dialog.dismiss();
							// 接收评论的id
							Loger.l();
							rmComment(comment_id);
							// 重新刷新
							Intent intent1 = new Intent(SeeMoreComment.this,
									SeeMoreComment.class);
							Bundle bundle1 = new Bundle();

							bundle1.putString("creatorname", creator_name);
							bundle1.putString("creator", creator_id);
							bundle1.putString("orderId", orderIdString);
							bundle1.putString("ordercreator", orderCreatorString);
//							bundle1.putString("orderstatus", orderStatusString);
//							bundle1.putBoolean("isCreator", isCreator);
//							bundle1.putBoolean("isParticipants", isParticipants);
							intent1.putExtras(bundle1);

							startActivity(intent1);
							finish();
							chooseItem = -1;
							break;
						default:
							break;

						}
					}
				}).show();

	}

	private void dialog7(final String creator_name, final String creator_id,final String commentcontent) {

		new AlertDialog.Builder(this).setTitle("请选择操作")
				.setIcon(android.R.drawable.ic_media_play)
				.setItems(arg1, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
						case 0:
							dialog.dismiss();
							Intent intent = new Intent(SeeMoreComment.this,
									huifu.class);
							Bundle bundle = new Bundle();

							bundle.putString("creatorname", creator_name);
							bundle.putString("commentcreatorid", creator_id);
							bundle.putString("ordercreator", orderCreatorString);
							bundle.putString("orderId", orderIdString);
							bundle.putString("commentcontent", commentcontent);
//							bundle.putString("orderstatus", orderStatusString);
//							bundle.putBoolean("isCreator", isCreator);
//							bundle.putBoolean("isParticipants", isParticipants);
							intent.putExtras(bundle);

							startActivity(intent);
//							finish();
							chooseItem = -1;
							break;

						default:
							break;

						}
					}
				}).show();

	}

	private class GetDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", orderIdString);// 暂时用这个orderId，应该是orderIdString
			parameters.put("page", page);
			parameters.put("queryTime", currentTime);
			Loger.i("page:" + page);
			Loger.i("params:" + parameters);
			try {
				List<Map<String, Object>> list = AVCloud.callFunction(
						"getComment", parameters);
				return list;
			} catch (AVException e) {
				e.printStackTrace();
				Loger.d("失败:" + e.getCode() + ", " + e.getMessage());
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null) {
				Loger.i("加载评论成功 : " + result);
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
