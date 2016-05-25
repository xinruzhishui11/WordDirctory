package com.panzhiming.dict.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OBOpenHelper extends SQLiteOpenHelper{

	public OBOpenHelper(Context context ) {
		super(context, "edu.db", null,1);
	}

	//第一次创建数据库是调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql = "create table words(_id integer primary key autoincrement, en verchar(16) not null unique, zh verchar(32))";
		db.execSQL(sql);
	}
	//版本更新时调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
