package com.example.test;

import com.example.test.util.Loger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper 
{
  public DBHelper(Context context, String name, int version) 
  {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) 
  {
	  db.execSQL("create table if not exists messages6 (id integer primary key,groupId varchar(63) not null,content varchar(1023) not null"
	  		+ ",timestamp varchar(127) not null,fromname varchar(63) not null)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
  {

  }
}
