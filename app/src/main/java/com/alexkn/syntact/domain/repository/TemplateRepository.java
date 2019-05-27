package com.alexkn.syntact.domain.repository;

import androidx.lifecycle.LiveData;

import com.alexkn.syntact.domain.model.Template;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

public interface TemplateRepository extends BaseRepository<Template> {

    boolean exists(Long id);

    LiveData<Template> findAll();
}
