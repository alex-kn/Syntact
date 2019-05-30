package com.alexkn.syntact.dataaccess.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.dataaccess.common.AppDatabase;
import com.alexkn.syntact.dataaccess.dao.TemplateDao;
import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.repository.TemplateRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class TemplateRepositoryImpl implements TemplateRepository {

    private TemplateDao templateDao;

    @Inject
    public TemplateRepositoryImpl(Application application) {

        AppDatabase database = AppDatabase.getDatabase(application);
        templateDao = database.templateDao();
    }

    @Override
    public Long insert(Template template) {

        return templateDao.insert(template);
    }

    @Override
    public void insert(Collection<Template> templates) {

        templateDao.insert(templates);
    }

    @Override
    public void update(Template template) {

        templateDao.update(template);
    }

    @Override
    public void delete(Template template) {

        templateDao.delete(template);
    }

    @Override
    public boolean exists(Long id) {

        return templateDao.exists(id);
    }

    @Override
    public LiveData<List<Template>> findAll() {

        return templateDao.findAll();
    }
}
