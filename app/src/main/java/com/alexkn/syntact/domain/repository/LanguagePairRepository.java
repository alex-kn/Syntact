package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.LanguagePair;

import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;

public interface LanguagePairRepository {

    Long insert(LanguagePair languagePair);

    void delete(Long id);

    void update(LanguagePair languagePair);

    LanguagePair find(Long id);

    boolean languagePairExists(Locale left, Locale right);

    LiveData<List<LanguagePair>> findAllLanguagePairs();

    void updateScore(Long id, int newScore);

    void incrementScore(Long id, int by);

    LiveData<LanguagePair> findLanguagePair(Long id);
}
