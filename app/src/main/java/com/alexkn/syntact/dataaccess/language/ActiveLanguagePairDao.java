package com.alexkn.syntact.dataaccess.language;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ActiveLanguagePairDao {

    @Insert
    void insert(ActiveLanguagePairEntity activeLanguagePairEntity);

    @Insert
    void insert(Collection<ActiveLanguagePairEntity> activeLanguagePairEntities);

    @Delete
    void delete(ActiveLanguagePairEntity activeLanguagePairEntity);

    @Query("SELECT * FROM ActiveLanguagePair")
    LiveData<List<ActiveLanguagePairEntity>> findAll();

}
