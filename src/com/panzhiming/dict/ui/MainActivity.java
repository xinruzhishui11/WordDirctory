package com.panzhiming.dict.ui;

import java.util.ArrayList;
import java.util.List;

import com.panzhiming.dict.R;
import com.panzhiming.dict.R.id;
import com.panzhiming.dict.R.layout;
import com.panzhiming.dict.R.menu;
import com.panzhiming.dict.adapter.WordAdapter;
import com.panzhiming.dict.dal.WordDAO;
import com.panzhiming.dict.entiry.Word;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {
	//EditText 英文单词
	private EditText etWordEn;
	//EditText 中文释义
	private EditText etWordZH;
	//Button 添加按钮
	private Button btnAdd;
	//Button 编辑按钮
	private Button btnEdit;
	//Button 取消按钮
	private Button btnCancle;
	//list集合，用于装word数据
	private List<Word> words;
	//ListView 显示数据
	private ListView lvWords;
	
	private WordAdapter wordAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		initViews();
		//初始化list集合
		words = new ArrayList<Word>();
		//初始化监听器
		initListeners();
		//初始化适配器
		initAdapter();
	}

	

	private void initViews() {
		etWordEn = (EditText) findViewById(R.id.et_en);
		etWordZH = (EditText) findViewById(R.id.et_zh);
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnCancle = (Button) findViewById(R.id.btn_cancle);
		btnEdit = (Button) findViewById(R.id.btn_edit);
		lvWords = (ListView) findViewById(R.id.lv_words);
	}

	private void initListeners() {
		btnAdd.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
	}
	
	
	private void initAdapter() {
		wordAdapter = new WordAdapter(this, words);
		lvWords.setAdapter(wordAdapter);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			//1.获取控件上的内容
			
			String en = etWordEn.getText().toString().trim();
			String zh = etWordZH.getText().toString().trim();
			//2.封装数据
			Word word = new Word();
			//3.调用添加方法，并返回结果
			word.setEn(en);
			word.setZh(zh);
			//4.对结果进行判断
			long id = new WordDAO(this).insert(word);
			//5.如果返回值为-1，添加不成功
			if(id == -1){
				Toast.makeText(this, "添加数据失败", Toast.LENGTH_SHORT).show();
			}
			//6.如果返回不为-1，添加成功
			else {
				Toast.makeText(this, "添加数据成功", Toast.LENGTH_SHORT).show();
				//7.将数据方法哦list集合中
				words.add(word);
			}
			break;
		case R.id.btn_edit:
			
			break;
		case R.id.btn_cancle:
			
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}




}
