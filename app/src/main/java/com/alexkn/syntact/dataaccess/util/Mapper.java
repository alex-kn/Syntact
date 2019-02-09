package com.alexkn.syntact.dataaccess.util;

import com.alexkn.syntact.dataaccess.language.ActiveLanguagePairEntity;
import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.ActiveLanguagePair;
import com.alexkn.syntact.domain.model.Phrase;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Phrase toPhrase(PhraseEntity entity) {

        return new Phrase(entity.getId(), entity.getClue(), entity.getSolution(),
                entity.getAttempt(), entity.getLastSolved(), entity.getTimesSolved(),
                entity.getClueLocale(), entity.getSolutionLocale());
    }

    public static List<Phrase> toPhrase(List<PhraseEntity> entities) {

        return entities.stream().map(Mapper::toPhrase).collect(Collectors.toList());
    }

    public static PhraseEntity toPhraseEntity(Phrase phrase) {

        return new PhraseEntity(phrase.getId(), phrase.getClue(), phrase.getSolution(),
                phrase.getAttempt(), phrase.getLastSolved(), phrase.getTimesSolved(),
                phrase.getClueLocale(), phrase.getSolutionLocale());
    }

    public static List<PhraseEntity> toPhraseEntity(List<Phrase> phrases) {

        return phrases.stream().map(Mapper::toPhraseEntity).collect(Collectors.toList());
    }

    public static ActiveLanguagePair toActiveLanguagePair(ActiveLanguagePairEntity entity) {

        return new ActiveLanguagePair(entity.getId(), entity.getLanguageLeft(),
                entity.getLanguageRight());
    }

    public static List<ActiveLanguagePair> toActiveLanguagePair(
            List<ActiveLanguagePairEntity> entities) {

        return entities.stream().map(Mapper::toActiveLanguagePair).collect(Collectors.toList());
    }

    public static ActiveLanguagePairEntity toActiveLanguagePairEntity(
            ActiveLanguagePair activeLanguagePair) {

        return new ActiveLanguagePairEntity(activeLanguagePair.getId(),
                activeLanguagePair.getLanguageLeft(), activeLanguagePair.getLanguageRight());
    }

    public static List<ActiveLanguagePairEntity> toActiveLanguagePairEntity(
            List<ActiveLanguagePair> activeLanguagePairs) {

        return activeLanguagePairs.stream().map(Mapper::toActiveLanguagePairEntity)
                .collect(Collectors.toList());
    }
}
