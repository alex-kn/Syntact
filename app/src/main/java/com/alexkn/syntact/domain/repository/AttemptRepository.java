package com.alexkn.syntact.domain.repository;

import com.alexkn.syntact.domain.model.Attempt;
import com.alexkn.syntact.domain.repository.base.BaseRepository;

public interface AttemptRepository extends BaseRepository<Attempt> {

    void updateAttempt(Long solvableItemId, String attempt);
}
