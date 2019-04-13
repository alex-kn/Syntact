package com.alexkn.syntact.domain.usecase;

import com.alexkn.syntact.domain.common.LetterColumn;
import com.alexkn.syntact.domain.model.Letter;
import com.alexkn.syntact.domain.repository.LetterRepository;
import com.alexkn.syntact.domain.service.LetterGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class ManageLetters {

    @Inject
    LetterRepository letterRepository;

    @Inject
    LetterGenerator letterGenerator;

    @Inject
    ManageLetters() {

    }

    public void generateLetters(Long languagePairId) {

        Character[] characters = letterGenerator.generateCharacters(24);

        List<Letter> letters = Arrays.stream(characters).map(character -> {
            Letter letter = new Letter();
            letter.setCharacter(character);
            letter.setLanguagePairId(languagePairId);
            LetterColumn value = LetterColumn.values()[new Random()
                    .nextInt(LetterColumn.values().length)];
            letter.setLetterColumn(value);
            return letter;
        }).collect(Collectors.toList());
        letterRepository.insert(letters);
    }

    public void replaceLetter(Letter oldLetter) {

        Character character = letterGenerator.generateNewCharacter();
        Letter newLetter = new Letter();
        newLetter.setCharacter(character);
        newLetter.setLanguagePairId(oldLetter.getLanguagePairId());
        newLetter.setLetterColumn(oldLetter.getLetterColumn());
        letterRepository.insert(newLetter);
        letterRepository.delete(oldLetter);
    }

    public LiveData<List<Letter>> getLetters(Long languagePairId, LetterColumn letterColumn) {
        return letterRepository.find(languagePairId, letterColumn);
    }
}
