package com.alexkn.syntact.hangwords.logic;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public class PhraseSolver {
    private static final PhraseSolver ourInstance = new PhraseSolver();

    public static PhraseSolver getInstance() {
        return ourInstance;
    }

    private PhraseSolver() {
    }

    public boolean isCharacterCorrect(SolvablePhrase phrase, Character character) {
        return StringUtils.containsIgnoreCase(phrase.getSolution(), character.toString());
    }

    public SolvablePhrase solve(SolvablePhrase phrase, Character character) {
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
