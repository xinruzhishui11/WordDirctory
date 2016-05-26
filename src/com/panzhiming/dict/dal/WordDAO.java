package com.panzhiming.dict.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

	@Override
	public List<Word> query() {
		List<Word> words = new ArrayList<Word>();
		OBOpenHelper oh = new OBOpenHelper(context);
		SQLiteDatabase db = oh.getWritableDatabase();
		String table = Database.Word.TABLE;
		String[] columns = {"_id","en","zh"};
		String selection = null;
		String[] selectionArgs= null;
		String groupBy = null;
		String having = null;
		String orderBy = "_id desc";
		Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		//遍历cursor，将数据封装到list集合中
		while(c.moveToNext()){
			Word word = new Word();
			word.setId(c.getLong(0));
			word.setEn(c.getString(1));
			word.setZh(c.getString(2));
			words.add(word);
		}
		return words;
	}

	@Override
	public int delete(long id) {
		int effecteRows = 0;
		OBOpenHelper oh = new OBOpenHelper(context);
		SQLiteDatabase db = oh.getWritableDatabase();
		String table = Database.Word.TABLE;
		String whereClause = "_id = ?";
		String[] whereArgs = {id+""};
		effecteRows = db.delete(table, whereClause, whereArgs);
		return effecteRows;
	}

	@Override
	public int upData(Word word) {
		int effecteRows = 0;
		OBOpenHelper oh = new OBOpenHelper(context);
		SQLiteDatabase db = oh.getWritableDatabase();
		String table = Database.Word.TABLE;
		ContentValues values = new ContentValues();
		values.put("zh", word.getZh());
		String whereClause = null;
		String[] whereArgs = null;
		effecteRows = db.update(table, values, whereClause, whereArgs);
		return effecteRows;
	}
	
}
