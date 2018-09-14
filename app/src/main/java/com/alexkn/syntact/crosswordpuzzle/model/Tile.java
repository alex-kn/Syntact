package com.alexkn.syntact.crosswordpuzzle.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Tile {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    private int x;
    private int y;

    private HashMap<Direction, Tile> neighbors = new HashMap<>();

    private Character character;

    private MutableLiveData<Integer> color = new MutableLiveData<>();

    private DirectionLiveData openDirections = new DirectionLiveData();

    private LinkedList<Registration> registrations = new LinkedList<>();

    private final int maxRegistrations = 2;

    Tile(int x, int y) {
        this.id = count.incrementAndGet();
        this.x = x;
        this.y = y;
    }

    Tile(int[] coordinates) {
        this(coordinates[0], coordinates[1]);
    }

    public void register(Phrase phrase, Axis axis) {
        if (registrations.size() < maxRegistrations) {
            registrations.add(new Registration(phrase, axis));
        } else {
            throw new RuntimeException("Registrations exceeded");
        }
    }

    private List<Tile> findTilesWithSamePhrase(Direction direction) {
        //TODO extract to activity
        //TODO refactor registration queries
        List<Tile> tiles = new LinkedList<>();

        Tile tile = neighbors.get(direction);
        if(tile == null) return tiles;
        for (Registration registration : tile.getRegistrations()) {
            for (Registration registration1 : this.registrations) {
                if (registration.phrase.equals(registration1.phrase)) {
                    tiles.addAll(tile.findTilesWithSamePhrase(direction));
                    tiles.add(tile);
                }
            }


        }
        return tiles;
    }

    private List<Tile> findConnectedTiles() {
        List<Tile> tiles = new LinkedList<>();
        tiles.add(this);
        for (Direction direction : neighbors.keySet()) {
            tiles.addAll(findTilesWithSamePhrase(direction));
        }

        return tiles;
    }


    public ArrayList<Axis> getFreeAxis() {
        ArrayList<Axis> axes = new ArrayList<>();
        if (registrations.isEmpty()) {
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

    public void setNeighbor(Direction direction, Tile tile) {
        neighbors.put(direction, tile);
        //TODO refactor
        if (!findTilesWithSamePhrase(direction).isEmpty()) {
            openDirections.addDirection(direction);
        }
    }

    public boolean isFull() {
        return registrations.size() >= maxRegistrations;
    }

    public int[] getCoordinates() {
        return new int[]{x, y};
    }

    public void setColor(Integer color) {
        this.color.setValue(color);
    }

    public void setColorForConnectedTiles(Integer color) {
        for (Tile tile : findConnectedTiles()) {
            tile.setColor(color);
        }

    }

    public LinkedList<Registration> getRegistrations() {
        return registrations;
    }

    public LiveData<Integer> getColor() {
        return color;
    }

    public LiveData<Set<Direction>> getOpenDirections() {
        return openDirections;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x &&
                y == tile.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
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
