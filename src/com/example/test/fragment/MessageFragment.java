package com.example.test.fragment;

import java.util.HashMap;

import android.R.color;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FunctionCallback;
import com.example.test.CommentActivity;
import com.example.test.PushActivity;
import com.example.test.R;
import com.example.test.StateActivity;
import com.example.test.util.Loger;

public class MessageFragment extends Fragment {

	int pushNum;
	int commentNum;
	int stateNum;
	private TextView pushNumTextView;
	private TextView commentNumTextView;
	private TextView stateNumTextView;

	private RelativeLayout pushRelativeLayout;
	private RelativeLayout commentRelativeLayout;
	private RelativeLayout statuschangeRelativeLayout;
	private RelativeLayout chatRelativeLayout;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static MessageFragment newInstance(int index) {
		MessageFragment f = new MessageFragment();

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
		View view = inflater.inflate(R.layout.activity_message, container,
				false);

		pushRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.push_RelativeLayout);
		pushRelativeLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					pushRelativeLayout.setBackgroundColor(Color.rgb(230, 230,
							230));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					pushRelativeLayout.setBackgroundColor(Color.rgb(240, 240,
							240));// f0f0f0
				}
				return false;
			}
		});
		pushRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AVUser.getCurrentUser() != null) {
					Intent intent = new Intent(getActivity(),
							PushActivity.class);
					startActivity(intent);
				} else
					Toast.makeText(getActivity(), "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT)
							.show();
			}

		});

		commentRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.comment_RelativeLayout);
		commentRelativeLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					commentRelativeLayout.setBackgroundColor(Color.rgb(230,
							230, 230));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					commentRelativeLayout.setBackgroundColor(Color.rgb(240,
							240, 240));// f0f0f0
				}
				return false;
			}
		});
		commentRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AVUser.getCurrentUser() != null) {
					Loger.i("µã»÷ÆÀÂÛ°´Å¥");
					Intent intent = new Intent(getActivity(),
							CommentActivity.class);
					startActivity(intent);
				} else
					Toast.makeText(getActivity(), "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT)
							.show();
			}

		});
		
		statuschangeRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.statuschange_RelativeLayout);
		statuschangeRelativeLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					statuschangeRelativeLayout.setBackgroundColor(Color.rgb(230,
							230, 230));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					statuschangeRelativeLayout.setBackgroundColor(Color.rgb(240,
							240, 240));// f0f0f0
				}
				return false;
			}
		});
		statuschangeRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (AVUser.getCurrentUser() != null) {
					Intent intent = new Intent(getActivity(),
							StateActivity.class);
					startActivity(intent);
				} else
					Toast.makeText(getActivity(), "ÇëÏÈµÇÂ¼", Toast.LENGTH_SHORT)
							.show();
			}

		});
		
//		chatRelativeLayout = (RelativeLayout)view.findViewById(R.id.chat_RelativeLayout);
//		chatRelativeLayout.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					chatRelativeLayout.setBackgroundColor(Color.rgb(230, 230, 230));
//                }else if(event.getAction() == MotionEvent.ACTION_UP){    
//                	chatRelativeLayout.setBackgroundColor(Color.rgb(240, 240, 240));//f0f0f0    
//                }   
//				return false;
//			}
//		});
		
		pushNumTextView = (TextView)view.findViewById(R.id.pushNum);
		commentNumTextView = (TextView)view.findViewById(R.id.commentNum);
		stateNumTextView = (TextView)view.findViewById(R.id.stateNum);
		
		pushNumTextView.setVisibility(View.GONE);
		commentNumTextView.setVisibility(View.GONE);
		stateNumTextView.setVisibility(View.GONE);
		getMsgNum();
		
		return view;
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		getMsgNum();
		super.onResume();
	}
	
	public void getMsgNum() {
		AVCloud.callFunctionInBackground("getMessageNum", null, 
				new FunctionCallback<HashMap<String, Object>>() {
			@Override
			public void done(HashMap<String, Object> object, AVException e) {
				if(e == null){
					if(object != null){
						pushNum = (Integer) object.get("pushNum");
						if(pushNum == 0){
							pushNumTextView.setVisibility(View.GONE);
						}else {
							pushNumTextView.setVisibility(View.VISIBLE);
							pushNumTextView.setText(pushNum + "");
						}
						
						commentNum = (Integer) object.get("commentNum");
						if(commentNum == 0){
							commentNumTextView.setVisibility(View.GONE);
						}else {
							commentNumTextView.setVisibility(View.VISIBLE);
							commentNumTextView.setText(commentNum + "");
						}
						
						stateNum = (Integer) object.get("stateNum");
						if(stateNum == 0){
							stateNumTextView.setVisibility(View.GONE);
						}else {
							stateNumTextView.setVisibility(View.VISIBLE);
							stateNumTextView.setText(stateNum + "");
						}
						
					}
				}else {
					Loger.d(e.getMessage());
				}
			}
		});
	}
	
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