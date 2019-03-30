package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.LanguagePair;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LanguagePairRepository {

    Long insert(LanguagePair LanguagePair);

    LiveData<List<LanguagePair>> findAllLanguagePairs();

    void updateScore(Long id, int newScore);

    void incrementScore(Long id, int by);

    LiveData<LanguagePair> findLanguagePair(Long id);
}
