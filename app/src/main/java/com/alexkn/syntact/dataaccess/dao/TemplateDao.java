package com.alexkn.syntact.dataaccess.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.alexkn.syntact.dataaccess.dao.base.BaseDao;
import com.alexkn.syntact.domain.model.Template;

@Dao
public interface TemplateDao extends BaseDao<Template> {

    @Query("SELECT EXISTS(SELECT 1 FROM Template WHERE id=:id LIMIT 1)")
    boolean exists(Long id);

    @Query("SELECT * FROM Template")
    LiveData<Template> findAll();
}
