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
	//EditText Ӣ�ĵ���
	private EditText etWordEn;
	//EditText ��������
	private EditText etWordZH;
	//Button ��Ӱ�ť
	private Button btnAdd;
	//Button �༭��ť
	private Button btnEdit;
	//Button ȡ����ť
	private Button btnCancle;
	//list���ϣ�����װword����
	private List<Word> words;
	//ListView ��ʾ����
	private ListView lvWords;
	
	private WordAdapter wordAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//��ʼ���ؼ�
		initViews();
		//��ʼ��list����
		words = new ArrayList<Word>();
		//��ʼ��������
		initListeners();
		//��ʼ��������
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
			//1.��ȡ�ؼ��ϵ�����
			
			String en = etWordEn.getText().toString().trim();
			String zh = etWordZH.getText().toString().trim();
			//2.��װ����
			Word word = new Word();
			//3.������ӷ����������ؽ��
			word.setEn(en);
			word.setZh(zh);
			//4.�Խ�������ж�
			long id = new WordDAO(this).insert(word);
			//5.�������ֵΪ-1����Ӳ��ɹ�
			if(id == -1){
				Toast.makeText(this, "�������ʧ��", Toast.LENGTH_SHORT).show();
			}
			//6.������ز�Ϊ-1����ӳɹ�
			else {
				Toast.makeText(this, "������ݳɹ�", Toast.LENGTH_SHORT).show();
				//7.�����ݷ���Ŷlist������
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
