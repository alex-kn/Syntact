package com.alexkn.syntact.crosswordpuzzle.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Phrase {
    String clue;
    String solution;

    ArrayList<Character> solutionCharacters = new ArrayList<>();

    public Phrase(String clue, String solution) {
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

    public int getLength(){
        return solution.length(); //TODO no clue for now
    }

}
