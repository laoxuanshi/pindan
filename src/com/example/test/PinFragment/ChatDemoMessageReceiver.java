package com.example.test.PinFragment;

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
import com.avos.avoscloud.AVMessage;
import com.avos.avoscloud.AVMessageReceiver;
import com.avos.avoscloud.LogUtil;
import com.avos.avoscloud.Session;
import com.avos.avospush.notification.NotificationCompat;
import com.example.test.util.Loger;

public class ChatDemoMessageReceiver extends AVMessageReceiver 
{
	static HashMap<String, MessageListener> sessionMessageDispatchers = new HashMap<String, MessageListener>();

  @Override
  public void onSessionOpen(Context context, Session session) 
  {
    //this.sendOpenIntent(context);
    Loger.i("用户成功登录上实时聊天服务器了");
  }

  @Override
  public void onSessionPaused(Context context, Session session) {
	  Loger.i("这里掉线了");
  }

  @Override
  public void onSessionResumed(Context context, Session session) {
	  Loger.i("重新连接上了");
  }

@Override
public void onError(Context arg0, Session arg1, Throwable arg2) {
	LogUtil.log.e("session error", (Exception) arg2);
	
}

@Override
public void onMessage(Context arg0, Session arg1, AVMessage arg2) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMessageFailure(Context context, Session session, AVMessage msg) 
{
	Loger.i("message failed :" + msg.getMessage());
}

@Override
public void onMessageSent(Context context, Session session, AVMessage msg) 
{
	Loger.i("message fromPeerId=" + msg.getFromPeerId());
	Loger.i("message to peerIds=" + msg.getToPeerIds());
	Loger.i("message sent timestamp=" + msg.getTimestamp());
}

@Override
public void onStatusOffline(Context arg0, Session arg1, List<String> arg2) {
	Loger.i("status offline :" + arg2.toString());
	
}

@Override
public void onStatusOnline(Context arg0, Session arg1, List<String> arg2) {
	Loger.i("status online :" + arg2.toString());
	
}


//  private void sendOpenIntent(Context context) {
//    Intent intent = new Intent(context, ChatTargetActivity.class);
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//    context.startActivity(intent);
//  }
//
  public static void registerSessionListener(String peerId, MessageListener listener) {
    sessionMessageDispatchers.put(peerId, listener);
  }

  public static void unregisterSessionListener(String peerId) {
    sessionMessageDispatchers.remove(peerId);
  }
//


}
