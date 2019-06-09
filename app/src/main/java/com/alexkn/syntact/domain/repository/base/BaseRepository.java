package com.alexkn.syntact.domain.repository.base;

import java.util.Collection;

public interface BaseRepository<T>{

    Long insert(T t);

    void insert(Collection<T> t);

    void update(T t);

    void delete(T t);

    T find(Long id);
}
