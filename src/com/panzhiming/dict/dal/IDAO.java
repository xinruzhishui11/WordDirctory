package com.panzhiming.dict.dal;

import java.util.List;

import com.panzhiming.dict.entiry.Word;

public interface IDAO {
	long insert(Word word);
	List<Word> query();
	int delete(long id);
	int upData(Word word);
}
