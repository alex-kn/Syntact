package com.alexkn.syntact.hangwords.dataaccess.api;

import com.alexkn.syntact.hangwords.dataaccess.api.to.Phrase;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PhraseDao {

    @Insert
    public void insert(Phrase phrase);

    @Insert
    public void insert(Collection<Phrase> phrases);

    @Update
    public void update(Phrase phrase);

    @Delete
    public void delete(Phrase phrase);

    @Query("SELECT * FROM phrase")
    public LiveData<List<Phrase>> findAll();
}
