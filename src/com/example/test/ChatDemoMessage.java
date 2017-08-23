package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVMessage;
import com.avos.avoscloud.AVUtils;
import com.example.test.util.Loger;

public class ChatDemoMessage {
  public enum MessageType {
    Status(-1), Text(0), Image(1), Audio(2);

    private final int type;

    MessageType(int type) {
      this.type = type;
    }

    public int getType() 
    {
      return this.type;
    }
    
    public static MessageType fromInt(int i) 
    {
        return values()[i];
    }
  }

  MessageType messageType;
  String messageContent;
  String messageFrom;
  String timestamp;
  AVMessage internalMessage;

  public ChatDemoMessage() {
    internalMessage = new AVMessage();
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  public String getMessageContent() 
  {
	  return messageContent;
  }

  public void setMessageContent(String messageContent) 
  {
	  this.messageContent = messageContent;
  }

  public String getMessageFrom() {
    return messageFrom;
  }

  public void setMessageFrom(String messageFrom) {
    this.messageFrom = messageFrom;
  }

  public void setFromPeerId(String peerId) {
    this.internalMessage.setFromPeerId(peerId);
  }

  public String getFromPeerId() {
    return internalMessage.getFromPeerId();
  }

  public String getGroupId() {
    return this.internalMessage.getGroupId();
  }

  public void setGroupId(String groupId) {
    this.internalMessage.setGroupId(groupId);
  }

  public void setToPeerIds(List<String> peerIds) {
    this.internalMessage.setToPeerIds(peerIds);
  }

  public List<String> getToPeerIds() {
    return this.internalMessage.getToPeerIds();
  }
  
  public void setTimestamp(String timestamp) 
  {
	  this.timestamp = timestamp;
  }
  
  public String getTimestamp() 
  {
	  return timestamp;
  }

  public void fromAVMessage(AVMessage message) {
    this.internalMessage = message;
    if (!AVUtils.isBlankString(internalMessage.getMessage())) {
      HashMap<String, Object> params =
          JSON.parseObject(internalMessage.getMessage(), HashMap.class);
      this.messageContent = (String) params.get("content");
      this.messageFrom = (String) params.get("dn");
      this.messageType = (MessageType.valueOf((String) params.get("type")));
    }
  }

  public AVMessage makeMessage() 
  {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("type", this.messageType);
    params.put("content", this.messageContent);
    params.put("dn", this.messageFrom);
    internalMessage.setMessage(JSON.toJSONString(params));
//    if (!AVUtils.isBlankString(internalMessage.getFromPeerId())) {
//      internalMessage.setRequestReceipt(true);
//    } else if (!AVUtils.isBlankString(internalMessage.getGroupId())) {
//      internalMessage.setRequestReceipt(false);
//    }
    return internalMessage;
  }
}
