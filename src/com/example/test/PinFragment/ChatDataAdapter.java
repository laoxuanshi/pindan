package com.example.test.PinFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.test.ChatDemoMessage;
import com.example.test.util.Loger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatDataAdapter extends BaseAdapter 
{

  List<ChatDemoMessage> messages;
  Context mContext;

  public ChatDataAdapter(Context context, List<ChatDemoMessage> messages) {
    this.messages = messages;
    this.mContext = context;
  };

  @Override
  public int getCount() {

    return messages.size();
  }

  @Override
  public ChatDemoMessage getItem(int position) {
    return messages.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position) {
    return messages.get(position).getMessageType().getType();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) 
  {
    ViewHolder holder = null;
    final ChatDemoMessage m = getItem(position);

    switch (getItemViewType(position)) {
      case -1:
        if (convertView == null) {
          convertView = LayoutInflater.from(mContext).inflate(R.layout.item_info, null);
          holder = new ViewHolder();
          holder.message = (TextView) convertView.findViewById(R.id.avoscloud_chat_demo_info);
          convertView.setTag(holder);
        } else {
          holder = (ViewHolder) convertView.getTag();
        }
        break;
      case 0:
        if (convertView == null) 
        {
          convertView = LayoutInflater.from(mContext).inflate(R.layout.item_message, null);
          holder = new ViewHolder();
          holder.message = (TextView) convertView.findViewById(R.id.avoscloud_chat_demo_message);
          holder.username = (TextView) convertView.findViewById(R.id.avoscloud_chat_demo_user_id);
          holder.timest = (TextView) convertView.findViewById(R.id.avoscloud_feedback_timestamp);
          convertView.setTag(holder);
        } 
        else 
        {
          holder = (ViewHolder) convertView.getTag();
        }
        holder.username.setText(m.getMessageFrom());
        break;
    }
    holder.message.setText(m.getMessageContent());
    holder.timest.setText(m.getTimestamp());
    return convertView;
  }

  public class ViewHolder {
    TextView message;
    TextView username;
    TextView timest;
  }

}
