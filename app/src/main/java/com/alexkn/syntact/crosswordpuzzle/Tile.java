package com.alexkn.syntact.crosswordpuzzle;

import java.util.ArrayList;
import java.util.LinkedList;

public class Tile {

    int x;
    int y;

    Character character;

    private LinkedList<Registration> registrations = new LinkedList<>();

    private final int maxRegistrations = 2;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }
    public void register(Phrase phrase, Axis axis) {
        if (registrations.size() < maxRegistrations) {
            registrations.add(new Registration(phrase, axis));
        } else {
            //TODO exception
        }
    }

    public ArrayList<Axis> getFreeAxis() {
        ArrayList<Axis> axes= new ArrayList<>();
        if(registrations.isEmpty()){
            axes.add(Axis.X);
            axes.add(Axis.Y);
        } else {
            axes.add(registrations.getFirst().axis.opposite());
        }
        return axes;
    }

    public boolean isAxisFree(Axis axis) {
        return registrations.isEmpty() || registrations.getFirst().axis != axis;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean canHaveCharacter(Character character) {
        return this.character == null || this.character.equals(character);
    }

    public boolean isFull() {
        return registrations.size() >= maxRegistrations;
    }

    public int[] getCoordinates(){
        return new int[]{x, y};
    }

    class Registration {

        Phrase phrase;
        Axis axis;

        Registration(Phrase phrase, Axis axis) {
            this.phrase = phrase;
            this.axis = axis;
        }
    }
}
