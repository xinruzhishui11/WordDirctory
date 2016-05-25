package com.panzhiming.dict.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.panzhiming.dict.entiry.Database;
import com.panzhiming.dict.entiry.Word;
import com.panzhiming.dict.util.OBOpenHelper;

public class WordDAO implements IDAO {
	
	private Context context;
	
	 
	public void setContext(Context context) {
		this.context = context;
	}

	public WordDAO(Context context){
		setContext(context);
	}

	@Override
	public long insert(Word word) {
		long id = -1;
		OBOpenHelper oh = new OBOpenHelper(context);
		SQLiteDatabase db = oh.getWritableDatabase();
		String table = Database.Word.TABLE;
		String nullColumnHack = Database.Word.column.ID;
		ContentValues values = new ContentValues();
		values.put(Database.Word.column.EN, word.getEn());
		values.put(Database.Word.column.ZH, word.getZh());
		id = db.insert(table, nullColumnHack, values);
		return id;
	}
	
}
