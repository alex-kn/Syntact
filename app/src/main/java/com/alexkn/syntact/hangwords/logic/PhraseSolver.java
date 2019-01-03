package com.alexkn.syntact.hangwords.logic;

import com.alexkn.syntact.hangwords.ui.Letter;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class PhraseSolver {
    private static final PhraseSolver ourInstance = new PhraseSolver();

    public static PhraseSolver getInstance() {
        return ourInstance;
    }

    private LetterManagement letterManagement;

    private PhraseSolver() {
        letterManagement = LetterManagement.getInstance();
    }

    public boolean isCharacterCorrect(SolvablePhrase phrase, int id) {
        return StringUtils.containsIgnoreCase(phrase.getSolution(), letterManagement.findLetter(id).toString());
    }

    public SolvablePhrase solve(SolvablePhrase phrase, int id) {
        Character character = letterManagement.findLetter(id).getCharacter();
        String solution = phrase.getSolution();
        IntStream indices = IntStream.range(0, solution.length()).filter(i -> StringUtils
                .equalsIgnoreCase(character.toString(), String.valueOf(solution.charAt(i))));

        StringBuilder stringBuilder = new StringBuilder(phrase.getCurrentText());
        indices.forEach(index -> stringBuilder.setCharAt(index, character));

        phrase.setCurrentText(stringBuilder.toString());
        return phrase;

    }

    public boolean checkSolved(SolvablePhrase phrase) {
        return !phrase.getCurrentText().contains("_");
    }
}
