package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LetterRepository extends BaseRepository<Letter> {

    LiveData<List<Letter>> find(Long bucketId, LetterColumn letterColumn);

    void deleteAllLettersForLanguage(Long bucketId);
}
