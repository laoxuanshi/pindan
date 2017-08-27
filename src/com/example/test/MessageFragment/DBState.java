package com.example.test.MessageFragment

import com.example.test.util.Loger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBState extends SQLiteOpenHelper 
{
  public DBState(Context context, String name, int version) 
  {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) 
  {
	  db.execSQL("create table if not exists state (id integer primary key,userId varchar(63) not null,orderId varchar(63) not null,"
	  		+ "content varchar(1023) not null,timestamp varchar(63) not null)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
  {

  }
}
