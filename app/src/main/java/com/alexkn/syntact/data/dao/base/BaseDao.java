package com.alexkn.syntact.data.dao.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.Collection;

public interface BaseDao<T> {

    @Insert
    Long insert(T t);

    @Insert
    void insert(Collection<T> t);

    @Update
    void update(T t);

    @Delete
    void delete(T t);
}
