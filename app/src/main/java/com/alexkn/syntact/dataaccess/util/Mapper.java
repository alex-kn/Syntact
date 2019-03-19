package com.alexkn.syntact.dataaccess.util;

import com.alexkn.syntact.dataaccess.language.LanguagePairEntity;
import com.alexkn.syntact.dataaccess.phrase.PhraseEntity;
import com.alexkn.syntact.domain.model.LanguagePair;
import com.alexkn.syntact.domain.model.Phrase;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Phrase toPhrase(PhraseEntity entity) {

        return new Phrase(entity.getId(), entity.getClue(), entity.getSolution(),
                entity.getAttempt(), entity.getLastSolved(), entity.getNextDueDate(),
                entity.getEasiness(), entity.getConsecutiveCorrectAnswers(), entity.getTimesSolved(),
                entity.getClueLocale(), entity.getSolutionLocale(), entity.getLanguagePairId());
    }

    public static List<Phrase> toPhrase(List<PhraseEntity> entities) {

        return entities.stream().map(Mapper::toPhrase).collect(Collectors.toList());
    }

    public static PhraseEntity toPhraseEntity(Phrase phrase) {

        return new PhraseEntity(phrase.getId(), phrase.getClue(), phrase.getSolution(),
                phrase.getAttempt(), phrase.getLastSolved(), phrase.getNextDueDate(),
                phrase.getEasiness(), phrase.getConsecutiveCorrectAnswers(),
                phrase.getTimesSolved(), phrase.getClueLocale(), phrase.getSolutionLocale(),
                phrase.getLanguagePairId());
    }

    public static List<PhraseEntity> toPhraseEntity(List<Phrase> phrases) {

        return phrases.stream().map(Mapper::toPhraseEntity).collect(Collectors.toList());
    }

    public static LanguagePair toLanguagePair(LanguagePairEntity entity) {

        return new LanguagePair(entity.getId(), entity.getLanguageLeft(), entity.getLanguageRight(),
                entity.getScore());
    }

    public static List<LanguagePair> toLanguagePair(List<LanguagePairEntity> entities) {

        return entities.stream().map(Mapper::toLanguagePair).collect(Collectors.toList());
    }

    public static LanguagePairEntity toLanguagePairEntity(LanguagePair languagePair) {

        return new LanguagePairEntity(languagePair.getId(), languagePair.getLanguageLeft(),
                languagePair.getLanguageRight(), languagePair.getScore());
    }

    public static List<LanguagePairEntity> toLanguagePairEntity(List<LanguagePair> languagePairs) {

        return languagePairs.stream().map(Mapper::toLanguagePairEntity)
                .collect(Collectors.toList());
    }
}
