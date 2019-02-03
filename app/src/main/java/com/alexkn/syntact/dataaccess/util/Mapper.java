package com.alexkn.syntact.dataaccess.util;

import com.alexkn.syntact.dataaccess.phrase.api.PhraseEntity;
import com.alexkn.syntact.domain.model.Phrase;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Phrase toPhrase(PhraseEntity entity) {

        return new Phrase(entity.getId(), entity.getClue(), entity.getSolution(),
                entity.getAttempt(), entity.getLastSolved(), entity.getTimesSolved(), entity.getClueLocale(), entity.getSolutionLocale());
    }

    public static List<Phrase> toPhrase(List<PhraseEntity> entities) {

        return entities.stream().map(Mapper::toPhrase).collect(Collectors.toList());
    }

    public static PhraseEntity toEntity(Phrase phrase) {

        return new PhraseEntity(phrase.getId(), phrase.getClue(), phrase.getSolution(),
                phrase.getAttempt(), phrase.getLastSolved(), phrase.getTimesSolved(), phrase.getClueLocale(), phrase.getSolutionLocale());
    }

    public static List<PhraseEntity> toEntity(List<Phrase> phrases) {

        return phrases.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }
}
