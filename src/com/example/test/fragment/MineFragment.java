package com.example.test.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.PushService;
import com.example.test.*;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

public class MineFragment extends Fragment {
	RelativeLayout in, seemyorder, seemyongoingorder, seemyParticipate,
			seemycollect,shezhi;
	Button dl, zc;
	RelativeLayout bt1, bt2, bt3, bt4, bt5, bt6, bt7;
	String portraitIdString;
	ImageView portraitImageView;
	Bitmap bitmap;
	static View view;
	private ImageDownLoader mImageDownLoader;

	public static MineFragment newInstance(int index) {
		MineFragment f = new MineFragment();

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
		mImageDownLoader = new ImageDownLoader(getActivity()
				.getApplicationContext());
		view = inflater.inflate(R.layout.activity_mine, container, false);
		dl = (Button) view.findViewById(R.id.buttondldl);
		zc = (Button) view.findViewById(R.id.buttonzczc);
		in = (RelativeLayout) view.findViewById(R.id.rebasicinformation);
		seemycollect = (RelativeLayout) view.findViewById(R.id.seemycollection);
		seemyorder = (RelativeLayout) view.findViewById(R.id.seemyorder);
		seemyongoingorder = (RelativeLayout) view
				.findViewById(R.id.seemyongoingorder);
		seemyParticipate = (RelativeLayout) view
				.findViewById(R.id.seemyParticipate);
		shezhi = (RelativeLayout) view.findViewById(R.id.shezhi_layout);
		portraitImageView = (ImageView) view.findViewById(R.id.imagetouxiang);

		
		return view;

	}

	public void getPicUrl(String imageIdString) {
		AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
		query.whereEqualTo("objectId", imageIdString);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("成功", "查询到" + avObjects.size() + " 条头像url");
					if (avObjects.size() != 0) {
						final String urlString = (String) avObjects.get(0).get(
								"url");
						Bitmap bitmap = mImageDownLoader.downloadImage(
								urlString, new onImageLoaderListener() {
									@Override
									public void onImageLoader(Bitmap bitmap,
											String url) {
										if (bitmap != null) {
											portraitImageView
													.setImageBitmap(bitmap);
										}
									}
								});
						if (bitmap != null) {
							portraitImageView.setImageBitmap(bitmap);
						}
					}
				} else {
					Log.d("失败", "查询头像url错误: " + e.getMessage());
				}
			}
		});
	}

	@Override
	public void onResume() {
		if (AVUser.getCurrentUser() == null) {
			dl.setVisibility(View.VISIBLE);
			zc.setVisibility(View.VISIBLE);
			portraitImageView.setImageResource(R.drawable.mine);
			dl.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), login.class);
					startActivity(intent);
				}
			});

			zc.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), signup1.class);
					startActivity(intent);
				}
			});

			in.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});

			seemycollect.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});
			seemyorder.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});
			seemyongoingorder.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});
			seemyParticipate.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});
			
			shezhi.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			});
		} else {

			dl.setVisibility(View.INVISIBLE);
			zc.setVisibility(View.INVISIBLE);
			String userIdString;
			userIdString = AVUser.getCurrentUser().getObjectId();
			AVQuery<AVObject> query = new AVQuery<AVObject>("UserProfile");
			query.whereEqualTo("userId", userIdString);
			query.findInBackground(new FindCallback<AVObject>() {
				public void done(List<AVObject> avObjects, AVException e) {
					if (e == null) {
						Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
						portraitIdString = (String) avObjects.get(0).get(
								"avatar");
						if (portraitIdString == null)
							portraitImageView.setImageResource(R.drawable.mine);
						else
							getPicUrl(portraitIdString);
					} else {
						Log.d("失败", "查询头像id错误: " + e.getMessage());
					}
				}
			});

			in.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), myxinxi.class);
					startActivity(intent);
				}
			});
			seemyorder.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), SeeMyOrder.class);
					startActivity(intent);
				}
			});

			seemyongoingorder.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							SeeMyOngoingOrder.class);
					startActivity(intent);
				}
			});

			seemyParticipate.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							SeeMyParticipate.class);
					startActivity(intent);
				}
			});

			seemycollect.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							SeeMyCollection.class);
					startActivity(intent);
				}
			});

			
			shezhi.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							shezhi.class);
					startActivity(intent);
				}
			});
		}
		super.onResume();
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}