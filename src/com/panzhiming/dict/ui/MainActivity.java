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
	//WordAdapter ������
	private WordAdapter wordAdapter;
	//��ǰ������word
	private Word actionWord;
	//�޸ģ�ȡ��������
	private LinearLayout llWordEdit;
	//������Ϣ
	private TextView tvTitle;

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
		
		//Ϊlistview ע�������ĵ���˵�
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
		//��ѯ����
		words = new WordDAO(this).query();
		//Ϊlistview���������
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
			Word word = new Word();
			//3.������ӷ����������ؽ��
			actionWord.setEn(en);
			actionWord.setZh(zh);
			//4.�Խ�������ж�
			long id = new WordDAO(this).insert(actionWord);
			//5.�������ֵΪ-1����Ӳ��ɹ�
			if(id == -1){
				Toast.makeText(this, "�������ʧ��", Toast.LENGTH_SHORT).show();
			}
			//6.������ز�Ϊ-1����ӳɹ�
			else {
				Toast.makeText(this, "������ݳɹ�", Toast.LENGTH_SHORT).show();
				//7.�����ݴ����list������
			}
			//���edittext�ؼ�������
			etWordEn.setText(null);
			etWordZH.setText(null);
			//ˢ������
			words.add(actionPosition,actionWord);
			wordAdapter.notifyDataSetChanged();
			
			break;
		case R.id.btn_edit:
			//��ȡ����� ������
			String _zh = etWordZH.getText().toString();
			actionWord.setZh(_zh);
			//���ø��·���
			int effecteRows = new WordDAO(this).upData(actionWord);
			
			if(effecteRows == 0){
				Toast.makeText(this, "�޸�ʧ��", Toast.LENGTH_SHORT).show();
			}else{
				//���ģʽ
				//������Ӱ�ť�����޸ģ�ȡ����ť����
				btnAdd.setVisibility(View.VISIBLE);
				llWordEdit.setVisibility(View.GONE);
				//��Ӣ�ĵı༭����Ϊ�ɼ�
				etWordEn.setEnabled(true);
				
				//��������
				etWordZH.setText(null);
				etWordEn.setText(null);
				//��textview ����Ϊ���༭������Ϣ��
				tvTitle.setText("��ӵ�����Ϣ");
				//����adapter
				
				words.set(0,actionWord);
				wordAdapter.notifyDataSetChanged();
				
				Toast.makeText(this, "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
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
	//ListView�����ĵ���˵�
	private int actionPosition;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) menuInfo;
		actionWord = words.get(contextMenuInfo.position);
		actionPosition = contextMenuInfo.position;
		
		menu.add(Menu.NONE, MENU_ID_EDIT, 200, "�༭("+actionWord.getEn()+")");
		menu.add(Menu.NONE, MENU_ID_DELETE, 201, "ɾ��("+actionWord.getEn()+")");
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ID_EDIT:
			//�༭ģʽ
			setEditMode();
			break;
			
		case MENU_ID_DELETE:
			//�����Ի���
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			AlertDialog alertDialog = builder.setTitle("����")
			.setMessage("ɾ��Ϊ���ɻָ����������Ƿ�Ҫɾ���أ�")
			.setPositiveButton("ȷ��", this)
			.setNegativeButton("ȡ��", this)
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
			//ɾ������
			int effecteRows = new WordDAO(this).delete(actionWord.getId());
			//�Է��ؽ�������ж�
			if(effecteRows == 0){
				Toast.makeText(this, "ɾ��ʧ��", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
				//ˢ������
				words.remove(actionWord);
				wordAdapter.notifyDataSetChanged();
				//�༭ģʽ
			}
			
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			
			break;

		}
	}
	
	private void setEditMode(){
		//������Ӱ�ť�����޸ģ�ȡ����ť���ֳ���
		btnAdd.setVisibility(View.GONE);
		llWordEdit.setVisibility(View.VISIBLE);
		//��Ӣ�ĵı༭����Ϊ���ɼ�
		etWordEn.setEnabled(false);
		
		//������������ӵ����ı༭����
		etWordZH.setText(actionWord.getZh());
		etWordEn.setText(actionWord.getEn());
		//��textview ����Ϊ���༭������Ϣ��
		tvTitle.setText("�༭������Ϣ");
	}


}
