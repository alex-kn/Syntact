package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LetterRepository {

    LiveData<List<Letter>> find(Long languagePairId, LetterColumn letterColumn);

    void insert(List<Letter> letters);

    void insert(Letter letter);

    void delete(Letter letter);
}
