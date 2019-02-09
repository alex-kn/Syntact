package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.ActiveLanguagePair;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface ActiveLanguagePairRepository {

    void insert(ActiveLanguagePair activeLanguagePair);

    LiveData<List<ActiveLanguagePair>> findAllActiveLanguagePairs();
}
