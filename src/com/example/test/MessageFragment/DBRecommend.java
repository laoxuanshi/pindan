package com.example.test.MessageFragment;

import com.example.test.util.Loger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBRecommend extends SQLiteOpenHelper 
{
  public DBRecommend(Context context, String name, int version) 
  {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) 
  {
	  db.execSQL("create table if not exists recommend (id integer primary key,userId varchar(63) not null,orderId varchar(63) not null,"
	  		+ "ordercreator varchar(63) not null,spot varchar(63) not null,description varchar(1023) not null,timestamp varchar(63) not null)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
  {

  }
}
