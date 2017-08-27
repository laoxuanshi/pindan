package com.example.test.MessageFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVGroupMessageReceiver;
import com.avos.avoscloud.AVMessage;
import com.avos.avoscloud.Group;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.Session;
import com.avos.avospush.notification.NotificationCompat;
import com.example.test.ChatDemoMessage;
import com.example.test.util.Loger;

public class DemoGroupMessageReceiver extends AVGroupMessageReceiver {
	public interface JoinedListener {
		public void onJoined(String groupId);
	}

	public static void setJoinedListener(JoinedListener joinedListener) {
		listener = joinedListener;
	}

	public static JoinedListener listener = null;

	static String groupid = null;
	static HashMap<String, MessageListener> groupMessageDispatchers = new HashMap<String, MessageListener>();

	@Override
	public void onJoined(Context context, Group group) {
		Loger.i("����ɹ�" + group.getGroupId() + " Joined");
		groupid = group.getGroupId();
		if (listener != null)
			listener.onJoined(groupid);
	}

	@Override
	public void onError(Context context, Group group, Throwable e) {
		LogUtil.log.e("", (Exception) e);
	}

	@Override
	public void onInvited(Context arg0, Group arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKicked(Context arg0, Group arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMemberJoin(Context context, Group group,
			List<String> joinedPeerIds) {
		Loger.i(joinedPeerIds + " join " + group.getGroupId());

	}

	@Override
	public void onMemberLeft(Context context, Group group,
			List<String> leftPeerIds) {
		Loger.i(leftPeerIds + " left " + group.getGroupId());
	}

	@Override
	public void onMessage(Context context, Group group, AVMessage msg) {
		JSONObject j = JSONObject.parseObject(msg.getMessage());
		ChatDemoMessage message = new ChatDemoMessage();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		message.setTimestamp(df.format(new Date()));
		MessageListener listener = groupMessageDispatchers.get(group
				.getGroupId());
		/*
		 * ������demo���Զ�������ݸ�ʽ�������Լ���ʵ���У�������ȫ���ɵ�ͨ��json�������������Լ�����Ϣ��ʽ
		 * 
		 * �û����͵���Ϣ {"msg":"����һ����Ϣ","dn":"������Ϣ��Դ�ߵ�����"}
		 * 
		 * �û���״̬��Ϣ {"st":"�û�������״̬��Ϣ","dn":"������Ϣ��Դ�ߵ�����"}
		 */

		if (j.containsKey("content")) {

			message.fromAVMessage(msg);
			// ���Activity����Ļ�ϲ���active��ʱ���ѡ���� ֪ͨ

			if (listener == null) {
				Loger.i("Activity inactive, about to send notification.");
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);

				String ctnt = message.getMessageFrom() + "��"
						+ message.getMessageContent();
				Intent resultIntent = new Intent(context,
						GroupChatActivity.class);
				resultIntent.putExtra("groupId", group.getGroupId());
				resultIntent.putExtra(Session.AV_SESSION_INTENT_DATA_KEY,
						JSON.toJSONString(message));
				resultIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

				PendingIntent pi = PendingIntent.getActivity(context, -1,
						resultIntent, PendingIntent.FLAG_ONE_SHOT);

				Notification notification = new NotificationCompat.Builder(
						context)
						.setContentTitle(
								context.getString(R.string.notif_group))
						.setContentText(ctnt)
						.setContentIntent(pi)
						.setSmallIcon(R.drawable.ic_launcher)
						.setLargeIcon(
								BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.ic_launcher))
						.setAutoCancel(true).build();
				if(shezhi.tishiyin==1)
					notification.defaults=Notification.DEFAULT_SOUND;
				
				nm.notify(233, notification);
				Loger.i("notification sent");
			} else {
				listener.onMessage(JSON.toJSONString(message));
			}
		}
		Loger.i(message + " receiver");
	}

	@Override
	public void onMessageSent(Context context, Group group, AVMessage message) 
	{
		Loger.i(message.getMessage() + " sent");
		LogUtil.avlog.d("message sent timestamp=" + message.getTimestamp());
	}

	@Override
	public void onMessageFailure(Context context, Group group, AVMessage message) {
		Loger.i(message.getMessage() + " failure");
	}

	@Override
	public void onQuit(Context arg0, Group group) {
		Loger.i(group.getGroupId() + " quit");
	}

	@Override
	public void onReject(Context arg0, Group arg1, String arg2,
			List<String> arg3) {
		// TODO Auto-generated method stub

	}

	public static void registerGroupListener(String groupId,
			MessageListener listener) {
		groupMessageDispatchers.put(groupId, listener);
	}

	public static void unregisterGroupListener(String groupId) {
		groupMessageDispatchers.remove(groupId);
	}

}
