package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class Phrase {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    private String clue;
    private String solution;

    private ArrayList<Character> solutionCharacters = new ArrayList<>();

    Phrase(String clue, String solution) {
        this.id = count.incrementAndGet();
        this.clue = clue;
        this.solution = solution;
        for (int i = 0; i < solution.toCharArray().length; i++) {
            solutionCharacters.add(solution.toCharArray()[i]);
        }
    }

    public boolean hasCharacter (Character character) {
        return solutionCharacters.contains(character);
    }

    public ArrayList<Integer> getIndexOf(Character character) {
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(solutionCharacters.indexOf(character));
        return indexes;
    }

    public Character getCharacterAt(int index){
        return solutionCharacters.get(index);
    }

    public Set<Character> getOverlappingCharacters(Phrase phrase) {
        Set<Character> first = new HashSet<>(solutionCharacters);
        Set<Character> second = new HashSet<>(phrase.solutionCharacters);
        first.retainAll(second);
        return first;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phrase phrase = (Phrase) o;
        return id == phrase.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public int getLength(){
        return solution.length(); //TODO no clue for now
    }

}
