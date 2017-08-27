package com.example.test.PinFragment;

import com.example.test.util.Loger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBComment extends SQLiteOpenHelper 
{
  public DBComment(Context context, String name, int version) 
  {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) 
  {
	  db.execSQL("create table if not exists comment (id integer primary key,userId varchar(63) not null,orderId varchar(63) not null,"
	  		+ "creatorname varchar(1023) not null,ordercreator varchar(1023) not null,content varchar(1023) not null,"
	  		+ "creatorAvatar varchar(63) not null,timestamp varchar(63) not null)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
  {

  }
}
