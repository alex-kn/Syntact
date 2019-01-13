package com.alexkn.syntact.dataaccess.util;

import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.Phrase;

public class Mapper {

    public static Phrase toPhrase(PhraseEntity entity) {

        return new Phrase(entity.getId(), entity.getClue(), entity.getSolution(),
                entity.getAttempt(), entity.getLastSolved(), entity.getTimesSolved());
    }

    public static PhraseEntity toEntity(Phrase phrase) {
        return new PhraseEntity(phrase.getId(), phrase.getClue(), phrase.getSolution(),
                phrase.getAttempt(), phrase.getLastSolved(), phrase.getTimesSolved());
    }
}
