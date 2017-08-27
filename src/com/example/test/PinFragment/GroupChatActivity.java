package com.example.test.PinFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.*;
import com.avos.avoscloud.AVHistoryMessageQuery.HistoryMessageCallback;
import com.example.test.ChatDemoMessage;
import com.example.test.ChatDemoMessage.MessageType;
import com.example.test.util.Loger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.test.ChatDemoMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupChatActivity extends Activity implements OnClickListener,
		MessageListener {
	private String orderIdString;
	private String orderCreatorString;
	private String groupId;
	private ImageButton sendBtn;
	private Button back;
	private EditText composeZone;
	String currentName;

	ListView chatList;
	ChatDataAdapter adapter;
	List<ChatDemoMessage> messages = new LinkedList<ChatDemoMessage>();
	Group group;
	String userId;

	// ���ݱ�һЩ�ֶ�
	public static final String MESSAGES = "messages6";
	public static final String GROUPID = "groupId";
	public static final String FROM_PEER_NAME = "fromname";
	public static final String TIMESTAMP = "timestamp";
	public static final String CONTENT = "content";
	long unix;
	DBHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.chat);
		Bundle bundle = this.getIntent().getExtras();
		Loger.i("bundle:" + bundle);
		groupId = bundle.getString("groupId");
		Loger.i("groupId:" + groupId);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
//		actionBar.setTitle("                         ����");
//		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP,
//				ActionBar.DISPLAY_HOME_AS_UP);

		chatList = (ListView) this.findViewById(R.id.chat_list);
		adapter = new ChatDataAdapter(this, messages);
		chatList.setAdapter(adapter);
		sendBtn = (ImageButton) this.findViewById(R.id.sendChatMsgBtn);
		back = (Button) this.findViewById(R.id.chat_back);
		composeZone = (EditText) this.findViewById(R.id.chatText);
		
		queryCurrentName();
		queryOrderDetails(groupId);
		
		group = SessionManager.getInstance(userId).getGroup(groupId);

		dbHelper = new DBHelper(this, "chathistory6.db", 1);
		getMsgs();
		
		sendBtn.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		try {
			if ((getIntent().getExtras().getString(
					Session.AV_SESSION_INTENT_DATA_KEY) != null)) {
				String msg = getIntent().getExtras().getString(
						Session.AV_SESSION_INTENT_DATA_KEY);
				Loger.i(msg);
				ChatDemoMessage message = JSON.parseObject(msg,
						ChatDemoMessage.class);
				insertMsg(message);
				messages.add(message);
				adapter.notifyDataSetChanged();
			}
		} catch (NullPointerException e) {
		}
	}

	@Override
	public void onClick(View v) {
		String text = composeZone.getText().toString();

		if (TextUtils.isEmpty(text)) {
			return;
		}
		composeZone.getEditableText().clear();
		ChatDemoMessage message = new ChatDemoMessage();
		message.setMessageContent(text);
		message.setMessageType(MessageType.Text);
		message.setMessageFrom(currentName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		message.setTimestamp(df.format(new Date()));
		group.sendMessage(message.makeMessage());
		insertMsg(message);
		messages.add(message);
		adapter.notifyDataSetChanged();
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			Intent intent = new Intent(GroupChatActivity.this, SeePindan.class);
//			intent.putExtra("orderId", orderIdString);
//			intent.putExtra("ordercreator", orderCreatorString);
//			startActivity(intent);
//			finish();
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMessage(String msg) {
		ChatDemoMessage message = JSON.parseObject(msg, ChatDemoMessage.class);
		SimpleDateFormat dg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		message.setTimestamp(dg.format(new Date()));
		insertMsg(message);
		messages.add(message);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onResume() {
		super.onResume();
		DemoGroupMessageReceiver.registerGroupListener(groupId, this);
	}

	@Override
	public void onPause() {
		super.onPause();
		DemoGroupMessageReceiver.unregisterGroupListener(groupId);
	}

	// �����ݱ�������������¼
	public int insertMsg(ChatDemoMessage msg) {
		List<ChatDemoMessage> msgs = new ArrayList<ChatDemoMessage>();
		msgs.add(msg);
		return insertMsgs(msgs);
	}

	// �����ݱ�������������¼
	public int insertMsgs(List<ChatDemoMessage> msgs) {
		if (msgs == null || msgs.size() == 0) {
			return 0;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Loger.i("д���ݿ�·��" + db.getPath());
		db.beginTransaction();
		int n = 0;
		try {
			for (ChatDemoMessage msg : msgs) {
				Loger.i("��ʼ��������");
				String ah = msg.getTimestamp().toString();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					unix = df.parse(ah).getTime() / 1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ContentValues cv = new ContentValues();
				cv.put(GROUPID, groupId);
				cv.put(TIMESTAMP, msg.getTimestamp()); // Ҳ�������
				cv.put(FROM_PEER_NAME, msg.getMessageFrom());
				cv.put(CONTENT, msg.getMessageContent());
				long re = db.insert(MESSAGES, null, cv);
				n++;

			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		// Loger.i("������Ŀ"+n);
		return n;
	}

	public void getMsgs() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Loger.i("�����ݿ�·��" + db.getPath());
		assert db != null;
		// ����groupId��ѯ
		Cursor c = db.query(MESSAGES, null, "groupId=?",
				new String[] { groupId }, null, null, null, null);
		// Cursor c = db.query(MESSAGES, null, null, null, null, null, null,
		// null);
		Loger.i("��¼��Ŀ" + c.getCount());

		while (c.moveToNext()) {
			ChatDemoMessage msg = new ChatDemoMessage();
			msg.setMessageContent(c.getString(c.getColumnIndex(CONTENT)));
			msg.setMessageType(MessageType.Text);
			msg.setMessageFrom(c.getString(c.getColumnIndex(FROM_PEER_NAME)));
			msg.setTimestamp(c.getString(c.getColumnIndex(TIMESTAMP)));
			messages.add(msg);
			adapter.notifyDataSetChanged();
		}
		c.close();
		db.close();
	}
	
	private void queryOrderDetails (String groupId) {
		Loger.l();
		AVQuery<AVObject> query = new AVQuery<AVObject>("Orders");
		query.whereEqualTo("groupId", groupId);
		query.findInBackground(new FindCallback<AVObject>() {
		    public void done(List<AVObject> avObjects, AVException e) {
		        if (e == null) {
		            Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
		            orderIdString = (String) avObjects.get(0).getObjectId();
		            orderCreatorString = (String) avObjects.get(0).get("creator");
		            Loger.i(orderIdString + " " + orderCreatorString);
		        } else {
		            Log.d("ʧ��", "��ѯ����: " + e.getMessage());
		        }
		    }
		});
	}

	private void queryCurrentName() {
		Loger.l();
		userId = AVUser.getCurrentUser().getObjectId();
		AVQuery<AVObject> query = new AVQuery<AVObject>("UserProfile");
		query.whereEqualTo("userId", userId);
		query.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null) {
					Log.d("�ɹ�", "��ѯ��" + avObjects.size() + " ����������������");
					currentName = (String) avObjects.get(0).get("nickName");
				} else {
					Log.d("ʧ��", "��ѯ����: " + e.getMessage());
				}
			}
		});
	}
}
