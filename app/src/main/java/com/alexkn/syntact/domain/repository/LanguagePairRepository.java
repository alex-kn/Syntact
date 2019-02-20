package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.LanguagePair;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface LanguagePairRepository {

    void insert(LanguagePair activeLanguagePair);

    LiveData<List<LanguagePair>> findAllLanguagePairs();

    void updateScore(int id, int newScore);


}
