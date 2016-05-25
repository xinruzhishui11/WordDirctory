package com.panzhiming.dict.adapter;

import java.util.List;

import com.panzhiming.dict.R;
import com.panzhiming.dict.entiry.Word;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WordAdapter extends MyBaseAdapter<Word>{

	public WordAdapter(Context context, List<Word> data) {
		super(context, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Word word = getData().get(position);
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = getLayoutInflater().inflate(R.layout.word_item, null);
			holder.tvEn = (TextView) convertView.findViewById(R.id.tv_en);
			holder.tvZh = (TextView) convertView.findViewById(R.id.tv_zh);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvEn.setText(word.getEn());
		holder.tvZh.setText(word.getZh());
		return convertView;
	}
	
	class ViewHolder{
		TextView tvEn;
		TextView tvZh;
	}

}
