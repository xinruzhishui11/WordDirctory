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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener ,android.content.DialogInterface.OnClickListener {
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
	//WordAdapter 适配器
	private WordAdapter wordAdapter;
	//当前操作的word
	private Word actionWord;
	//修改，取消的区域
	private LinearLayout llWordEdit;
	//标题信息
	private TextView tvTitle;

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
		
		//为listview 注册上下文点击菜单
		registerForContextMenu(lvWords);
	}

	

	private void initViews() {
		etWordEn = (EditText) findViewById(R.id.et_en);
		etWordZH = (EditText) findViewById(R.id.et_zh);
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnCancle = (Button) findViewById(R.id.btn_cancle);
		btnEdit = (Button) findViewById(R.id.btn_edit);
		lvWords = (ListView) findViewById(R.id.lv_words);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		llWordEdit = (LinearLayout) findViewById(R.id.ll_edit_or_cancle);
	}

	private void initListeners() {
		btnAdd.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		btnCancle.setOnClickListener(this);
	}
	
	
	private void initAdapter() {
		//查询数据
		words = new WordDAO(this).query();
		//为listview添加适配器
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
			Word word = new Word();
			//3.调用添加方法，并返回结果
			actionWord.setEn(en);
			actionWord.setZh(zh);
			//4.对结果进行判断
			long id = new WordDAO(this).insert(actionWord);
			//5.如果返回值为-1，添加不成功
			if(id == -1){
				Toast.makeText(this, "添加数据失败", Toast.LENGTH_SHORT).show();
			}
			//6.如果返回不为-1，添加成功
			else {
				Toast.makeText(this, "添加数据成功", Toast.LENGTH_SHORT).show();
				//7.将数据存放在list集合中
			}
			//清空edittext控件的内容
			etWordEn.setText(null);
			etWordZH.setText(null);
			//刷新数据
			words.add(actionPosition,actionWord);
			wordAdapter.notifyDataSetChanged();
			
			break;
		case R.id.btn_edit:
			//获取输框中 的内容
			String _zh = etWordZH.getText().toString();
			actionWord.setZh(_zh);
			//调用更新方法
			int effecteRows = new WordDAO(this).upData(actionWord);
			
			if(effecteRows == 0){
				Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
			}else{
				//添加模式
				//显现添加按钮，将修改，取消按钮隐藏
				btnAdd.setVisibility(View.VISIBLE);
				llWordEdit.setVisibility(View.GONE);
				//将英文的编辑框设为可见
				etWordEn.setEnabled(true);
				
				//情况输入框
				etWordZH.setText(null);
				etWordEn.setText(null);
				//将textview 设置为“编辑单词信息”
				tvTitle.setText("添加单词信息");
				//更新adapter
				
				words.set(0,actionWord);
				wordAdapter.notifyDataSetChanged();
				
				Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
			}
			
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

	
	private static final int MENU_ID_EDIT = 1;
	private static final int MENU_ID_DELETE = 2;
	//ListView上下文点击菜单
	private int actionPosition;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		actionWord = words.get(contextMenuInfo.position);
		actionPosition = contextMenuInfo.position;
		
		menu.add(Menu.NONE, MENU_ID_EDIT, 200, "编辑("+actionWord.getEn()+")");
		menu.add(Menu.NONE, MENU_ID_DELETE, 201, "删除("+actionWord.getEn()+")");
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ID_EDIT:
			//编辑模式
			setEditMode();
			break;
			
		case MENU_ID_DELETE:
			//弹出对话框
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			AlertDialog alertDialog = builder.setTitle("警告")
			.setMessage("删除为不可恢复操作，您是否要删除呢？")
			.setPositiveButton("确定", this)
			.setNegativeButton("取消", this)
			.create();
			alertDialog.show();
			
			break;
		}
		
		return super.onContextItemSelected(item);
	}



	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			//删除数据
			int effecteRows = new WordDAO(this).delete(actionWord.getId());
			//对返回结果进行判断
			if(effecteRows == 0){
				Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
				//刷新数据
				words.remove(actionWord);
				wordAdapter.notifyDataSetChanged();
				//编辑模式
			}
			
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			
			break;

		}
	}
	
	private void setEditMode(){
		//隐藏添加按钮，将修改，取消按钮显现出来
		btnAdd.setVisibility(View.GONE);
		llWordEdit.setVisibility(View.VISIBLE);
		//将英文的编辑框设为不可见
		etWordEn.setEnabled(false);
		
		//将中文释义添加到中文编辑框中
		etWordZH.setText(actionWord.getZh());
		etWordEn.setText(actionWord.getEn());
		//将textview 设置为“编辑单词信息”
		tvTitle.setText("编辑单词信息");
	}


}
